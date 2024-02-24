package com.MaiN.main_android.View.Reserv

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.MaiN.main_android.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalField
import java.time.temporal.WeekFields
import java.util.Calendar
import java.util.Locale


class ReservFragment : Fragment() {
    val itemList = arrayListOf<Date>()
    val listAdapter = CalendarAdapter(itemList)
    lateinit var calendarList:RecyclerView
    lateinit var mLayoutManager:LinearLayoutManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.reserv_fragment,container,false)
        calendarList = view.findViewById(R.id.week_recyclerview)
        mLayoutManager = LinearLayoutManager(view.context)

        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        calendarList.layoutManager = mLayoutManager
        setListView()

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //달력 다이얼로그 띄우기
        val calendar_button = view.findViewById<Button>(R.id.date_button)

        val currentDate = getCurrentDate() //버튼에 현재 날짜 표시
        calendar_button.text = currentDate

        calendar_button.setOnClickListener{
            showDatePickerDialog(calendar_button)
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    var selectedDate = LocalDate.now() //선택 날짜 저장 전역변수 (초기값은 현재 날짜)
    //달력 다이얼로그 보여주는 함수
    @RequiresApi(Build.VERSION_CODES.O)
    fun showDatePickerDialog(button: Button) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        //달력 다이얼로그에서 날짜를 선택했을 때 동작 정의
        val datePickerDialog = DatePickerDialog(requireContext(), R.style.CalendarTheme,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            this.selectedDate = LocalDate.of(year,month+1,dayOfMonth)//선택된 날짜 local date 로 변환
            button.text = "$year/${month+1}/$dayOfMonth" //버튼에 날짜 표시
            setListView() //날짜 선택할때마다 리스트뷰 갱신
        }, year, month, day)

        datePickerDialog.show()
    }

    //현재 날짜 가져오는 함수
    fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return "$year/${month+1}/$day"
    }


    @RequiresApi(Build.VERSION_CODES.O)
    //리스트 뷰 설정 (한 주 날짜 스크롤)
    fun setListView() {
        val weekFields: TemporalField = WeekFields.of(Locale.KOREA).dayOfWeek()
        val startOfWeek = selectedDate.with(weekFields,1) //선택한 날짜가 속한 주의 첫째 날
        val endOfWeek = startOfWeek.plusDays(6) //선택한 날짜가 속한 주의 마지막 날

        itemList.clear()
        var currentDate = startOfWeek
        while(!currentDate.isAfter(endOfWeek)) {
            val dayOfWeek: DayOfWeek = currentDate.dayOfWeek
            val dayOfWeekKorean = when(dayOfWeek){
                DayOfWeek.MONDAY -> "월"
                DayOfWeek.TUESDAY -> "화"
                DayOfWeek.WEDNESDAY -> "수"
                DayOfWeek.THURSDAY -> "목"
                DayOfWeek.FRIDAY -> "금"
                DayOfWeek.SATURDAY -> "토"
                DayOfWeek.SUNDAY -> "일"
            }
            itemList.add(Date(dayOfWeekKorean,currentDate.dayOfMonth.toString()))
            currentDate = currentDate.plusDays(1)
        }
        calendarList.adapter = listAdapter
    }

}

