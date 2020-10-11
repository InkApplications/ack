package com.inkapplications.karps.parser.extension

import com.inkapplications.karps.structures.unit.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class TransmitterInfoExtensionChunkerTest {
    @Test
    fun validInfo() {
        val given = "PHG5132Test"

        val result = TransmitterInfoExtensionChunker.popChunk(given)

        assertEquals(25.watts, result.result.value.power)
        assertEquals(20.feet, result.result.value.height)
        assertEquals(3.decibels, result.result.value.gain)
        assertEquals(Cardinal.East.degreesBearing, result.result.value.direction)
        assertEquals("Test", result.remainingData)
    }

    @Test
    fun invalidInfo() {
        val given = "PHGHello World"

        assertFails("Should not parse non-numbers") { TransmitterInfoExtensionChunker.popChunk(given) }
    }

    @Test
    fun missingControl() {
        val given = "RNG1234Test"

        assertFails("Should not parse if control is wrong") { TransmitterInfoExtensionChunker.popChunk(given) }
    }
}