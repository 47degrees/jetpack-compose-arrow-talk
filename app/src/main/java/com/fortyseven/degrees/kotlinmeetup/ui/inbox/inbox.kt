package com.fortyseven.degrees.kotlinmeetup.ui.inbox

import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.Spacer
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.layout.preferredHeight
import androidx.ui.layout.preferredWidth
import androidx.ui.layout.size
import androidx.ui.layout.wrapContentSize
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.fortyseven.degrees.kotlinmeetup.R
import com.fortyseven.degrees.kotlinmeetup.ui.lightThemeColors

@Model
class CounterState(var count: Int = 0)

@Composable
fun BadgeEnvelope(count: Int) {
    Envelope(count = count) {
        if (count > 0) {
            Badge(text = if (count > 10) "10+" else "$count")
        }
    }
}

@Composable
fun Envelope(count: Int, children: @Composable() () -> Unit) =
    Box(modifier = Modifier.padding(16.dp).fillMaxWidth().wrapContentSize(Alignment.Center)) {
        Image(
            asset = imageResource(
                when {
                    count <= 0 -> R.mipmap.envelope_empty
                    count in 1..8 -> R.mipmap.envelope_some
                    else -> R.mipmap.envelope_full
                }
            )
        )
        Box(
            modifier = Modifier.size(70.dp, 50.dp),
            children = children
        )
    }


@Composable
fun Badge(text: String) {
    Surface(
        shape = RoundedCornerShape(24.dp), color = Color.Red,
        modifier = Modifier.wrapContentSize(Alignment.TopEnd)
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp
            ),
            modifier = Modifier.wrapContentSize(Alignment.TopEnd).padding(8.dp)
        )
    }
}

@Composable
fun AddEmailButton(counterState: CounterState) {
    Button(
        onClick = { counterState.count++ },
        backgroundColor = if (counterState.count > 5) Color.Green else lightThemeColors.primary
    ) {
        Text(text = "Add email (${counterState.count})")
    }
}

@Composable
fun AddEmailButton(count: Int, countCb: (Int) -> Unit) {
    Button(
        onClick = { countCb(count + 1) },
        backgroundColor = if (count > 5) Color.Green else lightThemeColors.primary
    ) {
        Text(text = "Add email (${count})")
    }
}

@Composable
fun DeleteEmailButton(counterState: CounterState) {
    Button(
        onClick = { if (counterState.count > 0) counterState.count-- },
        backgroundColor = Color.Red
    ) {
        Text(text = "Delete email")
    }
}

@Composable
fun DeleteEmailButton(count: Int, countCb: (Int) -> Unit) {
    Button(
        onClick = { if (count > 0) countCb(count - 1) },
        backgroundColor = Color.Red
    ) {
        Text(text = "Delete email")
    }
}

@Preview("envelope example4")
@Composable
private fun previewExample4() {
    MaterialTheme(
        colors = lightThemeColors
    ) {
        val counterState = CounterState(count = 4)
        Column(modifier = Modifier.fillMaxWidth().padding(8.dp).wrapContentSize(Alignment.Center)) {
            BadgeEnvelope(counterState.count)
            Spacer(Modifier.preferredHeight(8.dp))
            Row(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)) {
                AddEmailButton(counterState)
                Spacer(Modifier.preferredWidth(8.dp))
                DeleteEmailButton(counterState)
            }
        }
    }
}

@Preview("envelope example5")
@Composable
private fun previewExample5() {
    InboxExample()
}

    @Composable
    fun InboxExample() {
        val (counterState, counterStateCb) = state { 0 }
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp)
                .wrapContentSize(Alignment.Center)
        ) {
            BadgeEnvelope(counterState)
            Spacer(Modifier.preferredHeight(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                AddEmailButton(counterState, counterStateCb)
                Spacer(Modifier.preferredWidth(8.dp))
                DeleteEmailButton(counterState, counterStateCb)
            }
        }
    }

