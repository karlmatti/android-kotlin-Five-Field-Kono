package karlmatti.application.hw1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.game_board.*
import kotlinx.android.synthetic.main.game_statistics.*

class MainActivity : AppCompatActivity() {
    private var counter = 0
    private lateinit var lastClickButton: Button
    private lateinit var gameBoardButtons: List<List<Button>>
    private var gameBoardMatrix = Array(5) { Array(5) {0}}
    private var player1Score = 0
    private var player2Score = 0
    private val player1Color = R.drawable.player1_button
    private val player2Color = R.drawable.player2_button
    private lateinit var whoseTurn: Enum<Player>
    private var gameStarted: Boolean = false
    private lateinit var gameState: MutableMap<String, Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        whoseTurn = Player.One
        Log.d("OnCreate", "x")
    }
    fun handlePlay(btn: View) {
        //TODO: Reset game board in gameBoardMatrix and gameBoardButtons
        //TODO: Set whoseTurn depending if the switch is turned off or on
        //TODO: Save the state

        resetGameBoard()
        saveSate()


    }
    private fun saveSate() {
        val row0 = listOf(button00, button01, button02, button03, button04)
        val row1 = listOf(button10, button11, button12, button13, button14)
        val row2 = listOf(button20, button21, button22, button23, button24)
        val row3 = listOf(button30, button31, button32, button33, button34)
        val row4 = listOf(button40, button41, button42, button43, button44)

        gameState["whoseTurn"] = whoseTurn
        gameState["Mode"] = game_mode.selectedItem.toString()
        gameState["Started"] = true
        gameState["Board"] = listOf(row0, row1, row2, row3, row4)


    }
    private fun resetGameBoard(){
        //TODO: Reset also gameBoardMatrix
        button00.setBackgroundResource(R.drawable.player2_button)
        button01.setBackgroundResource(R.drawable.player2_button)
        button02.setBackgroundResource(R.drawable.player2_button)
        button03.setBackgroundResource(R.drawable.player2_button)
        button04.setBackgroundResource(R.drawable.player2_button)

        button10.setBackgroundResource(R.drawable.player2_button)
        button11.setBackgroundResource(R.drawable.empty_button)
        button12.setBackgroundResource(R.drawable.empty_button)
        button13.setBackgroundResource(R.drawable.empty_button)
        button14.setBackgroundResource(R.drawable.player2_button)

        button20.setBackgroundResource(R.drawable.empty_button)
        button21.setBackgroundResource(R.drawable.empty_button)
        button22.setBackgroundResource(R.drawable.empty_button)
        button23.setBackgroundResource(R.drawable.empty_button)
        button24.setBackgroundResource(R.drawable.empty_button)

        button30.setBackgroundResource(R.drawable.player1_button)
        button31.setBackgroundResource(R.drawable.empty_button)
        button32.setBackgroundResource(R.drawable.empty_button)
        button33.setBackgroundResource(R.drawable.empty_button)
        button34.setBackgroundResource(R.drawable.player1_button)

        button40.setBackgroundResource(R.drawable.player1_button)
        button41.setBackgroundResource(R.drawable.player1_button)
        button42.setBackgroundResource(R.drawable.player1_button)
        button43.setBackgroundResource(R.drawable.player1_button)
        button44.setBackgroundResource(R.drawable.player1_button)

    }
    fun handleClick(btn: View) {
        //TODO: Handle also gameBoardMatrix -> create new method for making move makeMove(from, to) etc.
        val button = btn as Button
        counter++
        if (counter % 2 == 0) {
            if(isMovingToEmptySpace(button) &&
                isMovingDiagonally(lastClickButton, button) &&
                isMovingSelfButtons(lastClickButton)) {
                button.background = lastClickButton.background

                lastClickButton.setBackgroundResource(R.drawable.empty_button)
                Log.d("whoWon()", whoWon().toString())
                changeTurn()
            }
            lastClickButton.text = ""
        } else {
            lastClickButton = button
            button.text = "x"
        }


    }
    private fun isMovingToEmptySpace(button: Button): Boolean {
        return button.background.constantState ==
                resources.getDrawable(R.drawable.empty_button).constantState
    }

    private fun isMovingDiagonally(firstlyClickedBtn: Button, secondlyClickedBtn: Button): Boolean {
        var row: Int = 0
        var col: Int = 0
        for (i in 0..4) {
            for (j in 0..4) {
                if (gameBoardButtons[i][j] == firstlyClickedBtn){
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
                if(gameBoardButtons[row+1][col+1] == secondlyClickedBtn) return true
            } else if(col == 4) {
                if(gameBoardButtons[row+1][col-1] == secondlyClickedBtn) return true
            } else {
                if(gameBoardButtons[row+1][col+1] == secondlyClickedBtn) return true
                else if(gameBoardButtons[row+1][col-1] == secondlyClickedBtn) return true
            }
        }
        //south
        else if(row == 4){ // When movable button is in last row
            if(col == 0) {
                if(gameBoardButtons[row-1][col+1] == secondlyClickedBtn) return true
            } else if(col == 4) {
                if(gameBoardButtons[row-1][col-1] == secondlyClickedBtn) return true
            } else {
                if(gameBoardButtons[row-1][col+1] == secondlyClickedBtn) return true
                else if(gameBoardButtons[row-1][col-1] == secondlyClickedBtn) return true
            }
        }
        //east
        else if(col == 4){ // When movable button is in last column
            if(gameBoardButtons[row+1][col-1] == secondlyClickedBtn) return true
            else if(gameBoardButtons[row-1][col-1] == secondlyClickedBtn) return true
        }

        //west
        else if(col == 0){ // When movable button is in first column
            if(gameBoardButtons[row+1][col+1] == secondlyClickedBtn) return true
            else if(gameBoardButtons[row-1][col+1] == secondlyClickedBtn) return true
        }
        //rest
        else if(gameBoardButtons[row+1][col+1] == secondlyClickedBtn) return true
        else if(gameBoardButtons[row+1][col-1] == secondlyClickedBtn) return true
        else if(gameBoardButtons[row-1][col+1] == secondlyClickedBtn) return true
        else if(gameBoardButtons[row-1][col-1] == secondlyClickedBtn) return true

        return false

    }

    private fun isMovingSelfButtons(button: Button): Boolean {
        //TODO: Should compare values in business logic not UI id values
        if(button.background.constantState.toString() == player1Color &&
            whoseTurn == Player.One){
            return true
        }  else if (button.background.constantState.toString() == player2Color &&
            whoseTurn == Player.Two) {
            return true
        }
        return false
    }

    private fun whoWon(): Player {
        //TODO: Should compare values in business logic not UI id values
        //Check if Player1 has won
        if (gameBoardButtons[0][0].background.constantState.toString() == player1Color &&
            gameBoardButtons[0][1].background.constantState.toString() == player1Color &&
            gameBoardButtons[0][2].background.constantState.toString() == player1Color &&
            gameBoardButtons[0][3].background.constantState.toString() == player1Color &&
            gameBoardButtons[0][4].background.constantState.toString() == player1Color &&
            gameBoardButtons[1][0].background.constantState.toString() == player1Color &&
            gameBoardButtons[1][4].background.constantState.toString() == player1Color
                ) return Player.One

        //Check if Player2 has won
        if (gameBoardButtons[4][0].background.constantState.toString() == player2Color &&
            gameBoardButtons[4][1].background.constantState.toString() == player2Color &&
            gameBoardButtons[4][2].background.constantState.toString() == player2Color &&
            gameBoardButtons[4][3].background.constantState.toString() == player2Color &&
            gameBoardButtons[4][4].background.constantState.toString() == player2Color &&
            gameBoardButtons[3][0].background.constantState.toString() == player2Color &&
            gameBoardButtons[3][4].background.constantState.toString() == player2Color
        ) return Player.Two

        return Player.None
    }

    private fun changeTurn(){
        if(whoseTurn==Player.One){
            whoseTurn = Player.Two
        } else {
            whoseTurn = Player.One
        }
    }



}
