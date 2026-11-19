//  create rank enum class
enum class Rank(
    val rankCode: String,
    val rankValue: Int
) {
    ACE("A", 1),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 11),
    QUEEN("Q", 12),
    KING("K", 13),
    LITTLE_JOKER("LJ", 14),
    BIG_JOKER("BJ", 15);

    companion object {
        val standardRanks = entries.filter {
            it != LITTLE_JOKER && it != BIG_JOKER
        }
    }
}