fun createCard(): Card {
    val rank = Rank.standardRanks.random()
    val suit = Suit.entries.random()

    val card = Card(rank = rank, suit = suit)
    return card
}
