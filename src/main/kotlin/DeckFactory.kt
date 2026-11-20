fun createDecks(deckOptions: DeckOptions = DeckOptions()): List<Deck> {
    require(deckOptions.numberOfDecks > 0) {
        "numberOfDecks must be greater than zero"
    }

    val decks: MutableList<Deck> = mutableListOf()

    var decksToCreate = deckOptions.numberOfDecks
    while (decksToCreate > 0) {
        val deck = Deck()
        for (suit in Suit.entries) {

            for (rank in Rank.standardRanks) {
                val card = Card(
                    rank,
                    suit
//                    parentDeck = deck,
                )

                deck.addCard(card)
            }
        }

        if (deckOptions.addJokers) {
            deck.addCard(Card(Rank.LITTLE_JOKER))
            deck.addCard(Card(Rank.BIG_JOKER))
        }

        if (deckOptions.shuffleDeck) {
            deck.shuffleDeck()
        }

        decks.add(deck)
        decksToCreate--
    }

    return decks
}
