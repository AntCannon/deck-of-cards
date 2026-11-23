# WarPG

WarPG is an early Kotlin/JVM prototype for an offline, single-player Android card RPG inspired by War / I Declare War. The repository currently contains the reusable card and container foundation; it is not yet an Android application or a playable game.

## Current capabilities

- Create one or more independently identified standard decks.
- Optionally include suitless Little and Big Jokers.
- Give every deck, pile, and card a UUID.
- Preserve a card's original deck ID when it moves.
- Represent decks and named `HAND`, `DISCARD`, or `STANDARD` piles through `CardContainer`.
- Add, draw, shuffle, and shift cards in decks and piles.
- Perform a basic top-card transfer between two containers.
- Display decks and cards through configurable console helpers.

The current transfer function is sequential rather than atomic, duplicate membership is not yet prevented, and complete movement history is not implemented. There are no automated tests yet.

## Current milestone

The next milestone is validated, all-or-nothing card movement with ordered transfer history. A successful deck to hand to discard to deck cycle must preserve card identity and origin. A rejected transfer must leave every container and its history unchanged.

Shoe support, Android UI, War rules, progression, saving, and idle mechanics are later milestones. WarPG I does not require a server or premium currency.

## Build verification

The project uses Kotlin 2.4.21 and a Java 24 toolchain. Use the Gradle wrapper:

```shell
./gradlew compileKotlin test
```

When Gradle reports `test NO-SOURCE`, compilation succeeded but no automated tests ran.

## Project documents

- [Development plan](DEVELOPMENT_PLAN.md) — current sequence and milestone goals.
- [Features and definitions](FEATURES.md) — confirmed, proposed, future, and unresolved behavior.
- [Game concept](WARPG_CONCEPT.md) — long-term brainstorming and progression ideas.
- [Development handoff](HANDOFF.md) — current branch checkpoint and immediate work.
- [Agent guidance](AGENTS.md) — repository-specific collaboration and verification rules.
