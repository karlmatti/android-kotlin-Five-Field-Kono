package karlmatti.application.hw1

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.game_board.*
import kotlinx.android.synthetic.main.game_statistics.*
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = this::class.java.declaringClass!!.simpleName
        private var engine = KonoGame()
    }

    private val interval: Long = 1000
    private var player2Starts: Boolean = false
    private lateinit var gameBoardButtons: Array<Array<Button>>
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeGameBoardButtons()
        handler = Handler()

    }

    fun handleSwitch(switch: View){
        player2Starts = !player2Starts
    }

    fun handlePlay(btn: View) {
        handler.removeCallbacks(handleAIvsAI)
        val gameMode = getGameMode(game_mode.selectedItem.toString())
        engine.handlePlayBtn(player2Starts, gameMode)
        updateUI(false)

        if (gameMode == Mode.ComputerVsComputer.id){
            handleAIvsAI.run()
        }
    }

    var handleAIvsAI: Runnable = object : Runnable {
        override fun run() {
            try {
                engine.doAIMove()
                updateUI(false)
            } finally {
                handler.postDelayed(this, interval)
            }
        }
    }

    fun handleClick(btn: View) {
        val row = getBtnRow(btn)
        val col = getBtnCol(btn)

        val drawSelection = engine.handleClickOn(row, col)
        updateUI(drawSelection)


    }

    private fun updateUI(drawSelection: Boolean) {
        updateUIState()
        updateUIBoard(drawSelection)

    }

    @SuppressLint("SetTextI18n")
    private fun updateUIState() {

        when (engine.whoWon) {
            0 -> {
                when (engine.whoseTurn) {
                    1 -> {
                        game_status.text = "<-"
                    }
                    2 -> {
                        game_status.text = "->"
                    }
                    else -> {
                        game_status.text = "Press start"
                    }
                }
                player1.text = engine.player1Name
                player2.text = engine.player2Name
                player1_score.text = engine.player1Score.toString()
                player2_score.text = engine.player2Score.toString()
            }
            1 -> {
                player1.text = "Winner: " + player1.text
            }
            2 -> {
                player2.text = "Winner: " + player2.text
            }
        }
    }

    private fun updateUIBoard(drawSelection: Boolean) {
        val board = engine.gameBoard
        val selectedBtn = engine.selectedButtonToMove

        if (drawSelection) {
            gameBoardButtons[selectedBtn[0]][selectedBtn[1]].text = "X"
        } else {
            gameBoardButtons[selectedBtn[0]][selectedBtn[1]].text = ""
        }

        for (row in 0..4) {
            for (col in 0..4) {

                when {
                    board[row][col] == Player.None.id -> {
                        gameBoardButtons[row][col]
                            .setBackgroundResource(R.drawable.empty_button)

                    }
                    board[row][col] == Player.One.id -> {
                        gameBoardButtons[row][col]
                            .setBackgroundResource(R.drawable.player1_button)
                    }
                    board[row][col] == Player.Two.id -> {
                        gameBoardButtons[row][col]
                            .setBackgroundResource(R.drawable.player2_button)
                    }

                }
            }
        }
    }

    private fun getGameMode(selectedGameMode: String): Int {
        return when (selectedGameMode) {
            "Player V Player" -> {
                Mode.PlayerVsPlayer.id
            }
            "Player V Computer" -> {
                Mode.PlayerVsComputer.id
            }
            else -> {
                Mode.ComputerVsComputer.id
            }
        }
    }

    private fun getBtnCol(btn: View): Int {
        when {
            intArrayOf(
                button00.id, button10.id, button20.id, button30.id, button40.id
            ).contains(btn.id) -> {
                return 0
            }
            intArrayOf(
                button01.id, button11.id, button21.id, button31.id, button41.id
            ).contains(btn.id) -> {
                return 1
            }
            intArrayOf(
                button02.id, button12.id, button22.id, button32.id, button42.id
            ).contains(btn.id) -> {
                return 2
            }
            intArrayOf(
                button03.id, button13.id, button23.id, button33.id, button43.id
            ).contains(btn.id) -> {
                return 3
            }
            intArrayOf(
                button04.id, button14.id, button24.id, button34.id, button44.id
            ).contains(btn.id) -> {
                return 4
            }
        }
        return -1
    }

    private fun getBtnRow(btn: View): Int {
        when {
            intArrayOf(
                button00.id, button01.id, button02.id, button03.id, button04.id
            ).contains(btn.id) -> {
                return 0
            }
            intArrayOf(
                button10.id, button11.id, button12.id, button13.id, button14.id
            ).contains(btn.id) -> {
                return 1
            }
            intArrayOf(
                button20.id, button21.id, button22.id, button23.id, button24.id
            ).contains(btn.id) -> {
                return 2
            }
            intArrayOf(
                button30.id, button31.id, button32.id, button33.id, button34.id
            ).contains(btn.id) -> {
                return 3
            }
            intArrayOf(
                button40.id, button41.id, button42.id, button43.id, button44.id
            ).contains(btn.id) -> {
                return 4
            }
        }
        return -1
    }

    private fun initializeGameBoardButtons() {
        val row0 = arrayOf(button00, button01, button02, button03, button04)
        val row1 = arrayOf(button10, button11, button12, button13, button14)
        val row2 = arrayOf(button20, button21, button22, button23, button24)
        val row3 = arrayOf(button30, button31, button32, button33, button34)
        val row4 = arrayOf(button40, button41, button42, button43, button44)
        gameBoardButtons = arrayOf(row0, row1, row2, row3, row4)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "LifeCycle: onSaveInstanceState")
        outState.putSerializable("state", engine.saveGameState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "LifeCycle: onRestoreInstanceState")
        val state = savedInstanceState.getSerializable("state") as HashMap<String, Any>
        engine.loadGameState(state)
        updateUI(state["isMoveClick"] as Boolean)
        if (state["gameMode"] == Mode.ComputerVsComputer.id) {
            handleAIvsAI.run()
        } else {
            handler.removeCallbacks(handleAIvsAI)
        }
    }
}
