package karlmatti.application.hw1

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.game_board.*
import kotlinx.android.synthetic.main.game_statistics.*

class MainActivity : AppCompatActivity() {


    private var whoWon: Int = 0
    private var whoseTurn: Int = 0
    lateinit var gameBoardButtons: Array<Array<Button>>

    private var engine = KonoGame()


    companion object {
        private val TAG = this::class.java.declaringClass!!.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("BUG01", "onCreate")
        setContentView(R.layout.activity_main)
        initializeGameBoardButtons()
    }
    fun handlePlay(btn: View) {

    }

    fun handleClick(btn: View) {
        val row = getBtnRow(btn)
        val col = getBtnCol(btn)
        val drawSelection = engine.handleClickOn(row, col)
        updateUI(drawSelection)


    }

    private fun updateUI(drawSelection: Boolean) {
        updateUIBoard(drawSelection)
        updateUIState()

    }

    @SuppressLint("SetTextI18n")
    private fun updateUIState() {
        whoseTurn = engine.whoseTurn
        whoWon = engine.whoWon
        if (whoWon == 0) {
            if (whoseTurn == 1) {
                game_status.text = "<-"
            } else if (whoseTurn == 2) {
                game_status.text = "->"
            } else {
                game_status.text = "Press start"
            }
        } else if (whoWon == 1) {
            game_status.text = "<- winner"
        } else if (whoWon == 2) {
            game_status.text = "winner ->"
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
                    board[row][col] == 0 -> {
                        gameBoardButtons[row][col]
                            .setBackgroundResource(R.drawable.empty_button)

                    }
                    board[row][col] == 1 -> {
                        gameBoardButtons[row][col]
                            .setBackgroundResource(R.drawable.player1_button)
                    }
                    board[row][col] == 2 -> {
                        gameBoardButtons[row][col]
                            .setBackgroundResource(R.drawable.player2_button)
                    }

                }
            }
        }
    }

    private fun getBtnCol(btn: View): Int {
        when {
            intArrayOf(
                button00.id, button10.id, button20.id, button30.id, button40.id
            ).contains(btn.id) -> {
                return 0;
            }
            intArrayOf(
                button01.id, button11.id, button21.id, button31.id, button41.id
            ).contains(btn.id) -> {
                return 1;
            }
            intArrayOf(
                button02.id, button12.id, button22.id, button32.id, button42.id
            ).contains(btn.id) -> {
                return 2;
            }
            intArrayOf(
                button03.id, button13.id, button23.id, button33.id, button43.id
            ).contains(btn.id) -> {
                return 3;
            }
            intArrayOf(
                button04.id, button14.id, button24.id, button34.id, button44.id
            ).contains(btn.id) -> {
                return 4;
            }
        }
        return -1;
    }

    private fun getBtnRow(btn: View): Int {
        when {
            intArrayOf(
                button00.id, button01.id, button02.id, button03.id, button04.id
            ).contains(btn.id) -> {
                return 0;
            }
            intArrayOf(
                button10.id, button11.id, button12.id, button13.id, button14.id
            ).contains(btn.id) -> {
                return 1;
            }
            intArrayOf(
                button20.id, button21.id, button22.id, button23.id, button24.id
            ).contains(btn.id) -> {
                return 2;
            }
            intArrayOf(
                button30.id, button31.id, button32.id, button33.id, button34.id
            ).contains(btn.id) -> {
                return 3;
            }
            intArrayOf(
                button40.id, button41.id, button42.id, button43.id, button44.id
            ).contains(btn.id) -> {
                return 4;
            }
        }
        return -1;
    }




    fun initializeGameBoardButtons() {
        Log.d("BUG01", "initializeGameBoardButtons")
        val row0 = arrayOf(button00, button01, button02, button03, button04)
        val row1 = arrayOf(button10, button11, button12, button13, button14)
        val row2 = arrayOf(button20, button21, button22, button23, button24)
        val row3 = arrayOf(button30, button31, button32, button33, button34)
        val row4 = arrayOf(button40, button41, button42, button43, button44)
        gameBoardButtons = arrayOf(row0, row1, row2, row3, row4)
    }




}
