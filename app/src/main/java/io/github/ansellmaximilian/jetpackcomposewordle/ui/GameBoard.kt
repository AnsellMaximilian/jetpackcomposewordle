package io.github.ansellmaximilian.jetpackcomposewordle.ui

import android.widget.Toast
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun GameBoard(gameState: GameState = viewModel()) {
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Wordle")
        Text(text = gameState.currentWord)

        Column(
            modifier =  Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(6) {
                val word = gameState.userGuesses.getOrNull(it)
                val isSubmitted = (gameState.userGuesses.size - 1) > it || gameState.isGameOver
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    repeat(5) { letterIdx ->
                        val letter = word?.getOrNull(letterIdx)?.toString() ?: ""
                        val isCorrectLetter = isSubmitted && gameState.currentWord.contains(letter, ignoreCase = true)
                        val isCorrect = isSubmitted && letter.isNotEmpty() && letter.equals(gameState.currentWord[letterIdx].toString(), ignoreCase = true)

                        OutlinedTextField(
                            value = letter,
                            onValueChange = {},
                            modifier = Modifier.size(50.dp),
                            textStyle = TextStyle(textAlign = TextAlign.Center, fontWeight = FontWeight.Bold),
                            singleLine = true,
                            readOnly = true,
                            enabled = false,
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledContainerColor = if (isSubmitted) {
                                    when {
                                        isCorrect -> Color(0xFF2E7D32)
                                        isCorrectLetter -> Color(0xFFF9A825)
                                        else -> Color(0xFF424242)
                                    }
                                } else Color.White,
                                disabledTextColor = if(isSubmitted) Color.White else Color.Black,
                            ),

                        )
                    }
                }
            }
        }
        TextField(
            value = gameState.userGuesses[gameState.userGuesses.size - 1],
            onValueChange = {
                val res = gameState.updateCurrentUserGuess(it)

                if(res != null) Toast.makeText(context, res, Toast.LENGTH_SHORT).show()

            },
            modifier = Modifier
                .width(1.dp)
                .height(1.dp)
                .alpha(0f)
                .focusable()
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Characters,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { gameState.submitCurrentUserGuess() }
            )

        )
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

}