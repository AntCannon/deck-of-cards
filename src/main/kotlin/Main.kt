import kotlin.String

fun main() {
//  access nested map
    createDeck()
}

// create suit enum class
enum class Suit(
    val suitCode: String,
    val suitValue: Int
) {
    SPADES("S", 0),
    DIAMONDS("D", 0),
    CLUBS("C", 0),
    HEARTS("H", 0);
}

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

//  create deck class
class Deck(
    val deckId: String,
) {
    private val cards: MutableList<Card> = mutableListOf()

    fun getCards(): List<Card> {
        return cards
    }

    fun addCard(card: Card) {
        cards.add(card)
    }
}

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

data class DeckOptions(
    val addJokers: Boolean = true,
    val shuffle: Boolean = false,
    val numberOfDecks: Int = 1,
)

fun createDeck(options: DeckOptions = DeckOptions()): Deck {
    println("Creating Deck Box...")

    val deckId: String = "01"
    val deck = Deck(deckId)

    println("Printing Cards...")
    for (suit in Suit.entries) {

        for (rank in Rank.standardRanks) {
            val card = Card(
                rank,
                suit
//                parentDeck = deck,
            )

            deck.addCard(card)
        }
    }

    if (options.addJokers) {
        deck.addCard(Card(Rank.LITTLE_JOKER))
        deck.addCard(Card(Rank.BIG_JOKER))
    }

    println("Total cards: ${deck.getCards().size}")
    return deck
}
