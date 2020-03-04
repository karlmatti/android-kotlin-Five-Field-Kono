package karlmatti.application.hw1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.graphics.drawable.toDrawable
import kotlinx.android.synthetic.main.game_board.*
import kotlinx.android.synthetic.main.game_statistics.*

class MainActivity : AppCompatActivity() {
    private var counter = 0
    private lateinit var lastClickButton: Button
    private lateinit var gameBoard: List<List<Button>>
    private lateinit var player1Color: String
    private lateinit var player2Color: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val row0 = listOf(button00, button01, button02, button03, button04)
        val row1 = listOf(button10, button11, button12, button13, button14)
        val row2 = listOf(button20, button21, button22, button23, button24)
        val row3 = listOf(button30, button31, button32, button33, button34)
        val row4 = listOf(button40, button41, button42, button43, button44)
        gameBoard= listOf(row0, row1, row2, row3, row4)
        player1Color = button40.background.constantState.toString()
        player2Color = button00.background.constantState.toString()
    }

    fun handleClick(btn: View) {
        val button = btn as Button
        counter++
        if (counter % 2 == 0) {
            if(isEmptySpace(button) && isMovedDiagonally(lastClickButton, button)) {
                button.background = lastClickButton.background

                lastClickButton.setBackgroundResource(R.drawable.empty_button)
                Log.d("whoWon", whoWon().toString())
            }
        } else {
            lastClickButton = button
        }


    }
    private fun isEmptySpace(button: Button): Boolean {
        return button.background.constantState ==
                resources.getDrawable(R.drawable.empty_button).constantState
    }

    private fun isMovedDiagonally(firstlyClickedBtn: Button, secondlyClickedBtn: Button): Boolean {
        var row: Int = 0
        var col: Int = 0
        for (i in 0..4) {
            for (j in 0..4) {
                if (gameBoard[i][j] == firstlyClickedBtn){
                    row = i
                    col = j
                    Log.d("isMovedDiagonally_row", row.toString())
                    Log.d("isMovedDiagonally_col", col.toString())
                    break
                }
            }
        }

        //north
        if(row == 0){ // When movable button is in first row
            if(col == 0) {
                if(gameBoard[row+1][col+1] == secondlyClickedBtn) return true
            } else if(col == 4) {
                if(gameBoard[row+1][col-1] == secondlyClickedBtn) return true
            } else {
                if(gameBoard[row+1][col+1] == secondlyClickedBtn) return true
                else if(gameBoard[row+1][col-1] == secondlyClickedBtn) return true
            }
        }
        //south
        else if(row == 4){ // When movable button is in last row
            if(col == 0) {
                if(gameBoard[row-1][col+1] == secondlyClickedBtn) return true
            } else if(col == 4) {
                if(gameBoard[row-1][col-1] == secondlyClickedBtn) return true
            } else {
                if(gameBoard[row-1][col+1] == secondlyClickedBtn) return true
                else if(gameBoard[row-1][col-1] == secondlyClickedBtn) return true
            }
        }
        //east
        else if(col == 4){ // When movable button is in last column
            if(gameBoard[row+1][col-1] == secondlyClickedBtn) return true
            else if(gameBoard[row-1][col-1] == secondlyClickedBtn) return true
        }

        //west
        else if(col == 0){ // When movable button is in first column
            if(gameBoard[row+1][col+1] == secondlyClickedBtn) return true
            else if(gameBoard[row-1][col+1] == secondlyClickedBtn) return true
        }
        //rest
        else if(gameBoard[row+1][col+1] == secondlyClickedBtn) return true
        else if(gameBoard[row+1][col-1] == secondlyClickedBtn) return true
        else if(gameBoard[row-1][col+1] == secondlyClickedBtn) return true
        else if(gameBoard[row-1][col-1] == secondlyClickedBtn) return true

        return false

    }

    private fun whoWon(): Player {
        //Check if Player1 has won
        if (gameBoard[0][0].background.constantState.toString() == player1Color &&
            gameBoard[0][1].background.constantState.toString() == player1Color &&
            gameBoard[0][2].background.constantState.toString() == player1Color &&
            gameBoard[0][3].background.constantState.toString() == player1Color &&
            gameBoard[0][4].background.constantState.toString() == player1Color &&
            gameBoard[1][0].background.constantState.toString() == player1Color &&
            gameBoard[1][4].background.constantState.toString() == player1Color
                ) return Player.One

        //Check if Player2 has won
        if (gameBoard[4][0].background.constantState.toString() == player2Color &&
            gameBoard[4][1].background.constantState.toString() == player2Color &&
            gameBoard[4][2].background.constantState.toString() == player2Color &&
            gameBoard[4][3].background.constantState.toString() == player2Color &&
            gameBoard[4][4].background.constantState.toString() == player2Color &&
            gameBoard[3][0].background.constantState.toString() == player2Color &&
            gameBoard[3][4].background.constantState.toString() == player2Color
        ) return Player.Two

        return Player.None
    }
}
