package io.github.ansellmaximilian.jetpackcomposewordle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.ansellmaximilian.jetpackcomposewordle.ui.theme.JetpackComposeWordleTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.ansellmaximilian.jetpackcomposewordle.ui.GameBoard


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeWordleTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GameBoard()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    JetpackComposeWordleTheme {
        GameBoard()
    }
}