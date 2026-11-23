fun main() {
    message("creating deck(s)")
    val initialDeckContainer = createDecks()[0]
    val initialDeck = initialDeckContainer.cards
    message("Decks created")

    message("shift top to bottom")
    viewCard(initialDeck.first())
    initialDeckContainer.shiftTopCardToBottom()
    viewCard(initialDeck.last())
}
