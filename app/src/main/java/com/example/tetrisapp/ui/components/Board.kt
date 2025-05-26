package com.example.tetrisapp.ui.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.tetrisapp.models.Piece
import com.example.tetrisapp.ui.activities.GameOverActivity
import com.example.tetrisapp.ui.activities.MainActivity

class Board(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    
    private val rows = 20
    private val cols = 10
    private var cellWidth: Float = 0f
    private var cellHeight: Float = 0f
    private val cellPadding: Float = 6f
    private var screenWidth: Float = 0f
    private var screenHeight: Float = 0f
    var score : Long = 0
    private var speed : Long = 400
    var currentPiece = Piece(PieceShape.T)
    var scoreUpdateListener: OnScoreUpdateListener? = null
    var levelUpdateListener: OnLevelUpdateListener? = null
    var level: Int = 1
    private var isGameOver = false




    private var matriz = arrayListOf<ArrayList<Int>>().apply {
        repeat(rows) {
            add(ArrayList(IntArray(cols) { 0 }.toList()))
        }
    }

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = android.graphics.Color.WHITE
        strokeWidth = 10f

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        screenHeight = h.toFloat()
        screenWidth = w.toFloat()
        cellWidth = (w / cols).toFloat()
        cellHeight = (h / rows).toFloat()
        drawPieceOnMatrix(currentPiece)
        Log.d("BoardLog", "onSizeChanged: cellWidth=$cellWidth, cellHeight=$cellHeight")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        for (i in 0 until matriz.size) {
            for (j in 0 until matriz[i].size) {
                when (matriz[i][j]) {
                    1 -> {
                        paint.color = android.graphics.Color.WHITE
                        canvas.drawRect(
                            j * cellWidth + cellPadding,
                            i * cellHeight + cellPadding,
                            (j + 1) * cellWidth - cellPadding,
                            (i + 1) * cellHeight - cellPadding,
                            paint
                        )
                    }
                    2 -> {
                        paint.color = android.graphics.Color.GRAY
                        canvas.drawRect(
                            j * cellWidth + cellPadding,
                            i * cellHeight + cellPadding,
                            (j + 1) * cellWidth - cellPadding,
                            (i + 1) * cellHeight - cellPadding,
                            paint
                        )
                    }
                }
            }
        }


        val guidePaint = Paint().apply {
            color = android.graphics.Color.BLUE
            strokeWidth = 2f
            style = Paint.Style.STROKE
            isAntiAlias = true
        }


        for (col in 1 until cols) {
            val x = col * cellWidth
            canvas.drawLine(x, 0f, x, screenHeight, guidePaint)
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return false
        val x = event.x
        val y = event.y
        if (event.action == MotionEvent.ACTION_DOWN) {

            clearPieceFromMatrix(currentPiece)
            if(x > screenWidth / 2 && canMoveRight(currentPiece)){
                currentPiece.moveRight()

                Log.d("BoardLog",currentPiece.col.toString())
            }
            if (x < screenWidth / 2 && canMoveLeft(currentPiece)){
                currentPiece.moveLeft()
                Log.d("BoardLog",currentPiece.col.toString())

            }
            drawPieceOnMatrix(currentPiece)
            invalidate()
        }
        return true

    }

    fun drawPieceOnMatrix (piece: Piece){
        var shape = piece.shape
        for (i in shape.indices) {
            for (j in shape[i].indices) {
                if (shape[i][j] == 1) {
                    val row = piece.row + i
                    val col = piece.col + j
                    if (row in 0 until rows && col in 0 until cols) {
                        matriz[row][col] = 1
                    }
                }
            }
        }
    }

    private fun clearPieceFromMatrix(piece: Piece) {
        val shape = piece.shape
        for (i in shape.indices) {
            for (j in shape[i].indices) {
                if (shape[i][j] == 1) {
                    val row = piece.row + i
                    val col = piece.col + j
                    if (row in 0 until rows && col in 0 until cols) {
                        matriz[row][col] = 0
                    }
                }
            }
        }
    }

    fun rotate() {
        clearPieceFromMatrix(currentPiece)
        currentPiece.rotate()
        drawPieceOnMatrix(currentPiece)
        invalidate()
    }
    fun drop(){
        clearPieceFromMatrix(currentPiece)
        while (canMoveDown(currentPiece)){
            currentPiece.moveDown()
        }
        fixPieceToMatrix(currentPiece)
        checkAndClearFullRows()
        generateCurrentPiece()
        drawPieceOnMatrix(currentPiece)
        invalidate()
    }

    private fun canMoveLeft(piece: Piece): Boolean {
        return piece.col > 0
    }

    private fun canMoveRight(piece: Piece): Boolean {
        val shapeWidth = piece.shape[0].size
        return piece.col + shapeWidth < cols
    }

    fun startFalling() {
        Thread {
            while (!isGameOver) {
                Thread.sleep(speed)

                post {
                    clearPieceFromMatrix(currentPiece)
                    if (canMoveDown(currentPiece)) {
                        currentPiece.moveDown()
                    } else {
                        fixPieceToMatrix(currentPiece)
                        checkAndClearFullRows()
                        generateCurrentPiece()
                    }
                    drawPieceOnMatrix(currentPiece)
                    invalidate()
                }

            }
        }.start()
    }

    private fun canMoveDown(piece: Piece): Boolean {
        val shape = piece.shape
        for (i in shape.indices) {
            for (j in shape[i].indices) {
                if (shape[i][j] == 1) {
                    val row = piece.row + i + 1
                    val col = piece.col + j
                    if (row >= rows || matriz[row][col] == 2) {
                        return false
                    }
                }
            }
        }
        return true
    }

    private fun generateCurrentPiece(){
        val randomIndex = (0 until PieceShape.entries.size).random()
        val randomShape = PieceShape.entries[randomIndex]
        val newPiece = Piece(randomShape)
        if (!canPlacePiece(newPiece)) {

            isGameOver = true;
            goToGameOverScreen()
            return
        }
        currentPiece = newPiece
    }

    private fun fixPieceToMatrix(piece: Piece) {
        val shape = piece.shape
        for (i in shape.indices) {
            for (j in shape[i].indices) {
                if (shape[i][j] == 1) {
                    val row = piece.row + i
                    val col = piece.col + j
                    if (row in 0 until rows && col in 0 until cols) {
                        matriz[row][col] = 2
                    }
                }
            }
        }
    }

    private fun canPlacePiece(piece: Piece): Boolean {
        val shape = piece.shape
        for (i in shape.indices) {
            for (j in shape[i].indices) {
                if (shape[i][j] == 1) {
                    val row = piece.row + i
                    val col = piece.col + j
                    if (row >= rows || col < 0 || col >= cols || matriz[row][col] == 2) {
                        return false
                    }
                }
            }
        }
        return true
    }

    private fun checkAndClearFullRows() {
        val newMatriz = arrayListOf<ArrayList<Int>>()

        var linesCleared = 0

        for (row in matriz) {
            if (row.all { it == 2 }) {
                linesCleared++
            } else {
                newMatriz.add(row)
            }
        }

        repeat(linesCleared) {
            newMatriz.add(0, ArrayList(IntArray(cols) { 0 }.toList()))
        }

        matriz = newMatriz

        if (linesCleared > 0) {
            val pointsEarned = linesCleared * linesCleared * 10
            score += pointsEarned
            scoreUpdateListener?.onScoreUpdated(score)
            if (score >= 100) {
                level++
                levelUpdateListener?.onLevelUpdated(level)
                score = 0
                scoreUpdateListener?.onScoreUpdated(score)
                speed -= 100
            }
        }

    }

     fun restart(){

         isGameOver = false
        matriz = arrayListOf<ArrayList<Int>>().apply {
            repeat(rows) {
                add(ArrayList(IntArray(cols) { 0 }.toList()))
            }
        }
        generateCurrentPiece()
        score = 0
        level = 1
        speed = 400
        scoreUpdateListener?.onScoreUpdated(score)
        levelUpdateListener?.onLevelUpdated(level)
        invalidate()

     }

    fun goToGameOverScreen(){
        val intent = Intent(context, GameOverActivity::class.java)
        intent.putExtra("score", (level * 100) + score - 100)
        context.startActivity(intent)
        if (context is Activity) {
            (context as Activity).finish()
        }

    }

    interface OnScoreUpdateListener{
        fun onScoreUpdated(score: Long)
    }
    interface OnLevelUpdateListener{
        fun onLevelUpdated(level: Int)
    }
}