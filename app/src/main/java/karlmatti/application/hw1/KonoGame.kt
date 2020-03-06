package karlmatti.application.hw1

class KonoGame {
    private var counter = 0

    private lateinit var gameBoardMatrix: Array<Array<Int>>
    private var player1Score = 0
    private var player2Score = 0
    private lateinit var whoseTurn: Enum<Player>
    private var gameStarted: Boolean = false
    private lateinit var gameState: MutableMap<String, Any>

    fun startGame(player1Starts: Boolean) {
        resetGameBoard()

        whoseTurn = if(player1Starts) { Player.One }
                    else { Player.Two }

        gameStarted = true

    }
    fun resetGameBoard() {
        gameBoardMatrix = Array(5) { Array(5) {0}}
        for(i in 0..4){
            gameBoardMatrix[0][i]=2
            gameBoardMatrix[4][i]=1
        }
        gameBoardMatrix[1][0] = 2
        gameBoardMatrix[1][4] = 2
        gameBoardMatrix[3][0] = 1
        gameBoardMatrix[3][4] = 1
    }

    fun whoWon(): Player {
        if(gameBoardMatrix[0][0] == 1 && gameBoardMatrix[0][1] == 1 &&
            gameBoardMatrix[0][2] == 1 && gameBoardMatrix[0][3] == 1 &&
            gameBoardMatrix[0][4] == 1 && gameBoardMatrix[1][4] == 1 &&
            gameBoardMatrix[1][0] == 1) {
            return Player.One
        } else if(gameBoardMatrix[4][0] == 2 && gameBoardMatrix[4][1] == 2 &&
            gameBoardMatrix[4][2] == 2 && gameBoardMatrix[4][3] == 2 &&
            gameBoardMatrix[4][4] == 2 && gameBoardMatrix[3][4] == 2 &&
            gameBoardMatrix[3][0] == 2) {
            return Player.Two
        } else {
            return Player.None
        }
    }

    fun changeTurn() {
        whoseTurn = if(whoseTurn == Player.One){
            Player.Two
        } else {
            Player.One
        }
    }

    private fun isMovingToEmptySpace(row: Int, column: Int): Boolean {
        return gameBoardMatrix[row][column] == 0
    }

    private fun isMovingDiagonally(firstlyClickedBtn: Int, secondlyClickedBtn: Int): Boolean {
        var row: Int = 0
        var col: Int = 0
        for (i in 0..4) {
            for (j in 0..4) {
                if (gameBoardMatrix[i][j] == firstlyClickedBtn){
                    row = i
                    col = j

                    break
                }
            }
        }

        //north
        if(row == 0){ // When movable button is in first row
            if(col == 0) {
                if(gameBoardMatrix[row+1][col+1] == secondlyClickedBtn) return true
            } else if(col == 4) {
                if(gameBoardMatrix[row+1][col-1] == secondlyClickedBtn) return true
            } else {
                if(gameBoardMatrix[row+1][col+1] == secondlyClickedBtn) return true
                else if(gameBoardMatrix[row+1][col-1] == secondlyClickedBtn) return true
            }
        }
        //south
        else if(row == 4){ // When movable button is in last row
            if(col == 0) {
                if(gameBoardMatrix[row-1][col+1] == secondlyClickedBtn) return true
            } else if(col == 4) {
                if(gameBoardMatrix[row-1][col-1] == secondlyClickedBtn) return true
            } else {
                if(gameBoardMatrix[row-1][col+1] == secondlyClickedBtn) return true
                else if(gameBoardMatrix[row-1][col-1] == secondlyClickedBtn) return true
            }
        }
        //east
        else if(col == 4){ // When movable button is in last column
            if(gameBoardMatrix[row+1][col-1] == secondlyClickedBtn) return true
            else if(gameBoardMatrix[row-1][col-1] == secondlyClickedBtn) return true
        }

        //west
        else if(col == 0){ // When movable button is in first column
            if(gameBoardMatrix[row+1][col+1] == secondlyClickedBtn) return true
            else if(gameBoardMatrix[row-1][col+1] == secondlyClickedBtn) return true
        }
        //rest
        else if(gameBoardMatrix[row+1][col+1] == secondlyClickedBtn) return true
        else if(gameBoardMatrix[row+1][col-1] == secondlyClickedBtn) return true
        else if(gameBoardMatrix[row-1][col+1] == secondlyClickedBtn) return true
        else if(gameBoardMatrix[row-1][col-1] == secondlyClickedBtn) return true

        return false

    }

}