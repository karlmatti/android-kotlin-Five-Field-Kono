package karlmatti.application.hw1

import java.lang.Math.abs

class KonoGame {
    companion object {
        private var ui = MainActivity()
    }
    private var whoseTurn = Player.One
    private var gameMode = Mode.PlayerVsPlayer.value
    private var isMoveClick: Boolean = false
    private var selectedButtonToMove = arrayOf(0, 0)

    var gameBoard = arrayOf(
        intArrayOf(Player.Two.color, Player.Two.color, Player.Two.color, Player.Two.color, Player.Two.color),
        intArrayOf(Player.Two.color, Player.None.color, Player.None.color, Player.None.color, Player.Two.color),
        intArrayOf(Player.None.color, Player.None.color, Player.None.color, Player.None.color, Player.None.color),
        intArrayOf(Player.One.color, Player.None.color, Player.None.color, Player.None.color, Player.One.color),
        intArrayOf(Player.One.color, Player.One.color, Player.One.color, Player.One.color, Player.One.color)
    )

    fun handleClickOn(row: Int, col: Int) {
        if(isMoveClick){
            val moveButtonTo = arrayOf(row, col)
            handleMove(selectedButtonToMove, moveButtonTo)
            isMoveClick = false
        } else {
            selectedButtonToMove = arrayOf(row, col)
            isMoveClick = true
        }
    }

    private fun handleMove(selectedBtnToMove: Array<Int>, moveBtnTo: Array<Int>) {
        if(isMovingDiagonally(selectedBtnToMove, moveBtnTo) &&
                    isMovingToEmptySquare(moveBtnTo) &&
                    isMovingSelfButtons(selectedBtnToMove)) {

            gameBoard[moveBtnTo[0]][moveBtnTo[1]] = gameBoard[selectedBtnToMove[0]][selectedBtnToMove[1]]
            gameBoard[selectedBtnToMove[0]][selectedBtnToMove[1]] = Player.None.color

            ui.paintSquare(moveBtnTo, gameBoard[moveBtnTo[0]][moveBtnTo[1]])
            ui.paintSquare(selectedBtnToMove, Player.None.color)

            //changeTurn()
        }

    }

    private fun isMovingSelfButtons(selectedButtonToMove: Array<Int>): Boolean {
        if (gameBoard[selectedButtonToMove[0]][selectedButtonToMove[1]] == whoseTurn.color) {
            return true
        }
        return false
    }

    private fun isMovingToEmptySquare(moveButtonTo: Array<Int>): Boolean {
        if (gameBoard[moveButtonTo[0]][moveButtonTo[1]] == Player.None.color) {
            return true
        }
        return false
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

    private fun changeTurn(){
        whoseTurn = if(whoseTurn == Player.One){
            ui.changeStatusText("turn", Player.Two.color) // TODO: Replace with Player2 or Computer
            Player.Two
        } else {
            ui.changeStatusText("turn", Player.One.color) // TODO: Replace with Player1 or Computer
            Player.One
        }
    }

    fun resetGameBoard() {
        gameBoard = arrayOf(
            intArrayOf(Player.Two.color, Player.Two.color, Player.Two.color, Player.Two.color, Player.Two.color),
            intArrayOf(Player.Two.color, Player.None.color, Player.None.color, Player.None.color, Player.Two.color),
            intArrayOf(Player.None.color, Player.None.color, Player.None.color, Player.None.color, Player.None.color),
            intArrayOf(Player.One.color, Player.None.color, Player.None.color, Player.None.color, Player.One.color),
            intArrayOf(Player.One.color, Player.One.color, Player.One.color, Player.One.color, Player.One.color)
        )
        // TODO: initialize new whoseTurn
        // TODO: initialize new gameMode
    }
}