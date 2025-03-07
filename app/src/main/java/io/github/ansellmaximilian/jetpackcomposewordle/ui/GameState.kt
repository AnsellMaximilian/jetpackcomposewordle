package io.github.ansellmaximilian.jetpackcomposewordle.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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

    private var _isGameOver = mutableStateOf(false)

    val isGameOver: Boolean get() = _isGameOver.value

    fun updateCurrentUserGuess(guess: String): String? {
        if(guess.length <= MAX_WORD_LENGTH) {
            _userGuesses[_userGuesses.size - 1] = guess
            return null
        }
        return "Enough!"
    }

    fun submitCurrentUserGuess(){
        if(_userGuesses.last().length != MAX_WORD_LENGTH) return

        if(checkWin()) return

        if(_userGuesses.size < MAX_CHANCES) _userGuesses.add("")
    }

    fun checkWin(): Boolean {
        val res = _userGuesses.last().equals(currentWord, ignoreCase = true)

        if(res || _userGuesses.size >= MAX_CHANCES) _isGameOver.value = true

        return res
    }

    fun reset() {
        _isGameOver.value = false
        _userGuesses.clear()
        _userGuesses.add("")
        currentWord = words.random()
    }

}