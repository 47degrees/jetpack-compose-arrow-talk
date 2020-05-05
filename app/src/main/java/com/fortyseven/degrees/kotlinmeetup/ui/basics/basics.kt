package com.fortyseven.degrees.kotlinmeetup.ui.basics

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.padding
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

@Composable
fun Greeting(name: String) {
    Text("Hello $name")
}

@Preview("Greeting")
@Composable
private fun previewExample1() {
    Greeting(name = "Kotlin folks!")
}

@Preview("Multi Greeting")
@Composable
private fun previewExample2() {
    Greeting(name = "47 Degrees!")
    Greeting(name = "San Fernando!")
    Greeting(name = "Madrid!")
    Greeting(name = "London!")
    Greeting(name = "Seattle!")
}

@Preview("Multi Greeting fixed")
@Composable
private fun previewExample3() {
    Column(modifier = Modifier.padding(8.dp)) {
        Greeting(name = "47 Degrees!")
        Greeting(name = "London!")
        Greeting(name = "San Fernando!")
        Greeting(name = "Madrid!")
        Greeting(name = "Seattle!")
    }
}
