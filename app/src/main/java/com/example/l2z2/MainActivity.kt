package com.example.l2z2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.l2z2.databinding.ActivityMainBinding
import com.example.l2z2.databinding.StartBinding
import java.util.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var startBinding: StartBinding

    private var guessWord: String = ""
    private var encodedWord: String = ""
    private var imageId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        startBinding = StartBinding.inflate(layoutInflater)

        setContentView(startBinding.root)
    }

    fun start(v: View) {
        setContentView(binding.root)
        createGame()
    }

    private fun createGame() {
        guessWord = getRandomWord()
        binding.guessWord.text = encode()
    }

    private fun getRandomWord(): String {
        val words = resources.getStringArray(R.array.words)
        val randomValue = Random.nextInt(words.size)
        return words[randomValue]
    }

    private fun encode(): String {
        for (l in guessWord)
            encodedWord += ("?")
        return encodedWord
    }

    fun checkLetter(v: View) {
        val b = v as Button
        val oldEncodedWord = encodedWord
        for (i in guessWord.indices){
            if (guessWord[i].toString().toUpperCase(Locale.ROOT) == b.text) {
                encodedWord = StringBuilder(encodedWord).also { it.setCharAt(i, guessWord[i])}.toString()
                binding.guessWord.text = encodedWord
            }
        }

        if (checkWin()){
            Toast.makeText(applicationContext, "You won!", Toast.LENGTH_SHORT).show()
        } else if (checkWord(oldEncodedWord)) {
            imageId++
        }
    }

    private fun checkWin(): Boolean {
        return guessWord == encodedWord
    }

    private fun checkWord(old: String): Boolean {
        if (encodedWord == old){
            val image = resources.getIdentifier("_$imageId", "drawable", packageName)
            binding.hangmanView.setImageResource(image)
            if (imageId == 11) {
                Toast.makeText(applicationContext, "You lose!", Toast.LENGTH_SHORT).show()
                binding.guessWord.text = guessWord
                return false
            }
            return true
        }
        return false
    }

    fun end(v: View) {
        recreate()
        imageId = 0
        setContentView(startBinding.root)
    }
}