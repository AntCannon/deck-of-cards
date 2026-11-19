fun createDecks(options: DeckOptions = DeckOptions()): List<Deck> {
    require(options.numberOfDecks > 0) {
        "numberOfDecks must be greater than zero"
    }

    val decks: MutableList<Deck> = mutableListOf()

    var decksToCreate = options.numberOfDecks
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

        if (options.addJokers) {
            deck.addCard(Card(Rank.LITTLE_JOKER))
            deck.addCard(Card(Rank.BIG_JOKER))
        }

        decks.add(deck)
        decksToCreate--
    }

    return decks
}
