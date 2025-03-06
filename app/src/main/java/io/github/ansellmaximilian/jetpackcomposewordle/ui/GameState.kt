package io.github.ansellmaximilian.jetpackcomposewordle.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import io.github.ansellmaximilian.jetpackcomposewordle.data.MAX_CHANCES
import io.github.ansellmaximilian.jetpackcomposewordle.data.MAX_WORD_LENGTH
import io.github.ansellmaximilian.jetpackcomposewordle.data.words

class GameState : ViewModel() {
    private var _userGuesses: MutableList<String> = mutableStateListOf("")
    var currentWord: String = words.random()
        private set

    val userGuesses: List<String>
        get() = _userGuesses

    var isGameOver: Boolean = false
        private set

    fun updateCurrentUserGuess(guess: String): String? {
        if(guess.length <= MAX_WORD_LENGTH) {
            _userGuesses[_userGuesses.size - 1] = guess
            return null
        }
        return "Enough!"
    }

    fun submitCurrentUserGuess(){
        if(_userGuesses.size < MAX_CHANCES) {
            _userGuesses.add("")
        }
    }
}