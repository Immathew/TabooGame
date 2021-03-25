package com.example.taboogame.data

import com.example.taboogame.models.GuessWord

object WordsToGuessListSPANISH {

    fun allWordsSPANISH(): ArrayList<GuessWord> {
        val wordsToGuessList = ArrayList<GuessWord>()

        val word = GuessWord(
                0,
                "CRISTAL",
                "BOLA",
                "VASO",
                "COCINA",
                "LUJOSO"
        )
        wordsToGuessList.add(word)

        val word1 = GuessWord(
                1,
                "HOMBRO",
                "CUERPO",
                "PARTE",
                "CABEZA",
                "LLEVAR"
        )
        wordsToGuessList.add(word1)

        val word2 = GuessWord(
                2,
                "MOTOR",
                "MOTO",
                "COCHE",
                "MOTOCICLETA",
                "ENTREGAR"
        )
        wordsToGuessList.add(word2)

        val word3 = GuessWord(
                3,
                "TABLA DE SURF",
                "CAIDA DIEZ",
                "FIBRA DE VIDRIO",
                "OLA",
                "MADERA"
        )
        wordsToGuessList.add(word3)

        val word4 = GuessWord(
                4,
                "SUEGRA",
                "MARIDO",
                "ESPOSA",
                "PADRE",
                "HERMANO"
        )
        wordsToGuessList.add(word4)

        val word5 = GuessWord(
                5,
                "OBESO",
                "VIENTRE",
                "GORDO",
                "GRANDE",
                "REGORDETE"
        )
        wordsToGuessList.add(word5)

        val word6 = GuessWord(
                6,
                "ATREVIMIENTO",
                "VALIENTE",
                "VERDAD",
                "HACER",
                "DIABLO"
        )
        wordsToGuessList.add(word6)

        val word7 = GuessWord(
                7,
                "VAINILLA",
                "EXTRAER",
                "SABOR",
                "LLANURA",
                "HELADO"
        )
        wordsToGuessList.add(word7)

        val word8 = GuessWord(
                8,
                "CAVIAR",
                "BOLAS",
                "NEGRO",
                "LUJO",
                "COSTOSO"
        )
        wordsToGuessList.add(word8)

        val word9 = GuessWord(
                9,
                "LEPRACHAUN",
                "ORO",
                "COLORES",
                "ARCO IRIS",
                "LLUVIA"
        )
        wordsToGuessList.add(word9)

        wordsToGuessList.shuffle()
        return wordsToGuessList
    }
}