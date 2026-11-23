interface CardContainer {
//    attributes
    val containerId: String
    val containerType: ContainerType
    val containerName: String

//    contents
    val cards: List<Card>

//    references
    val removedCards: List<Card>

//    properties
    val cardCount: Int
        get() = cards.size

//    actions
    fun addCard(card: Card)

    fun shuffle()

    fun drawCard(): Card

    fun shiftTopCardToBottom(): Unit
}
