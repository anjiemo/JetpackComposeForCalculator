package cn.cqautotest.jetpackcomposeforcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.cqautotest.jetpackcomposeforcalculator.ui.theme.Background
import cn.cqautotest.jetpackcomposeforcalculator.ui.theme.DarkGray
import cn.cqautotest.jetpackcomposeforcalculator.ui.theme.LightGray
import cn.cqautotest.jetpackcomposeforcalculator.ui.theme.Orange

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calculator()
        }
    }
}

// 计算器按钮
val buttons = arrayOf(
    arrayOf("AC" to LightGray, "+/-" to LightGray, "%" to LightGray, "÷" to Orange),
    arrayOf("7" to DarkGray, "8" to DarkGray, "9" to DarkGray, "×" to Orange),
    arrayOf("4" to DarkGray, "5" to DarkGray, "6" to DarkGray, "-" to Orange),
    arrayOf("1" to DarkGray, "2" to DarkGray, "3" to DarkGray, "+" to Orange),
    arrayOf("0" to DarkGray, "." to DarkGray, "=" to Orange),
)

// 计算器状态
data class CalculatorState(val number1: Int = 0, val number2: Int = 0, val opt: String? = null)

// 计算逻辑
fun calculate(currState: CalculatorState, input: String): CalculatorState {
    return when (input) {
        in "0".."9" -> currState.copy(number2 = input.toInt(), number1 = currState.number2)
        in arrayOf("+", "-", "×", "÷") -> currState.copy(opt = input)
        "=" -> {
            when (currState.opt) {
                "+" -> currState.copy(number2 = currState.number1 + currState.number2, opt = null)
                "-" -> currState.copy(number2 = currState.number1 - currState.number2, opt = null)
                "×" -> currState.copy(number2 = currState.number1 * currState.number2, opt = null)
                "÷" -> currState.copy(number2 = currState.number1 / currState.number2, opt = null)
                else -> currState
            }
        }
        "AC" -> currState.copy(number1 = 0, number2 = 0, opt = null)
        else -> currState
    }
}

@Preview(showSystemUi = true)
@Composable
fun Calculator() {

    var state by remember {
        mutableStateOf(CalculatorState())
    }

    Column(
        modifier = Modifier
            .background(Background)
            .padding(horizontal = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.3f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(text = state.number2.toString(), fontSize = 100.sp, color = Color.White)
        }
        Column(modifier = Modifier.fillMaxSize()) {
            buttons.forEach {
                Row(
                    modifier = Modifier
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    it.forEach {
                        CalculatorButton(
                            modifier = Modifier
                                .weight(if (it.first == "0") 2f else 1f)
                                // 纵横比例
                                .aspectRatio(if (it.first == "0") 2f else 1f)
                                .background(it.second),
                            symbol = it.first
                        ) {
                            state = calculate(state, it.first)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(modifier: Modifier, symbol: String, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .then(modifier)
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = symbol, color = Color.White)
    }
}