//  create card class
data class Card(
    val rank: Rank,
    val suit: Suit? = null
) {
    val cardCode: String
        get() = rank.rankCode + (suit?.suitCode ?: "")

    val rankCode: String
        get() = rank.rankCode

    val suitCode: String
        get() = suit?.suitCode ?: ""
}