package com.MaiN.main_android.Adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.MaiN.main_android.R
import com.MaiN.main_android.SharedPreference.MyApplication
import com.MaiN.main_android.View.Notice.Funsys_WebView
import com.MaiN.main_android.retrofit.FunsysAPIService
import com.MaiN.main_android.retrofit.FunsysDataclass
import com.MaiN.main_android.retrofit.RetrofitConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FunsysAdapter: RecyclerView.Adapter<FunsysAdapter.FunsysViewHolder>() {
    private val allItems = ArrayList<FunsysDataclass.FunsysDataclassItem>()
    private var items = ArrayList<FunsysDataclass.FunsysDataclassItem>()

    fun setItems(items: List<FunsysDataclass.FunsysDataclassItem>) {
        this.allItems.clear()
        this.allItems.addAll(items)
        this.items= ArrayList(allItems)
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
    ): FunsysAdapter.FunsysViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.funsys_recycler,parent,false)
        return FunsysViewHolder(view)
    }

    override fun onBindViewHolder(holder: FunsysAdapter.FunsysViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class FunsysViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        private val start_date : TextView = itemView.findViewById(R.id.funsys_item_startdate)
        private val end_date : TextView = itemView.findViewById(R.id.funsys_item_enddate)
        private val title : TextView = itemView.findViewById(R.id.funsys_item_title)
        private val favorite: ImageView = itemView.findViewById(R.id.funsys_star_img)

        fun bind(item : FunsysDataclass.FunsysDataclassItem) {
            start_date.text = item.startDate
            end_date.text = item.end_date
            title.text = item.title

            favorite.isSelected = item.favorites //즐겨찾기 상태에 따라 이미지뷰 상태 변경 (별 색칠 )

            if (item.favorites) {
                favorite.setImageResource(R.drawable.selected_star)
            } else {
                favorite.setImageResource(R.drawable.unselected_star)
            }

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, Funsys_WebView::class.java)

                val webView = WebView(context)
                webView.clearCache(true)
                val cookieManager = CookieManager.getInstance()
                cookieManager.removeAllCookies(null)
                cookieManager.flush()

                intent.putExtra("url", item.link)
                context.startActivity(intent)
            }

            //즐겨찾기 버튼 클릭시 동작
            favorite.setOnClickListener {
                item.favorites = !item.favorites
                favorite.isSelected = item.favorites
                val studentId = MyApplication.prefs.getSchoolNumber("schoolNumber","")
                //favorite 이 true 일 때
                if (item.favorites) {
                    favorite.setImageResource(R.drawable.selected_star)
                    addFavorite(studentId, item.id)
                } else {
                    favorite.setImageResource(R.drawable.unselected_star)
                    deleteFavorite(studentId, item.id)
                }
            }
        }


        //즐겨찾기 post api 호출 함수
        private fun addFavorite(studentId:String, itemId: Int){
            val retrofit = RetrofitConnection.getInstance()

            val service = retrofit.create(FunsysAPIService::class.java)

            service.addFavorite(studentId, itemId).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d("ApiService", "즐겨찾기 추가 성공")
                        Log.d("ApiService", "즐겨찾기 추가 성공")
                    } else {
                        Log.d("ApiService", "즐겨찾기 추가 실패: ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("ApiService", "즐겨찾기 추가 실패: $t")
                }
            })

        }

        //즐겨찾기 delete 함수
        private fun deleteFavorite(studentId: String,itemId: Int){
            val retrofit = RetrofitConnection.getInstance()
            val service = retrofit.create(FunsysAPIService::class.java)

            //delete api 호출
            service.deleteFavorite(studentId,itemId).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d("ApiService","즐겨찾기 삭제 성공")
                    }else{
                        Log.d("ApiService","즐겨찾기 삭제 실패 ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("ApiService","즐겨찾기 삭제 실패 : $t")
                }
            })
            }
        }
    }