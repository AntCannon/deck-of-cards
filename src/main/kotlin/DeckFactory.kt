fun createDecks(
    deckOptions: DeckOptions = DeckOptions()
): List<Deck> {

    require(deckOptions.numberOfDecks > 0) {
        "numberOfDecks must be greater than zero"
    }

    val decks: MutableList<Deck> = mutableListOf()

    var decksToCreate = deckOptions.numberOfDecks
    while (decksToCreate > 0) {
        val deck = Deck(containerName = "Deck ${decks.size + 1}")
        for (suit in Suit.entries) {

            for (rank in Rank.standardRanks) {
                val card = Card(
                    rank = rank,
                    suit = suit,
                    originContainerId = deck.containerId
                )

                deck.addCard(card)
            }
        }

        if (deckOptions.addJokers) {
            deck.addCard(Card(rank = Rank.LITTLE_JOKER,
                originContainerId = deck.containerId))
            deck.addCard(Card(rank = Rank.BIG_JOKER,
                originContainerId = deck.containerId))
        }

        if (deckOptions.shuffleDeck) {
            deck.shuffle()
        }

        decks.add(deck)
        decksToCreate--
    }

    return decks
}
