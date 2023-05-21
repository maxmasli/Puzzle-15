package masli.prof.puzzle15

import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.mock

class ExampleUnitTest {
    @Test
    fun getTileTest() {
        val mockEngine = mock(Puzzle15Engine::class.java)
        mockEngine.newPuzzle()
        val tiles = mutableListOf<Int>()
        for (i in 0..3) {
            for (j in 0..3) {
                tiles.add(mockEngine.getTile(i, j))
            }
        }

        assertEquals(tiles.size, mockEngine.puzzleSize*mockEngine.puzzleSize)

    }
}