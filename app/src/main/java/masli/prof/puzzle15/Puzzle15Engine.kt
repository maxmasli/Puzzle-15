package masli.prof.puzzle15

class Puzzle15Engine {

    /**
     * List contains numbers from 1 to 15
     * number 0 means a tile is free
     */
    private val field = mutableListOf<MutableList<Int>>()
    val puzzleSize = 4
    var isOver = false

    fun newPuzzle() {
        isOver = false
        generateField()
        shuffleField()
    }

    fun getTile(x: Int, y: Int): Int {
        return field[y][x]
    }

    fun clickAction(x: Int, y: Int) {
        if (isOver) return
        if (field[y][x] == 0) return
        //check up
        if (y - 1 >= 0 && field[y - 1][x] == 0) {
            moveTile(Direction.Top)
            return
        }
        //check down
        if (y + 1 < puzzleSize && field[y + 1][x] == 0) {
            moveTile(Direction.Bottom)
            return
        }
        //check right
        if (x + 1 < puzzleSize && field[y][x + 1] == 0) {
            moveTile(Direction.Right)
            return
        }
        //check left
        if (x - 1 >= 0 && field[y][x - 1] == 0) {
            moveTile(Direction.Left)
            return
        }
    }

    private fun moveTile(direction: Direction) {
        move(direction)
        if (checkField()) {
            println("YOU WIN!!!!")
            isOver = true
        }
    }

    private fun generateField() {
        field.clear()
        var counter = 1;
        for (y in 0 until puzzleSize) {
            field.add(y, mutableListOf())
            for (x in 0 until puzzleSize) {
                field[y].add(x, counter++)
                if (counter == puzzleSize * puzzleSize) {
                    counter = 0
                }
            }
        }
    }

    private fun shuffleField() {
        do {
            val shuffledList = (0..15).shuffled().toMutableList()
            for (y in 0 until puzzleSize) {
                for (x in 0 until puzzleSize) {
                    field[y][x] = shuffledList.removeFirst()
                }
            }
        } while (!isSolvable())
    }

    private fun move(direction: Direction) {
        for (y in 0 until puzzleSize) {
            for (x in 0 until puzzleSize) {
                if (field[y][x] == 0) {
                    when (direction) {
                        Direction.Top -> {
                            val movedTileIndex = y + 1
                            if (movedTileIndex > puzzleSize - 1) return

                            val tile = field[movedTileIndex][x]
                            field[y][x] = tile
                            field[movedTileIndex][x] = 0
                            return
                        }
                        Direction.Bottom -> {
                            val movedTileYIndex = y - 1
                            if (movedTileYIndex < 0) return
                            val tile = field[movedTileYIndex][x]
                            field[y][x] = tile
                            field[movedTileYIndex][x] = 0
                            return
                        }
                        Direction.Right -> {
                            val movedTileXIndex = x - 1
                            if (movedTileXIndex < 0) return
                            val tile = field[y][movedTileXIndex]
                            field[y][x] = tile
                            field[y][movedTileXIndex] = 0
                            return
                        }
                        Direction.Left -> {
                            val movedTileXIndex = x + 1
                            if (movedTileXIndex > puzzleSize - 1) return
                            val tile = field[y][movedTileXIndex]
                            field[y][x] = tile
                            field[y][movedTileXIndex] = 0
                            return
                        }
                    }
                }
            }
        }
    }

    private fun isSolvable(): Boolean {
        var countInversions = 0
        val flatten = field.flatten().toMutableList().apply {
            remove(0)
        }
        for (i in 0 .. puzzleSize) {
            for (j in 0 .. i) {
                if (flatten[j] > flatten[i]) countInversions++
            }
        }

        return countInversions % 2 == 0

    }

    private fun checkField(): Boolean {
        var counter = 1
        for (y in 0 until puzzleSize) {
            for (x in 0 until puzzleSize) {
                if (field[y][x] != counter) return false
                counter++
                if (counter == puzzleSize * puzzleSize) {
                    counter = 0
                }
            }
        }
        return true
    }

    private fun fieldToString(): String {
        var sField = ""
        for (y in 0 until puzzleSize) {
            for (x in 0 until puzzleSize) {
                sField += "${field[y][x]} "
            }
            sField += "\n"
        }
        return sField
    }


}

enum class Direction {
    /**
     * Top [Direction] means tile must be moved from bottom to top
     * Bottom [Direction] means tile must be moved from top to bottom
     * Right [Direction] means tile must be moved from left to right
     * Left [Direction] means tile must be moved from right to left
     * */
    Top, Bottom, Right, Left;
}