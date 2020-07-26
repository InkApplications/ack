package com.inkapplications.karps.structures.unit

import com.inkapplications.karps.structures.unit.Cardinal.*

/**
 * Direction represented as an angle in degrees.
 */
inline class Bearing(val degrees: Short)

/**
 * Convert a number of absolute degrees to a [Bearing].
 */
val Number.degreesBearing get() = toShort().let(::Bearing)

/**
 * Get the absolute bearing of a cardinal direction.
 */
val Cardinal.degreesBearing get() = when (this) {
    North -> 0.degreesBearing
    East -> 90.degreesBearing
    South -> 180.degreesBearing
    West -> 270.degreesBearing
}