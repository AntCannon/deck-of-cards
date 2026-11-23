import java.util.UUID

//  create deck class
class Deck(
    override val containerId: String = UUID.randomUUID().toString(),
    override val containerName: String,
): CardContainer {
    override val containerType: ContainerType = ContainerType.DECK

//    contents
    override val cards: List<Card>
        field = mutableListOf<Card>()

//    references
    override val removedCards: List<Card>
        field = mutableListOf<Card>()

//    properties


//    actions
    override fun addCard(card: Card) {
        cards.add(card)
    }

    override fun shuffle() {
        cards.shuffle()
    }

    override fun drawCard(): Card {
        require (cards.isNotEmpty()) {
            "Deck must have 1 or more cards"
        }

        val removedCard = cards.removeFirst()
        removedCards.add(removedCard)

        return removedCard
    }

    override fun shiftTopCardToBottom() {
        require (cards.isNotEmpty()) {
            "Deck must have at least one card"
        }

        cards.add(cards.removeFirst())
    }
}
