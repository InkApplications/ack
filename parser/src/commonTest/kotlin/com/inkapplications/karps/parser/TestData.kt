package com.inkapplications.karps.parser

import com.inkapplications.karps.structures.*
import com.inkapplications.karps.structures.unit.*
import com.soywiz.klock.DateTime

object TestData {
    val now = DateTime.now()

    object Position {
        const val string = "KV4JW>APDR15,TCPIP*,qAC,T2HUN:=3746.72N/08402.19W\$112/002/A=000761 https://aprsdroid.org/"
        val expected = AprsPacket.Position(
            received = now,
            dataTypeIdentifier = '=',
            source = Address("KV4JW"),
            destination = Address("APDR15"),
            coordinates = Coordinates(
                Latitude(37, 46, 43.2f, Cardinal.North),
                Longitude(84, 2, 11.4f, Cardinal.West)
            ),
            digipeaters = listOf(
                Digipeater(Address("TCPIP"), heard = true),
                Digipeater(Address("qAC")),
                Digipeater(Address("T2HUN"))
            ),
            symbol = Symbol.Primary('$'),
            comment = "112/002/A=000761 https://aprsdroid.org/",
            timestamp = null
        )
    }

    object CompressedPosition {
        const val string = "REDKNL>APOT30,KE7JVX-10*,WIDE2-1,qAR,K7YI-4:!S;an%2#Co# st130F N7YSE Red Knoll"
        val expected = AprsPacket.Position(
            received = now,
            dataTypeIdentifier = '!',
            source = Address("REDKNL"),
            destination = Address("APOT30"),
            coordinates = Coordinates(
                Latitude(37, 9, 19.83f, Cardinal.North),
                Longitude(112, 38, 7.87f, Cardinal.West)
            ),
            digipeaters = listOf(
                Digipeater(Address("KE7JVX", "10"), heard = true),
                Digipeater(Address("WIDE2", "1")),
                Digipeater(Address("qAR")),
                Digipeater(Address("K7YI", "4"))
            ),
            symbol = Symbol.Alternate('#', overlay = 'S'),
            comment = " st130F N7YSE Red Knoll",
            timestamp = null
        )
    }

    object PositionlessWeather {
        const val string = "W0YC-5>APX200,TCPIP*,qAC,SEVENTH:_10090556c220s004g005t077r002p006P004h50b09900l234wRSW"
        val expected = AprsPacket.Weather(
            received = now,
            dataTypeIdentifier = '_',
            source = Address("W0YC", "5"),
            destination = Address("APX200"),
            digipeaters = listOf(
                Digipeater(Address("TCPIP"), heard = true),
                Digipeater(Address("qAC")),
                Digipeater(Address("SEVENTH"))
            ),
            windData = WindData(
                direction = 220.degreesBearing,
                speed = 4.mph,
                gust = 5.mph
            ),
            precipitation = Precipitation(
                rainLastHour = 2.hundredthsOfInch,
                rainLast24Hours = 6.hundredthsOfInch,
                rainToday = 4.hundredthsOfInch
            ),
            temperature = 77.degreesFahrenheit,
            humidity = 50.percent,
            pressure = 9900.decapascals,
            irradiance = 1234.wattsPerSquareMeter
        )
    }

    object CompleteWeather {
        const val string = "W0YC-5>APX200,TCPIP*,qAC,SEVENTH:@092345z4903.50N/07201.75W_220/004g005t-07r002p006P004h50b09900l234wRSW"
        val expected = AprsPacket.Weather(
            received = now,
            dataTypeIdentifier = '@',
            source = Address("W0YC", "5"),
            destination = Address("APX200"),
            digipeaters = listOf(
                Digipeater(Address("TCPIP"), heard = true),
                Digipeater(Address("qAC")),
                Digipeater(Address("SEVENTH"))
            ),
            windData = WindData(
                direction = 220.degreesBearing,
                speed = 4.mph,
                gust = 5.mph
            ),
            precipitation = Precipitation(
                rainLastHour = 2.hundredthsOfInch,
                rainLast24Hours = 6.hundredthsOfInch,
                rainToday = 4.hundredthsOfInch
            ),
            temperature = (-7).degreesFahrenheit,
            humidity = 50.percent,
            pressure = 9900.decapascals,
            irradiance = 1234.wattsPerSquareMeter
        )
    }
}
