package com.inkapplications.karps.parser.chunk.common

import com.inkapplications.karps.parser.chunk.Chunk
import com.inkapplications.karps.parser.chunk.Chunker

/**
 * Splits the entirety of remaining data into segments using a character separator.
 */
internal class CsvChunker(
    private val separator: Char,
): Chunker<List<String>> {
    override fun popChunk(data: String): Chunk<out List<String>> {
        return Chunk(data.split(separator), "")
    }
}