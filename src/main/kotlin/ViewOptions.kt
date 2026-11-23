data class ViewOptions (
    val decks: Decks = Decks(),
    val cards: Cards = Cards()
) {
    data class Decks(
        val showDeckName: Boolean = true,
        val showDeckId: Boolean = false,
        val showCardCount: Boolean = true,
        val showCards: Boolean = true
    )

    data class Cards(
        val showCardCode: Boolean = true,
        val showCardId: Boolean = true,
    )
}
