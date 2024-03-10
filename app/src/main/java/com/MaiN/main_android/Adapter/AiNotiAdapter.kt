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
import com.MaiN.main_android.View.Notice.AiNoti_WebView
import com.MaiN.main_android.retrofit.AiNotiAPIService
import com.MaiN.main_android.retrofit.AiNotiDataclass
import com.MaiN.main_android.retrofit.RetrofitConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AiNotiAdapter(
    private val lifecycleScope: LifecycleCoroutineScope
): RecyclerView.Adapter<AiNotiAdapter.AiNotiViewHolder>() {
    private val allItems = ArrayList<AiNotiDataclass.AiNotiDataclassItem>()
    private var items = ArrayList<AiNotiDataclass.AiNotiDataclassItem>()

    fun setItems(items: List<AiNotiDataclass.AiNotiDataclassItem>) {
        this.allItems.clear()
        this.allItems.addAll(items)
        this.items = ArrayList(allItems) //모든 아이템을 items 리스트에 복사 (즐겨찾기 필터링 위해 필요)
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AiNotiAdapter.AiNotiViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ainoti_recycler,parent,false)
        return AiNotiViewHolder(view)
    }

    override fun onBindViewHolder(holder: AiNotiAdapter.AiNotiViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class AiNotiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val date : TextView = itemView.findViewById(R.id.ai_item_date)
        private val title : TextView = itemView.findViewById(R.id.ai_item_title)
        private val favorite : ImageView = itemView.findViewById(R.id.ainoti_star_img) //즐겨찾기 버튼


        fun bind(item : AiNotiDataclass.AiNotiDataclassItem) {
            date.text = item.date
            title.text = item.title

            favorite.isSelected = item.favorites //즐겨찾기 상태에 따라 이미지뷰 상태 변경 (별 색칠 )

            if (item.favorites) {
                favorite.setImageResource(R.drawable.selected_star)
            } else {
                favorite.setImageResource(R.drawable.unselected_star)
            }

            //공지 클릭시 동작
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, AiNoti_WebView::class.java)
                intent.putExtra("url",item.link)
                context.startActivity(intent)
            }

            //즐겨찾기 버튼 클릭시 동작
            favorite.setOnClickListener{
                item.favorites = !item.favorites
                favorite.isSelected = item.favorites
                //favorite 이 true 일 때
                if(item.favorites) {
                    favorite.setImageResource(R.drawable.selected_star)
                    val studentId=MyApplication.prefs.getSchoolNumber("schoolNumber","")
                    lifecycleScope.launch(Dispatchers.IO) {
                        addFavorite(studentId,item.id)
                    }
                }else{
                    favorite.setImageResource(R.drawable.unselected_star)
                    val studentId=MyApplication.prefs.getSchoolNumber("schoolNumber","")
                    lifecycleScope.launch(Dispatchers.IO) {
                        deleteFavorite(studentId,item.id)
                    }
                }
            }
        }

        //즐겨찾기 post api 호출 함수
        private suspend fun addFavorite(studentId:String, itemId: Int){
            val retrofit = RetrofitConnection.getInstance()

            val service = retrofit.create(AiNotiAPIService::class.java)

            try {
                val response = service.addFavorite(studentId, itemId)
                if (response.isSuccessful) {
                    Log.d("ApiService", "즐겨찾기 추가 성공")
                } else {
                    Log.d("ApiService", "즐겨찾기 추가 실패: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("ApiService", "즐겨찾기 추가 실패: $e")
            }
        }

        //즐겨찾기 delete 함수
        private suspend fun deleteFavorite(studentId: String,itemId: Int){
            val retrofit = RetrofitConnection.getInstance()
            val service = retrofit.create(AiNotiAPIService::class.java)

            //delete api 호출
            try {
                val response = service.deleteFavorite(studentId, itemId)
                if (response.isSuccessful) {
                    Log.d("ApiService", "즐겨찾기 삭제 성공")
                } else {
                    Log.d("ApiService", "즐겨찾기 삭제 실패: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("ApiService", "즐겨찾기 삭제 실패: $e")
            }
        }
    }
}