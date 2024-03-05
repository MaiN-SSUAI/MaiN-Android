package com.MaiN.main_android.View.Reserv

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.MaiN.main_android.View.Reserv.component.CellColumn
import com.MaiN.main_android.View.Reserv.component.DateItem
import com.MaiN.main_android.View.Reserv.component.DatePickerDialog
import com.MaiN.main_android.View.Reserv.component.DetailBottomSheet
import com.MaiN.main_android.View.Reserv.component.ReservationBottomSheet
import com.MaiN.main_android.View.Reserv.component.Timeline
import com.MaiN.main_android.View.Reserv.data.toReservationRequest
import com.MaiN.main_android.View.Reserv.state.fillTwoZero
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationScreen(
    viewModel: ReservationViewModel = viewModel()
) {
    val state by viewModel.stateFlow.collectAsState()
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var showDetailBottomSheet by remember { mutableStateOf(false) }
    var showReservationBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(state.selectedIndex) {
        state.lazyListState.animateScrollToItemCenter(state.selectedIndex)
    }

    LaunchedEffect(state.day, state.month, state.year) {
        viewModel.refresh()
    }

    DetailBottomSheet(
        showBottomSheet = showDetailBottomSheet,
        onDismissRequest = {
            showDetailBottomSheet = false
        },
        bottomSheetData = state.bottomSheetData,
        onDeleteClick = {
            viewModel.deleteEvent(it)
        }
    )

    ReservationBottomSheet(
        showBottomSheet = showReservationBottomSheet,
        onDismissRequest = {
            showReservationBottomSheet = false
        },
        onReservation = {
            viewModel.addEvent(it.toReservationRequest("${state.year}-${state.month.fillTwoZero()}-${state.day.fillTwoZero()}"))
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "세미나실 예약",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 30.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row {
            Spacer(modifier = Modifier.width(20.dp))
            DatePickerDialog(
                text = "${state.usStyleMonth} ${state.day}, ${state.year}",
                onDateSelected = {
                    viewModel.setDateByMillis(it)
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .height(30.dp)
                    .width(70.dp)
                    .background(Color(0xFF298BFF), shape = RoundedCornerShape(6.dp))
                    .align(Alignment.CenterVertically)
                    .clickable {
                        showReservationBottomSheet = true
                    }
            ) {
                Text(
                    text = "예약하기",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyRow(
            state = state.lazyListState,
            modifier = Modifier
                .background(Color.White),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            itemsIndexed(state.monthDetails) { index, item ->
                DateItem(
                    dayOfTheWeek = item.first,
                    day = item.second,
                    isSelected = state.selectedIndex == index
                ) {
                    viewModel.updateSelectedIndex(index)
                    scope.launch {
                        state.lazyListState.animateScrollToItemCenter(index)
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Timeline(startTime = 0, endTime = 23)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(horizontal = 2.dp, vertical = 10.dp)
            ) {
                CellColumn(
                    cells = state.seminarRoom1,
                    color = Color(0xFF96DCFF),
                    onCellClick = {
                        showDetailBottomSheet = true
                        viewModel.updateBottomSheetData(
                            it
                        )
                    }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(horizontal = 2.dp, vertical = 10.dp)
            ) {
                CellColumn(
                    cells = state.seminarRoom2,
                    color = Color(0xFFFFA5A5),
                    onCellClick = {
                        showDetailBottomSheet = true
                        viewModel.updateBottomSheetData(
                            it
                        )
                    }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(horizontal = 2.dp, vertical = 10.dp)
            ) {
                CellColumn(
                    cells = state.facultyConferenceRoom,
                    color = Color(0xFFFFEBA5),
                    onCellClick = {
                        showDetailBottomSheet = true
                        viewModel.updateBottomSheetData(
                            it
                        )
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
}


suspend fun LazyListState.animateScrollToItemCenter(index: Int) {
    layoutInfo.resolveItemOffsetToCenter(index)?.let {
        animateScrollToItem(index, it)
        return
    }

    scrollToItem(index)

    layoutInfo.resolveItemOffsetToCenter(index)?.let {
        animateScrollToItem(index, it)
    }
}

private fun LazyListLayoutInfo.resolveItemOffsetToCenter(index: Int): Int? {
    val itemInfo = visibleItemsInfo.firstOrNull { it.index == index } ?: return null
    val containerSize = viewportSize.width - beforeContentPadding - afterContentPadding
    return -(containerSize - itemInfo.size) / 2
}


@Preview(showSystemUi = true)
@Composable
fun ReservationScreenPreview() {
    ReservationScreen()
}