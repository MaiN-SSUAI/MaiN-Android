package com.MaiN.main_android.View.Reserv

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.MaiN.main_android.R

//캘린더(주 날짜 리스트뷰)데이터를 가진 어댑터 클래스
class CalendarAdapter(private val dataSet: ArrayList<Date>):
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    var drawable: Drawable?=null //셀의 배경에 사용될 drawable
    var originalDrawable: Drawable?=null
    private lateinit var itemClickListner: OnItemClickListener
    private var selectedPosition = -1; //현재 선택된 셀의 위치 (초기값 = -1)

    //뷰홀더: 각 셀의 뷰 바인딩
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val dateCell : TextView = view.findViewById(R.id.date_cell) //날짜 표시
        val dayCell : TextView = view.findViewById(R.id.day_cell) //요일 표시
    }

    //새로운 뷰홀더 객체 생성 메소드
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
           //셀 레이아웃을 인플레이트하여 뷰 객체 생성, 뷰홀더에 바인딩
            val view=LayoutInflater.from(viewGroup.context).inflate(R.layout.reserv_weekdate_recyclerview,viewGroup,false)
            drawable = ContextCompat.getDrawable(view.context,R.drawable.date_cell_selected_box)
            originalDrawable = view.background;
            return ViewHolder(view) //뷰 홀더 객체 생성 및 반환
    }

    //뷰 홀더와 데이터 바인딩
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        //텍스트 뷰에 날짜와 요일 설정
        holder.dateCell.text = dataSet[position].date
        holder.dayCell.text = dataSet[position].day

        //선택된 셀만 배경색 변경. 그 외의 셀은 기본으로
        if(position == selectedPosition) {
            holder.itemView.background = drawable
        }else{
            holder.itemView.background = originalDrawable;
        }

        //셀 클릭 리스너 설정
        holder.itemView.setOnClickListener{
            selectedPosition = position //현재 선택된 셀의 위치 업데이트
            notifyDataSetChanged() //데이터 변경을 알려주어 뷰를 갱신하도록 함
        }
    }

    override fun getItemCount() = dataSet.size


}