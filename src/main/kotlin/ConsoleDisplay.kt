fun viewDecks(
    decks: List<Deck>,
    viewOptions: ViewOptions = ViewOptions()
) {
    decks.forEach { deck ->
        if (viewOptions.decks.showDeckName) println("Deck Name: ${deck.containerName}")
        if (viewOptions.decks.showCardCount) println("Card Count: ${deck.cards.size}")
        if (viewOptions.decks.showDeckId) println("Deck Id: ${deck.containerId}")
        if (viewOptions.decks.showCards) viewCards(deck,viewOptions.cards)
    }
}

fun viewCards(
        cardContainer: CardContainer,
        viewCardOptions: ViewOptions.Cards = ViewOptions.Cards()
) {
    cardContainer.cards.forEachIndexed { index, card ->
        println("\nCard #${index +1}")
        if (viewCardOptions.showCardCode) println("Code: ${card.cardCode}")
        if (viewCardOptions.showCardId) println("Id: ${card.cardId}")
    }
}


fun viewCard(
    card: Card,
    viewCardOptions: ViewOptions.Cards = ViewOptions.Cards()
) {
    if (viewCardOptions.showCardCode) println("Code: ${card.cardCode}")
    if (viewCardOptions.showCardId) println("Id: ${card.cardId}")
}
