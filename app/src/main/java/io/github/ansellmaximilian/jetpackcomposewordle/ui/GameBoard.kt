package io.github.ansellmaximilian.jetpackcomposewordle.ui

import android.widget.Toast
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.window.DialogProperties
import io.github.ansellmaximilian.jetpackcomposewordle.data.Correctness
import io.github.ansellmaximilian.jetpackcomposewordle.data.MAX_CHANCES
import io.github.ansellmaximilian.jetpackcomposewordle.data.MAX_WORD_LENGTH


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
            repeat(MAX_CHANCES) {
                val word = gameState.userGuesses.getOrNull(it)
                val isSubmitted = (gameState.userGuesses.size - 1) > it || gameState.isGameOver

                val targetWord = gameState.currentWord.uppercase()
                val wordFrequency = targetWord.groupingBy { letter -> letter }.eachCount().toMutableMap()

                val correctness: MutableList<Correctness> = MutableList(5) { Correctness.INCORRECT }

                word?.forEachIndexed { idx, char ->
                    if (char.equals(targetWord[idx], ignoreCase = true)) {
                        correctness[idx] = Correctness.CORRECT
                        wordFrequency[char] = wordFrequency[char]?.minus(1) ?: 0
                    }
                }

                word?.forEachIndexed { idx, char ->
                    if (correctness[idx] == Correctness.INCORRECT && wordFrequency[char] != null && wordFrequency[char]!! > 0) {
                        correctness[idx] = Correctness.PLACEMENT
                        wordFrequency[char] = wordFrequency[char]?.minus(1) ?: 0
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    repeat(MAX_WORD_LENGTH) { letterIdx ->
                        val letter = word?.getOrNull(letterIdx)?.toString() ?: ""

                        val backgroundColor = when (correctness[letterIdx]) {
                            Correctness.CORRECT -> Color(0xFF2E7D32)
                            Correctness.PLACEMENT -> Color(0xFFF9A825)
                            else -> Color(0xFF424242)
                        }

                        OutlinedTextField(
                            value = letter,
                            onValueChange = {},
                            modifier = Modifier.size(50.dp),
                            textStyle = TextStyle(textAlign = TextAlign.Center, fontWeight = FontWeight.Bold),
                            singleLine = true,
                            readOnly = true,
                            enabled = false,
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledContainerColor = if (isSubmitted) backgroundColor else Color.White,
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

        if(gameState.isGameOver) GameOverDialog(onReset = { gameState.reset() }, isWin = gameState.checkWin())
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
fun GameOverDialog(onReset: () -> Unit, isWin: Boolean, modifier: Modifier = Modifier) {
    AlertDialog(
        onDismissRequest = {},
        modifier = modifier,
        title = {
            Text(text = "Game Over")
        },
        text = {
            Text(text = if(isWin) "You Win!" else "Better Luck Next Time!", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth() )
        },
        confirmButton = {
            TextButton(onClick = onReset) {
                Text(text = "Play Again")
            }
        },
        dismissButton = {
            TextButton(onClick = {  }) {
                Text(text = "Back")
            }
        },
        icon = {
            Icon(imageVector = Icons.Filled.Star, contentDescription = null)
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    )
}