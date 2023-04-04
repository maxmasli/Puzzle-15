package masli.prof.puzzle15

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat


class MainActivity : AppCompatActivity() {

    private val puzzle15Engine = Puzzle15Engine()

    private lateinit var fieldLinearLayout: LinearLayoutCompat
    private lateinit var newGameButton: Button
    private lateinit var messageTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fieldLinearLayout = findViewById(R.id.fieldLinearLayout)
        newGameButton = findViewById(R.id.newGameButton)
        messageTextView = findViewById(R.id.messageTextView)

        setListeners()
        puzzle15Engine.newPuzzle()
        updateField()
    }

    private fun setListeners() {
        newGameButton.setOnClickListener {
            messageTextView.text = ""
            puzzle15Engine.newPuzzle()
            updateField()
        }
    }

    private fun updateField() {
        fieldLinearLayout.removeAllViews()
        for (y in 0 until puzzle15Engine.puzzleSize) {
            val row = LinearLayoutCompat(this)
            row.orientation = LinearLayoutCompat.HORIZONTAL
            row.gravity = Gravity.CENTER
            row.layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )

            for (x in 0 until puzzle15Engine.puzzleSize) {
                if (puzzle15Engine.getTile(x, y) == 0) {
                    val tileLayout = layoutInflater.inflate(R.layout.empty_tile, null, false)
                    row.addView(tileLayout)
                } else {
                    val tileLayout = layoutInflater.inflate(R.layout.tile, null, false)
                    tileLayout.findViewById<TextView>(R.id.tileNumber).text =
                        puzzle15Engine.getTile(x, y).toString()

                    tileLayout.setOnClickListener {
                        onTileClick(x, y)
                    }

                    row.addView(tileLayout)
                }
            }
            fieldLinearLayout.addView(row)
        }
    }

    private fun onTileClick(x: Int, y: Int) {
        puzzle15Engine.clickAction(x, y)
        updateField()
        if (puzzle15Engine.isOver) {
            messageTextView.text = getString(R.string.you_win)
        }
    }
}
