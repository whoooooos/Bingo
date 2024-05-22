package com.example.bingo.viewmodels

import androidx.lifecycle.ViewModel
import java.util.Random

class MainActivityViewModel: ViewModel() {
    private val randNumbers = ArrayList<Int>()
    private val calledNumber = ArrayList<Int>()

    fun generateNumbers() {
        val random = Random()
        while (randNumbers.size < 75) {
            val a = random.nextInt(75) + 1 // this will give numbers between 1 and 75.
            if (!randNumbers.contains(a)) {
                randNumbers.add(a)
            }
        }
    }

    fun getCalledNumbers(): ArrayList<Int> {
        return calledNumber
    }

    fun getNumber(): Int {
        val number = randNumbers.random()
        randNumbers.remove(number)
        calledNumber.add(number)
        return number
    }
}