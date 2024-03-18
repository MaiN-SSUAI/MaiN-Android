package com.MaiN.main_android.View.Reserv.state

import androidx.compose.foundation.lazy.LazyListState
import com.MaiN.main_android.View.Reserv.data.BottomSheetData

data class ReservationScreenUiState(
    val year: Int = 0,
    val usStyleMonth: String = "",
    val month: Int = 0,
    val day: Int = 0,
    val dayOfWeek: String = "",
    val daysInMonth: Int = 0,
    val monthDetails: List<Pair<String, Int>> = listOf(),
    val selectedIndex: Int = 0,
    val lazyListState: LazyListState = LazyListState(),
    val seminarRoom1: List<CellUiState> = listOf(),
    val seminarRoom2: List<CellUiState> = listOf(),
    val facultyConferenceRoom: List<CellUiState> = listOf(),
    val bottomSheetData: BottomSheetData = BottomSheetData(),
)