fun main() {
    val options = DeckOptions()

    println("Beginning deck production...")
    val decks = createDecks(options)
    println("${decks.size} deck(s) created!")
    println("Viewing Deck")
    viewCards(decks[0])

}

fun viewDecks(decks: List<Deck>, showCards: Boolean  = false) {
    decks.forEach { deck ->
        println("Deck Id: ${deck.deckId}")
        println("Card count: ${deck.getCards().size}")

        if (showCards) {
            viewCards(deck)
        }
    }
}

fun viewCards(deck: Deck) {
    deck.getCards().forEachIndexed { index, card ->
        println("Card #${index +1}: ${card.cardCode}")
    }
}