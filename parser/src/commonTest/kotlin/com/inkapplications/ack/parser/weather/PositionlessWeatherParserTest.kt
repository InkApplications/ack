package com.inkapplications.ack.parser.weather

import com.inkapplications.ack.parser.TestData
import com.inkapplications.ack.parser.timestamp.withUtcValues
import com.inkapplications.ack.structures.PacketData
import com.inkapplications.ack.structures.Precipitation
import com.inkapplications.ack.structures.WindData
import inkapplications.spondee.measure.*
import inkapplications.spondee.scalar.WholePercentage
import inkapplications.spondee.spatial.Degrees
import inkapplications.spondee.structure.Deka
import inkapplications.spondee.structure.of
import kotlinx.datetime.Clock
import kotlinx.datetime.Month
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNull

class PositionlessWeatherParserTest {
    private val parser = PositionlessWeatherTransformer(TestData.timestampModule)
    
    @Test
    fun parse() {
        val given = "_10090556c220s004g005t077r001p002P003h50b09900wRSW"

        val result = parser.parse(given)
        val expectedTime = Clock.System.now()
            .withUtcValues(
                month = Month.OCTOBER,
                dayOfMonth = 9,
                hour = 5,
                minute = 56,
                second = 0,
                nanosecond = 0
            )

        assertEquals(expectedTime, result.timestamp)
        assertEquals(Degrees.of(220), result.windData.direction)
        assertEquals(MilesPerHour.of(4), result.windData.speed)
        assertEquals(MilesPerHour.of(5), result.windData.gust)
        assertEquals(HundredthInches.of(1), result.precipitation.rainLastHour)
        assertEquals(HundredthInches.of(2), result.precipitation.rainLast24Hours)
        assertEquals(HundredthInches.of(3), result.precipitation.rainToday)
        assertEquals(WholePercentage.of(50), result.humidity)
        assertEquals(Pascals.of(Deka, 9900), result.pressure)
        assertNull(result.irradiance)
        assertNull(result.symbol)
        assertNull(result.coordinates)
    }

    @Test
    fun empty() {
        val given = "_10090556c...s   g...t...P012Jim"

        val result = parser.parse(given)
        val expectedTime = Clock.System.now()
            .withUtcValues(
                month = Month.OCTOBER,
                dayOfMonth = 9,
                hour = 5,
                minute = 56,
                second = 0,
                nanosecond = 0
            )

        assertEquals(expectedTime, result.timestamp)
        assertNull(result.windData.direction)
        assertNull(result.windData.speed)
        assertNull(result.windData.gust)
        assertNull(result.precipitation.rainLastHour)
        assertNull(result.precipitation.rainLast24Hours)
        assertEquals(HundredthInches.of(12), result.precipitation.rainToday)
        assertNull(result.humidity)
        assertNull(result.pressure)
        assertNull(result.irradiance)
        assertNull(result.symbol)
        assertNull(result.coordinates)
    }

    @Test
    fun nonWeather() {
        val given = ">Hello World"

        assertFails { parser.parse(given) }
    }

    @Test
    fun generate() {
        val given = PacketData.Weather(
            Clock.System.now().withUtcValues(
                month = Month.OCTOBER,
                dayOfMonth = 9,
                hour = 5,
                minute = 56,
                second = 0,
                nanosecond = 0
            ),
            windData = WindData(
                direction = Degrees.of(220),
                speed = MilesPerHour.of(4),
                gust = MilesPerHour.of(5),
            ),
            precipitation = Precipitation(
                rainLastHour = HundredthInches.of(1),
                rainLast24Hours = HundredthInches.of(2),
                rainToday = HundredthInches.of(3),
                snowLast24Hours = Inches.of(4),
                rawRain = 789,
            ),
            coordinates = null,
            symbol = null,
            temperature = Fahrenheit.of(77),
            humidity = WholePercentage.of(50),
            pressure = Pascals.of(Deka, 9900),
            irradiance = WattsPerSquareMeter.of(69),
        )

        val result = parser.generate(given)

        assertEquals("_10090556c220s004g005t077r001p002P003h50b09900L069s004#789", result)
    }

    @Test
    fun generateEmpty() {
        val given = PacketData.Weather(
            Clock.System.now().withUtcValues(
                month = Month.OCTOBER,
                dayOfMonth = 9,
                hour = 5,
                minute = 56,
                second = 0,
                nanosecond = 0
            ),
            windData = WindData(null, null, null),
            precipitation = Precipitation(null, null, null),
            coordinates = null,
            symbol = null,
            temperature = null,
            humidity = null,
            pressure = null,
            irradiance = null,
        )

        val result = parser.generate(given)

        assertEquals("_10090556c...s...g...t...", result)
    }

    @Test
    fun generateSecondTierIrradiance() {
        val given = PacketData.Weather(
            Clock.System.now().withUtcValues(
                month = Month.OCTOBER,
                dayOfMonth = 9,
                hour = 5,
                minute = 56,
                second = 0,
                nanosecond = 0
            ),
            windData = WindData(null, null, null),
            precipitation = Precipitation(null, null, null),
            coordinates = null,
            symbol = null,
            temperature = null,
            humidity = null,
            pressure = null,
            irradiance = WattsPerSquareMeter.of(1025),
        )

        val result = parser.generate(given)

        assertEquals("_10090556c...s...g...t...l025", result)
    }
}