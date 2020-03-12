package karlmatti.application.hw1

import android.util.Log
import kotlinx.android.synthetic.main.game_statistics.*

class KonoGame {


    var whoseTurn = Player.One.id
    var whoWon = Player.None.id
    private var gameMode = Mode.PlayerVsPlayer.id
    private var isMoveClick: Boolean = false
    var selectedButtonToMove = arrayOf(0, 0)

    var gameBoard = arrayOf(
        intArrayOf(Player.Two.id, Player.Two.id, Player.Two.id, Player.Two.id, Player.Two.id),
        intArrayOf(Player.Two.id, Player.None.id, Player.None.id, Player.None.id, Player.Two.id),
        intArrayOf(Player.None.id, Player.None.id, Player.None.id, Player.None.id, Player.None.id),
        intArrayOf(Player.One.id, Player.None.id, Player.None.id, Player.None.id, Player.One.id),
        intArrayOf(Player.One.id, Player.One.id, Player.One.id, Player.One.id, Player.One.id)
    )
        private set

    fun handleClickOn(row: Int, col: Int): Boolean {

        return if (isMoveClick && isGameContinuing()){
            val moveButtonTo = arrayOf(row, col)
            handleMove(selectedButtonToMove, moveButtonTo)
            isMoveClick = false
            false
        } else {
            selectedButtonToMove = arrayOf(row, col)
            isMoveClick = true
            true
        }
    }

    private fun handleMove(selectedBtnToMove: Array<Int>, moveBtnTo: Array<Int>) {
        if(isMovingDiagonally(selectedBtnToMove, moveBtnTo) &&
            isMovingToEmptySquare(moveBtnTo) &&
            isMovingSelfButtons(selectedBtnToMove)) {
            gameBoard[moveBtnTo[0]][moveBtnTo[1]] = gameBoard[selectedBtnToMove[0]][selectedBtnToMove[1]]
            gameBoard[selectedBtnToMove[0]][selectedBtnToMove[1]] = Player.None.id

            changeTurn()
            isGameContinuing()
        }

    }

    private fun isMovingSelfButtons(selectedButtonToMove: Array<Int>): Boolean {
        if (gameBoard[selectedButtonToMove[0]][selectedButtonToMove[1]] == whoseTurn) {
            return true
        }
        return false
    }

    private fun isMovingToEmptySquare(moveButtonTo: Array<Int>): Boolean {
        if (gameBoard[moveButtonTo[0]][moveButtonTo[1]] == Player.None.id) {
            return true
        }
        return false
    }

    fun handlePlayBtn(player2Starts: Boolean, selectedGameMode: Int) {
        resetGameBoard()
        setWhoseTurn(player2Starts)
        whoWon = Player.None.id
        isMoveClick = false
        gameMode = selectedGameMode
        Log.d("gameMode", gameMode.toString())
    }

    private fun isMovingDiagonally(
        selectedButtonToMove: Array<Int>,
        moveButtonTo: Array<Int>
    ): Boolean {

        if (kotlin.math.abs(moveButtonTo[0] - selectedButtonToMove[0]) <= 1 &&
            kotlin.math.abs(moveButtonTo[1] - selectedButtonToMove[1]) <= 1) {
            if(selectedButtonToMove[0]!=moveButtonTo[0] &&
                selectedButtonToMove[1]!=moveButtonTo[1]) {
                return true
            }
        }
        return false
    }

    private fun isGameContinuing(): Boolean {
        if (gameBoard[0][0] == Player.One.id &&
            gameBoard[0][1] == Player.One.id &&
            gameBoard[0][2] == Player.One.id &&
            gameBoard[0][3] == Player.One.id &&
            gameBoard[0][4] == Player.One.id &&
            gameBoard[1][0] == Player.One.id &&
            gameBoard[1][4] == Player.One.id) {
            whoWon = Player.One.id
            return false
        } else if (gameBoard[4][0] == Player.Two.id &&
            gameBoard[4][1] == Player.Two.id &&
            gameBoard[4][2] == Player.Two.id &&
            gameBoard[4][3] == Player.Two.id &&
            gameBoard[4][4] == Player.Two.id &&
            gameBoard[3][0] == Player.Two.id &&
            gameBoard[3][4] == Player.Two.id) {
            whoWon = Player.Two.id
            return false
        } else {
            return true
        }

    }

    private fun changeTurn(){
        this.whoseTurn = if(whoseTurn == Player.One.id){
            Player.Two.id
        } else {
            Player.One.id
        }
    }

    private fun resetGameBoard() {
        gameBoard = arrayOf(
            intArrayOf(Player.Two.id, Player.Two.id, Player.Two.id, Player.Two.id, Player.Two.id),
            intArrayOf(Player.Two.id, Player.None.id, Player.None.id, Player.None.id, Player.Two.id),
            intArrayOf(Player.None.id, Player.None.id, Player.None.id, Player.None.id, Player.None.id),
            intArrayOf(Player.One.id, Player.None.id, Player.None.id, Player.None.id, Player.One.id),
            intArrayOf(Player.One.id, Player.One.id, Player.One.id, Player.One.id, Player.One.id)
        )


    }

    private fun setWhoseTurn(player2Starts: Boolean) {
        whoseTurn = if (player2Starts) {
            Player.Two.id
        } else {
            Player.One.id
        }
    }
}