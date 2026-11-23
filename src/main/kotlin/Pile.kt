import java.util.UUID

// create pile class
class Pile (
    override val containerId: String = UUID.randomUUID().toString(),
    override val containerName: String,
    val pileType: PileType = PileType.STANDARD,
): CardContainer {
    override val containerType: ContainerType = ContainerType.PILE

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
            "Pile must have at least 1 card."
        }

        val removedCard = cards.removeFirst()
        removedCards.add(removedCard)

        return removedCard
    }

    override fun shiftTopCardToBottom() {
        require (cards.isNotEmpty()) {
            "Pile must have at least one card"
        }

        cards.add(cards.removeFirst())
    }
}
