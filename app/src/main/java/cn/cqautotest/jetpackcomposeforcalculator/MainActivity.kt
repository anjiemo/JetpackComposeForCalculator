package cn.cqautotest.jetpackcomposeforcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cn.cqautotest.jetpackcomposeforcalculator.ui.theme.JetpackComposeForCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeForCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    HomeScreen()
                }
            }
        }
    }
}

private const val TAG = "MainActivity"

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeForCalculatorTheme {
        HomeScreen()
    }
}

@Composable
private fun HomeScreen() {
    var text by remember { mutableStateOf("0") }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // input box
        Row(
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 0.dp)
                .align(alignment = Alignment.End)
        ) {
            InputComponent(text)
        }
        // number keyboard panel
        Box(modifier = Modifier.padding(10.dp)) {
            NumericKeypadPanel { key ->
                text = key
            }
        }
    }
}

@Composable
fun NumericKeypadPanel(onKeyClick: (text: String) -> Unit) {
    val keyArr = arrayOf(
        arrayOf("AC", "DEL", "%", "÷"),
        arrayOf("7", "8", "9", "×"),
        arrayOf("4", "5", "6", "-"),
        arrayOf("1", "2", "3", "+"),
        arrayOf("0", ".", "=", "Exit"),
    )
    NumericKeypadGridPanel(keyArr = keyArr, onKeyClick = onKeyClick)
}

@Composable
fun InputComponent(text: String) {
    Text(text = text, modifier = Modifier.background(color = Color.Transparent))
}

@Composable
fun NumericKeypadGridPanel(keyArr: Array<Array<String>>, onKeyClick: (text: String) -> Unit) {
    Column {
        keyArr.forEach {
            Row {
                val lastIndex = it.lastIndex
                it.forEachIndexed { index, key ->
                    Button(
                        onClick = { onKeyClick.invoke(key) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                    ) {
                        Text(text = key)
                    }
                    takeUnless { index == lastIndex }?.let { Spacer(modifier = Modifier.width(14.dp)) }
                }
            }
        }
    }
}