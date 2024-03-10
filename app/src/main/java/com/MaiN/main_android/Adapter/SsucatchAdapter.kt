package com.MaiN.main_android.Adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.MaiN.main_android.R
import com.MaiN.main_android.SharedPreference.MyApplication
import com.MaiN.main_android.View.Notice.Ssucatch_WebView
import com.MaiN.main_android.retrofit.RetrofitConnection
import com.MaiN.main_android.retrofit.SsucatchAPIService
import com.MaiN.main_android.retrofit.SsucatchDataclass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SsucatchAdapter(
    private val lifecycleScope: LifecycleCoroutineScope
) : RecyclerView.Adapter<SsucatchAdapter.SsucatchViewHolder>() {
    private val allItems = ArrayList<SsucatchDataclass.SsucatchDataclassItem>()
    private var items = ArrayList<SsucatchDataclass.SsucatchDataclassItem>()
    var selectedCategory: String = "전체"

    fun setItems(items: List<SsucatchDataclass.SsucatchDataclassItem>) {
        this.allItems.clear()
        this.allItems.addAll(items)
        this.items = ArrayList(allItems)
        notifyDataSetChanged()
    }

    //즐겨찾기된 아이템만 필터링
    fun filterFavorites() {
        items = ArrayList(allItems.filter { it.favorites })
        notifyDataSetChanged()
    }

    //필터링 해제
    fun clearFilter() {
        items = ArrayList(allItems) //모든 아이템 items 에 복사
        notifyDataSetChanged()
    }

    fun filterCategory(category: String) {
        items = ArrayList(allItems.filter { it.category == category })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SsucatchAdapter.SsucatchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ssucatch_recycler, parent, false)
        return SsucatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SsucatchAdapter.SsucatchViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class SsucatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val date: TextView = itemView.findViewById<TextView>(R.id.ssucatch_item_date)
        private val title: TextView = itemView.findViewById(R.id.ssucatch_item_title)
        private val favorite: ImageView = itemView.findViewById(R.id.ssucatch_star_img)
        private val category: TextView = itemView.findViewById(R.id.ssucatchCategoryText)
        private val progress: TextView = itemView.findViewById(R.id.ssucatchProgressText)


        fun bind(item: SsucatchDataclass.SsucatchDataclassItem) {
            date.text = item.sDate
            title.text = item.title
            category.text = item.category
            progress.text = item.progress

            //progress 비어있으면 textview 안보이게
            if (item.progress.isEmpty()) {
                progress.visibility = View.GONE
            } else {
                progress.text = item.progress
                progress.visibility = View.VISIBLE
            }

            favorite.isSelected = item.favorites

            if (item.favorites) {
                favorite.setImageResource(R.drawable.selected_star)
            } else {
                favorite.setImageResource(R.drawable.unselected_star)
            }

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, Ssucatch_WebView::class.java)
                intent.putExtra("url", item.link)
                context.startActivity(intent)
            }

            favorite.setOnClickListener {
                item.favorites = !item.favorites
                favorite.isSelected = item.favorites
                val studentId = MyApplication.prefs.getSchoolNumber("schoolNumber", "")
                //favorite 이 true 일 때
                if (item.favorites) {
                    favorite.setImageResource(R.drawable.selected_star)
                    lifecycleScope.launch(Dispatchers.IO) {
                        addFavorite(studentId, item.id)
                    }
                } else {
                    favorite.setImageResource(R.drawable.unselected_star)
                    lifecycleScope.launch(Dispatchers.IO) {
                        deleteFavorite(studentId, item.id)
                    }
                }
            }
        }

        //즐겨찾기 post api 호출 함수
        private suspend fun addFavorite(studentId: String, itemId: Int) {
            val retrofit = RetrofitConnection.getInstance()
            val service = retrofit.create(SsucatchAPIService::class.java)

            try {
                val response = service.addFavorite(studentId, itemId)
                if (response.isSuccessful) {
                    Log.d("ApiService", "즐겨찾기 추가 성공")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.d("ApiService", "즐겨찾기 추가 실패: $errorBody")
                }
            } catch (e: Exception) {
                Log.d("ApiService", "즐겨찾기 추가 실패: $e")
            }
        }

        //즐겨찾기 delete 함수
        private suspend fun deleteFavorite(studentId: String, itemId: Int) {
            val retrofit = RetrofitConnection.getInstance()
            val service = retrofit.create(SsucatchAPIService::class.java)

            try {
                val response = service.deleteFavorite(studentId, itemId)
                if (response.isSuccessful) {
                    Log.d("ApiService", "즐겨찾기 삭제 성공")
                } else {
                    Log.d("ApiService", "즐겨찾기 삭제 실패 ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.d("ApiService", "즐겨찾기 삭제 실패 : $e")
            }
        }
    }
}