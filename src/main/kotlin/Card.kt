import java.util.UUID

//  create card class
data class Card(
    val cardId: String = UUID.randomUUID().toString(),
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