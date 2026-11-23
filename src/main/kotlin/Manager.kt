fun moveCard(
    sourceCardContainer: CardContainer,
    targetCardContainer: CardContainer,
): Unit {
    require (sourceCardContainer.cards.isNotEmpty()) {
        "Container must have at least one card."
    }

    require (targetCardContainer.containerId != sourceCardContainer.containerId) {
        "Source and Target Containers must be different. " +
                "Use CardContainer operations instead."
    }

    val drawnCard: Card = sourceCardContainer.drawCard()
    targetCardContainer.addCard(drawnCard)
}
