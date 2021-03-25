package com.example.taboogame.data

import com.example.taboogame.models.GuessWord

object WordsToGuessListENGLISH {

    fun allWordsENGLISH(): ArrayList<GuessWord> {
        val wordsToGuessList = ArrayList<GuessWord>()

        val word = GuessWord(
                0,
                "OUT",
                "INSIDE",
                "BASEBALL",
                "BOX",
                "CRICKETT"
        )
        wordsToGuessList.add(word)

        val word1 = GuessWord(
                1,
                "NELSON MANDELA",
                "MADIBA",
                "SOUTH AFRICA",
                "ROBBEN ISLAND",
                "APARTHEID"
        )
        wordsToGuessList.add(word1)

        val word2 = GuessWord(
                2,
                "SURGEON",
                "OPERATE",
                "DOCTOR",
                "HOSPITAL",
                "STITCH"
        )
        wordsToGuessList.add(word2)

        val word3 = GuessWord(
                3,
                "BOREDOM",
                "NOTHING",
                "FUN",
                "SLOW",
                "ACTIVITY"
        )
        wordsToGuessList.add(word3)

        val word4 = GuessWord(
                4,
                "STRIKE",
                "BOWL",
                "HIT",
                "BALL",
                "OUT"
        )
        wordsToGuessList.add(word4)

        val word5 = GuessWord(
                5,
                "FOIL",
                "CHANGE",
                "WRENCH",
                "DISRUPT",
                "TIME"
        )
        wordsToGuessList.add(word5)

        val word6 = GuessWord(
                6,
                "ELASTIC DEMAND",
                "JEWELERY",
                "NON-ESSENTIALS",
                "BRANDS",
                "RUBBER"
        )
        wordsToGuessList.add(word6)

        val word7 = GuessWord(
                7,
                "PURPOSE",
                "REASON",
                "BEHIND",
                "FAULT",
                "TIMING"
        )
        wordsToGuessList.add(word7)

        val word8 = GuessWord(
                8,
                "EXPLAIN",
                "TELL",
                "SAY",
                "SPEAK",
                "COMMUNICATE"
        )
        wordsToGuessList.add(word8)

        val word9 = GuessWord(
                9,
                "TRANSLATION",
                "REPHRASE",
                "LOST",
                "LANGUAGE",
                "INTERPRET"
        )
        wordsToGuessList.add(word9)

        wordsToGuessList.shuffle()
        return wordsToGuessList
    }
}
