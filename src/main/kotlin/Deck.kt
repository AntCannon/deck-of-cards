import java.util.UUID

//  create deck class
class Deck(
    val deckId: String = UUID.randomUUID().toString(),
) {
    private val cards: MutableList<Card> = mutableListOf()

    fun getCards(): List<Card> {
        return cards
    }

    fun addCard(card: Card) {
        cards.add(card)
    }
}
