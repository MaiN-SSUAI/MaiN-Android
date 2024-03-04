package com.MaiN.main_android.View.Reserv

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MaiN.main_android.View.Reserv.data.BottomSheetData
import com.MaiN.main_android.View.Reserv.state.ReservationScreenUiState
import com.MaiN.main_android.View.Reserv.state.fillTwoZero
import com.MaiN.main_android.retrofit.ReservAPIService
import com.MaiN.main_android.retrofit.ReservDataclass
import com.MaiN.main_android.retrofit.ReservationRequest
import com.MaiN.main_android.retrofit.RetrofitConnection
import com.MaiN.main_android.retrofit.toCellUiStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale

class ReservationViewModel : ViewModel() {
    private val _stateFlow: MutableStateFlow<ReservationScreenUiState> =
        MutableStateFlow(ReservationScreenUiState())
    val stateFlow: StateFlow<ReservationScreenUiState> = _stateFlow.asStateFlow()

    private val retrofitAPI = RetrofitConnection.getInstance().create(ReservAPIService::class.java)


    init {
        val currentTimeMillis = System.currentTimeMillis()
        setDateByMillis(currentTimeMillis)
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _stateFlow.update {
                it.copy(
                    seminarRoom1 = emptyList(),
                    seminarRoom2 = emptyList(),
                    facultyConferenceRoom = emptyList(),
                )
            }
            val year = _stateFlow.value.year.toString()
            val month = _stateFlow.value.month.fillTwoZero()
            val day = _stateFlow.value.day.fillTwoZero()
            showEvents("$year-$month-$day", "세미나실1")
            showEvents("$year-$month-$day", "세미나실2")
            showEvents("$year-$month-$day", "교수회의실")
        }
    }


    fun setDateByMillis(it: Long) {
        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.of("UTC"))
        val year = dateTime.year
        val monthAsUsStyle =
            dateTime.month.getDisplayName(
                TextStyle.FULL,
                Locale.US
            ).take(3)
        val month = dateTime.month.value
        val day = dateTime.dayOfMonth
        val dayOfWeek = dateTime.dayOfWeek.getDisplayName(
            TextStyle.FULL,
            Locale.KOREA
        )


        _stateFlow.update {
            it.copy(
                year = year,
                month = month,
                usStyleMonth = monthAsUsStyle,
                day = day,
                dayOfWeek = dayOfWeek,
            )
        }

        _stateFlow.update {
            it.copy(
                daysInMonth = getDaysInMonth(year, dateTime.monthValue)
            )
        }

        _stateFlow.update {
            it.copy(
                monthDetails = getMonthDetails(year, dateTime.monthValue)
            )
        }

        _stateFlow.update {
            it.copy(
                selectedIndex = day - 1
            )
        }

    }

    private fun getDaysInMonth(year: Int, month: Int): Int {
        val yearMonth = YearMonth.of(year, month)
        return yearMonth.lengthOfMonth()
    }

    private fun getMonthDetails(year: Int, month: Int): MutableList<Pair<String, Int>> {
        val monthDetails = mutableListOf<Pair<String, Int>>()
        val yearMonth = YearMonth.of(year, month)

        val firstDayOfMonth = yearMonth.atDay(1)
        val lastDayOfMonth = yearMonth.atEndOfMonth()

        var currentDate = firstDayOfMonth
        while (!currentDate.isAfter(lastDayOfMonth)) {
            monthDetails.add(
                Pair(
                    currentDate.dayOfWeek.getDisplayName(
                        TextStyle.FULL,
                        Locale.KOREA
                    )[0].toString(),
                    currentDate.dayOfMonth
                )
            )
            currentDate = currentDate.plusDays(1)
        }

        return monthDetails
    }

    fun updateSelectedIndex(index: Int) {
        _stateFlow.update {
            it.copy(
                selectedIndex = index
            )
        }
        _stateFlow.update {
            it.copy(
                dayOfWeek = it.monthDetails[index].first,
                day = it.monthDetails[index].second
            )
        }
    }

    private fun showEvents(date: String, location: String) {
        viewModelScope.launch {
            retrofitAPI.showEvents(date, location).enqueue(
                object : Callback<ReservDataclass> {
                    override fun onResponse(
                        call: Call<ReservDataclass>,
                        response: Response<ReservDataclass>
                    ) {
                        val cellUiStateList = response.body()?.toCellUiStateList()
                        when (location) {
                            "세미나실1" -> {
                                _stateFlow.update {
                                    it.copy(
                                        seminarRoom1 = cellUiStateList ?: emptyList()
                                    )
                                }
                                Log.d("KWK", _stateFlow.value.seminarRoom1.toString())
                            }

                            "세미나실2" -> {
                                _stateFlow.update {
                                    it.copy(
                                        seminarRoom2 = cellUiStateList ?: emptyList()
                                    )
                                }
                                Log.d("KWK", _stateFlow.value.seminarRoom2.toString())
                            }

                            "교수회의실" -> {
                                _stateFlow.update {
                                    it.copy(
                                        facultyConferenceRoom = cellUiStateList ?: emptyList()
                                    )
                                }
                                Log.d("KWK", _stateFlow.value.facultyConferenceRoom.toString())
                            }

                            else -> {

                            }
                        }
                    }

                    override fun onFailure(call: Call<ReservDataclass>, t: Throwable) {
                        Log.d("KWK-showEvent-FAIL", t.message.toString())
                        _stateFlow.update {
                            it.copy(
                                seminarRoom1 = emptyList(),
                                seminarRoom2 = emptyList(),
                                facultyConferenceRoom = emptyList(),
                            )
                        }
                    }

                }
            )
        }
    }

    fun updateBottomSheetData(bottomSheetData: BottomSheetData) {
        _stateFlow.update {
            it.copy(
                bottomSheetData = bottomSheetData
            )
        }
    }

    fun addEvent(reservationRequest: ReservationRequest) {
        viewModelScope.launch {
            Log.d("KWK-addEvent", reservationRequest.toString())
            retrofitAPI.addEvent(reservationRequest).enqueue(
                object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Log.d("KWK-addEvent-SUCCESS", response.body().toString())
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("KWK-addEvent-FAIL", t.message.toString())
                    }

                }
            )
        }
    }

    fun deleteEvent(eventId: String) {
        viewModelScope.launch {
            retrofitAPI.deleteEvent(eventId).enqueue(
                object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        Log.d("KWK-deleteEvent-SUCCESS", response.body().toString())
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Log.d("KWK-deleteEvent-FAIL", t.message.toString())
                    }
                }
            )
        }
    }
}

