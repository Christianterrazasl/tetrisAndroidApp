package com.example.tetrisapp.models
import PieceShape

class Piece (val shapeType: PieceShape, var row: Int = 0, var col: Int = 3){
    var shape: Array<IntArray> = shapeType.shape
    fun moveDown() {
        row++
    }

    fun moveLeft() {
        col--

    }

    fun moveRight() {
        col++
    }

    fun rotate() {
        val newShape = Array(shape[0].size) { IntArray(shape.size) }
        for (i in shape.indices) {
            for (j in shape[i].indices) {
                newShape[j][shape.size - 1 - i] = shape[i][j]
            }
        }
        shape = newShape
    }
}