package com.example.taboogame.data

object WordsToGuessList {

    fun allWords(): ArrayList<GuessWord> {
        val wordsToGuessList = ArrayList<GuessWord>()

        val word = GuessWord(
            0,
            "Jeden",
            "1",
            "2",
            "3",
            "4"
        )
        wordsToGuessList.add(word)

        val word1 = GuessWord(
            1,
            "Dwa",
            "5",
            "6",
            "7",
            "8"
        )
        wordsToGuessList.add(word1)

        val word2 = GuessWord(
            2,
            "Trzy",
            "9",
            "10",
            "11",
            "12"
        )
        wordsToGuessList.add(word2)

        val word3 = GuessWord(
            3,
            "Cztery",
            "13",
            "14",
            "15",
            "16"
        )
        wordsToGuessList.add(word3)

        val word4 = GuessWord(
            4,
            "Pięc",
            "17",
            "18",
            "19",
            "20"
        )
        wordsToGuessList.add(word4)

        val word5 = GuessWord(
            5,
            "szesc",
            "21",
            "22",
            "23",
            "24"
        )
        wordsToGuessList.add(word5)

        val word6 = GuessWord(
            6,
            "siedem",
            "25",
            "26",
            "27",
            "28"
        )
        wordsToGuessList.add(word6)

        val word7 = GuessWord(
            7,
            "osiem",
            "30",
            "31",
            "32",
            "33"
        )
        wordsToGuessList.add(word7)

        val word8 = GuessWord(
            8,
            "dziewiec",
            "34",
            "35",
            "36",
            "37"
        )
        wordsToGuessList.add(word8)

        val word9 = GuessWord(
            9,
            "dziesiec",
            "38",
            "39",
            "40",
            "41"
        )
        wordsToGuessList.add(word9)

        wordsToGuessList.shuffle()
        return wordsToGuessList
    }

}