enum class PieceShape(val shape: Array<IntArray>) {
    I(arrayOf(
        intArrayOf(1, 1, 1, 1)
    )),
    O(arrayOf(
        intArrayOf(1, 1),
        intArrayOf(1, 1)
    )),
    T(arrayOf(
        intArrayOf(0, 1, 0),
        intArrayOf(1, 1, 1)
    )),
    S(arrayOf(
        intArrayOf(0, 1, 1),
        intArrayOf(1, 1, 0)
    )),
    Z(arrayOf(
        intArrayOf(1, 1, 0),
        intArrayOf(0, 1, 1)
    )),
    J(arrayOf(
        intArrayOf(1, 0, 0),
        intArrayOf(1, 1, 1)
    )),
    L(arrayOf(
        intArrayOf(0, 0, 1),
        intArrayOf(1, 1, 1)
    ));
}
