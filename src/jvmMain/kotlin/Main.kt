import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay

val flatland = Flatland(50, 50, initProbability = 300)

const val CELL_SIZE = 10F

val cellSize = Size(CELL_SIZE, CELL_SIZE)
@Composable
@Preview
fun App() {
    var matrix by remember { mutableStateOf(flatland.toMatrix()) }
    LaunchedEffect(Unit) {
        repeat(10000) {
            delay(60)
            flatland.update()
            matrix = flatland.toMatrix()
        }
    }
    MaterialTheme {
        Canvas(modifier = Modifier.fillMaxSize()) {

            matrix.forEachIndexed {rIdx, row ->
                row.forEachIndexed {cIdx, cell ->
                    if (cell == 1) {
                        drawRect(
                            color = Color.Black,
                            size = cellSize,
                            topLeft = Offset((CELL_SIZE + 2) * cIdx, (CELL_SIZE + 2) * rIdx),
                        )
                    }
                }
            }
        }
    }
}

fun clearConsole() {
    Runtime.getRuntime().exec("clear");
}
fun main() = application {

    Window(onCloseRequest = ::exitApplication) {
        App()
    }
//    repeat(1000) {
//        Thread.sleep(16)
//        clearConsole()
//        flatland.update()
//    }
}
