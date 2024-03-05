package com.MaiN.main_android.View.Reserv.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.MaiN.main_android.SharedPreference.MyApplication
import com.MaiN.main_android.View.Reserv.data.ReservationBottomSheetData
import com.MaiN.main_android.View.Reserv.state.fillTwoZero

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationBottomSheet(
    showBottomSheet: Boolean,
    onDismissRequest: () -> Unit = {},
    onReservation: (ReservationBottomSheetData) -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState()
    val schoolId = MyApplication.prefs.getSchoolNumber("schoolNumber", "???")

    val toggleStates = listOf(
        "세미나실1",
        "세미나실2",
        "교수회의실",
    )
    var selectedOption by remember {
        mutableStateOf(toggleStates[1])
    }
    val onSelectionChange = { text: String ->
        selectedOption = text
    }
    var startHour by remember { mutableIntStateOf(0) }
    var startMinute by remember { mutableIntStateOf(0) }
    var endHour by remember { mutableIntStateOf(0) }
    var endMinute by remember { mutableIntStateOf(0) }


    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                onDismissRequest()
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("등록하기")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TriStateToggle(
                        toggleStates = toggleStates,
                        selectedOption = selectedOption,
                        onSelectionChange = onSelectionChange
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "학번")
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = schoolId)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "시작시간")
                    Spacer(modifier = Modifier.weight(1f))
                    TimePickerWithDialog { hour, minute ->
                        startHour = hour
                        startMinute = minute
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "종료 시간")
                    Spacer(modifier = Modifier.weight(1f))
                    TimePickerWithDialog { hour, minute ->
                        endHour = hour
                        endMinute = minute
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            onReservation(
                                ReservationBottomSheetData(
                                    location = selectedOption,
                                    studentId = schoolId,
                                    startTime = "${startHour.fillTwoZero()}:${startMinute.fillTwoZero()}",
                                    endTime = "${endHour.fillTwoZero()}:${endMinute.fillTwoZero()}"
                                )
                            )
                            onDismissRequest()
                        }
                    ) {
                        Text(text = "등록하기")
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}

@Composable
fun TriStateToggle(
    toggleStates: List<String>,
    selectedOption: String,
    onSelectionChange: (String) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .wrapContentSize()
    ) {
        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(24.dp))
                .background(Color.LightGray)
        ) {
            toggleStates.forEach { text ->
                Text(
                    text = text,
                    color = Color.Black,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(24.dp))
                        .clickable {
                            onSelectionChange(text)
                        }
                        .background(
                            if (text == selectedOption) {
                                Color.White
                            } else {
                                Color.LightGray
                            }
                        )
                        .padding(
                            vertical = 12.dp,
                            horizontal = 16.dp,
                        ),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerWithDialog(
    selectedHour: Int = 0,
    selectedMinute: Int = 0,
    onTimeSelected: (hour: Int, minute: Int) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }
    val timeState = rememberTimePickerState(
        initialHour = selectedHour,
        initialMinute = selectedMinute,
        is24Hour = true
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .background(color = Color.White)
                    .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(state = timeState)
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { showDialog = false }) {
                        Text(text = "Dismiss")
                    }
                    TextButton(onClick = {
                        showDialog = false
                        onTimeSelected(timeState.hour, timeState.minute)
                    }) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { showDialog = true }) {
            Text(text = "${timeState.hour.fillTwoZero()} : ${timeState.minute.fillTwoZero()}")
        }
    }
}
