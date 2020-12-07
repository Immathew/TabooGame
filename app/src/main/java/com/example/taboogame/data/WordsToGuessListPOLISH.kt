package com.example.taboogame.data

object WordsToGuessListPOLISH {

    fun allWordsPOLISH(): ArrayList<GuessWord> {
        val wordsToGuessList = ArrayList<GuessWord>()

        val word = GuessWord(
            0,
            "FACHOWIEC",
            "ZNAWCA",
            "SPECJALISTA",
            "DOBRY",
            "NAPRAWA"
        )
        wordsToGuessList.add(word)

        val word1 = GuessWord(
            1,
            "TWARÓG",
            "KROWA",
            "SZCZYPIOREK",
            "SER BIAŁY",
            "ŚMIETANA"
        )
        wordsToGuessList.add(word1)

        val word2 = GuessWord(
            2,
            "TĘCZA",
            "DESZCZ",
            "KOLORY",
            "SŁONCE",
            "NIEBO"
        )
        wordsToGuessList.add(word2)

        val word3 = GuessWord(
            3,
            "TATAR",
            "SUROWE",
            "MIĘSO",
            "POTRAWA",
            "WOŁOWINA"
        )
        wordsToGuessList.add(word3)

        val word4 = GuessWord(
            4,
            "ALBINOS",
            "SKÓRA",
            "BIAŁACTWO",
            "BARWNIK",
            "KOLOR"
        )
        wordsToGuessList.add(word4)

        val word5 = GuessWord(
            5,
            "KAKTUS",
            "PUSTYNIA",
            "KOLCE",
            "BEZ WODY",
            "ZIELONY"
        )
        wordsToGuessList.add(word5)

        val word6 = GuessWord(
            6,
            "KISZKA",
            "SLEPA",
            "ZIEMNIACZNA",
            "JELITO",
            "KASZANKA"
        )
        wordsToGuessList.add(word6)

        val word7 = GuessWord(
            7,
            "STOPKA",
            "NOGA",
            "GAZETA",
            "SKARPETKA",
            "REDAKCYJNA"
        )
        wordsToGuessList.add(word7)

        val word8 = GuessWord(
            8,
            "DYLEMAT",
            "KŁOPOT",
            "NIEPEWNOŚĆ",
            "WYBÓR",
            "PROBLEM"
        )
        wordsToGuessList.add(word8)

        val word9 = GuessWord(
            9,
            "EKSPERYMENT",
            "SZCZUR",
            "DOŚWIADCZENIE",
            "LABORATORIUM",
            "WYBUCH"
        )
        wordsToGuessList.add(word9)

        wordsToGuessList.shuffle()
        return wordsToGuessList
    }
}