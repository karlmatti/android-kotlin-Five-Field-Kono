package karlmatti.application.hw1

import android.util.Log

class KonoGame {


    private var previousMoves: ArrayList<IntArray> = arrayListOf(intArrayOf(-1, -1),
                                                                 intArrayOf(-1, -1),
                                                                 intArrayOf(-1, -1))
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

    fun saveGameState(): HashMap<String, Any> {
        val hashMap : HashMap<String, Any>
                = HashMap<String, Any> ()
        hashMap["whoseTurn"] = whoseTurn
        hashMap["whoWon"] = whoWon
        hashMap["gameMode"] = gameMode
        hashMap["isMoveClick"] = isMoveClick
        hashMap["selectedButtonToMove"] = selectedButtonToMove

        return hashMap
    }

    fun loadGameState(hashMap: HashMap<String, Any>) {
        whoseTurn = hashMap["whoseTurn"] as Int
        whoWon = hashMap["whoWon"] as Int
        gameMode = hashMap["gameMode"] as Int
        isMoveClick = hashMap["isMoveClick"] as Boolean
        selectedButtonToMove = hashMap["selectedButtonToMove"] as Array<Int>
    }

    fun handleClickOn(row: Int, col: Int): Boolean {



        if (isGameContinuing()) {
            return if (isMoveClick){
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
        return false

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

    private fun doAIMove() {

    }

    private fun getAvailableMoves(row: Int, col: Int): ArrayList<IntArray> {
        val availableMoves = arrayListOf<IntArray>()

        for (i in 0..4) {
            for (j in 0..4) {
                if(isMovingToEmptySquare(arrayOf(i, j)) &&
                    isMovingDiagonally(
                        arrayOf(row, col),
                        arrayOf(i, j)
                    ) &&
                    isMovingSelfButtons(arrayOf(row, col)) &&
                    isNotInPreviousMoves(i, j)
                ) {

                    availableMoves.add(intArrayOf(i, j))
                }
            }
        }


        return availableMoves



    }

    fun isNotInPreviousMoves(row: Int, col: Int): Boolean {
        for (previousMove in previousMoves) {
            if (previousMove[0] == row && previousMove[1] == col){
                return false
            }
        }
        return true
    }
    fun getRandomAvailableMove(): ArrayList<IntArray> {
        val makemove = arrayListOf<IntArray>() // makemove
        var rnds = (0..6).random()
        var i = 0
        var j = 0
        while (i < 5 ) {
            while (j < 5) {
                if(gameBoard[i][j] == whoseTurn) {
                    if (rnds == 0) {
                        val moveFrom = intArrayOf(i, j)

                        val availableMoves = getAvailableMoves(i, j)
                        if (availableMoves.isEmpty()) {
                            rnds = (0..6).random()
                            i = 0
                            j = 0
                        } else {
                            val size = availableMoves.size - 1
                            val rndPosition = (0..size).random()

                            val moveTo = availableMoves[rndPosition]

                            makemove.add(moveFrom) // from
                            makemove.add(moveTo) // to
                            val currentPrevMoves = arrayListOf<IntArray>()
                            currentPrevMoves.add(moveFrom)
                            currentPrevMoves.add(previousMoves[0])
                            currentPrevMoves.add(previousMoves[1])
                            previousMoves = currentPrevMoves
                            Log.d("previousMoves size", previousMoves.size.toString())
                            Log.d("previousMoves 0", previousMoves[0].toString())
                            Log.d("previousMoves 1", previousMoves[1].toString())
                            Log.d("previousMoves 2", previousMoves[2].toString())
                            return makemove


                        }


                    } else {
                        rnds -= 1

                    }


                }
            j += 1
            }
            j = 0
            i += 1
        }

        return makemove
    }
}