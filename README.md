# Deck of Cards

A Kotlin deck-of-cards project being developed into an Android deck playground with placeholder UI. iOS work is planned after major milestones. Game mechanics are outside the initial build.

## Current implementation

- Card, rank, and suit models.
- Creation of one or more standard decks: 52 cards, or 54 with suitless Big and Little Jokers.
- Optional shuffle during creation, enabled by default, and an action to shuffle an existing deck.
- Console entry point in `src/main/kotlin/Main.kt` that creates a deck and prints its cards.

## Technical baseline

Kotlin/JVM with Gradle Kotlin DSL and a Java 24 toolchain. Kotlin test dependencies are configured; no automated tests have been added yet. There is no Android app module yet.

## Next milestone

Verify the deck baseline, then create an Android screen that creates a deck and displays its count using placeholder cards. Add drawing, returning, and discarding to a pile incrementally; those actions are not implemented yet.

## Project notes

- [Development plan](DEVELOPMENT_PLAN.md)
- [Running questions and decisions](DEVELOPMENT_QUESTIONS.md)
- [Future game concept](WARPG_CONCEPT.md)
