package com.matiyelicich.coinero.common.ext

fun putO(value: Int): String {
    return if (value < 10) {
        "0${value}"
    } else {
        value.toString()
    }
}

fun punctuation(value: String): String {

    val twoDecimals = value.takeLastWhile { it != '.' }.take(2)
    val decimals = if (twoDecimals.toInt() < 1) "" else ",$twoDecimals"

    val dotIndex = value.indexOf(".")
    val enters = value.substring(0, dotIndex)

    val hundred = enters.takeLast(3)
    val thousand = enters.takeLast(6).take(3)

    val ones = value.take(1)
    val twos = value.take(2)
    val threes = value.take(3)

    val points = when (enters.length) {
        1 -> "${ones}$decimals"
        2 -> "${twos}$decimals"
        3 -> "${threes}$decimals"
        4 -> "${ones}.${hundred}$decimals"
        5 -> "${twos}.${hundred}$decimals"
        6 -> "${threes}.${hundred}$decimals"
        7 -> "${ones}.${thousand}.${hundred}$decimals"
        8 -> "${twos}.${thousand}.${hundred}$decimals"
        9 -> "${threes}.${thousand}.${hundred}$decimals"
        else -> value
    }
    return "-$${points}"
}

fun twoPoints(value: Double): Double {

    val stringValue = value.toString()

    return if (stringValue.contains(".")) {
        val dotIndex = stringValue.indexOf(".")
        val enters = stringValue.substring(0, dotIndex)
        val twoDecimals = stringValue.takeLastWhile { it != '.' }.take(2)
        "${enters}.${twoDecimals}".toDouble()
    } else {
        value
    }
}

fun onlyTwoPoints(value: String): String {

    return if (value.contains(".")) {
        val dotIndex = value.indexOf(".")
        val enters = value.substring(0, dotIndex)
        val twoDecimals = value.takeLastWhile { it != '.' }.take(2)
        "${enters}.${twoDecimals}"
    } else {
        value
    }
}

fun sortedDate(value: String): String {

    val dateList: List<String> = value.split("/")
    val day: String = dateList[0]
    val month: String = dateList[1]
    val year: String = dateList[2]

    return "$year$month$day"
}
