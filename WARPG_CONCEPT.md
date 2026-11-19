# WarPG — Game Concept and Mechanics Log

**Status:** Brainstorming / pre-implementation  
**Version:** 0.2  
**Platform:** Mobile  
**Language:** Kotlin  
**Inspiration:** War / I Declare War, RPG progression, and idle games

## Purpose

WarPG is a mobile card game based on the traditional card game War (also called I Declare War), with RPG and idle mechanics.

The immediate goal is **learning through small, standalone playable experiments**. Build and understand each mechanic as its own mini-game or isolated exercise before integrating mechanics into the full game.

**Do not assume this document is a request to write code.** It is a living concept log. Do not implement features or invent requirements without an explicit instruction.

## Experience Goals

- Provide meaningful activities that take only a few minutes.
- Also provide longer activities for players who want extended sessions.
- Include idle progression that depends on time and continues to contribute to progress.
- Ensure short sessions, extended sessions, and idle activities all contribute to overall progression.
- Preserve the core identity of War while adding RPG depth.

## Base Card and Deck Actions

### Deck
- Have no deck.
- Get a deck.
- Delete a deck.
- Shuffle a deck.
- Draw a card from a deck.
- Return a card to a deck.

### Pile
- Create a pile.
- Discard a pile.
- Transfer a card to a pile.
- Transfer a card from a pile.

### Card
- Discard a card.
- View a card.
- Play a card.
- Show a card.
- Flip a card.

The exact semantics of actions such as *delete*, *discard*, *show*, *view*, *play*, and *flip* are not yet defined. Do not silently equate them.

## Economy and Time

- Regular currency.
- Premium currency.
- Time-based mechanics, especially for idle activities.
- Power-ups.
- Possibly talents (details to be developed).

Sources, costs, timers, monetization, and balance are not yet specified.

## Player RPG Progression

- Players gain experience points (XP) and level up.
- Player XP/level gates access to skills, talents, and features.
- Meeting a level/XP requirement alone is not sufficient for some unlocks: players must also spend the relevant points.
- There can be multiple types of skill points.
- Skill points can be spent and respeced (reallocated).

The types of points, costs, respec rules, and exact unlock thresholds are open questions.

## Individual Card Progression

- Individual cards can level up.
- **Card levels do not increase the card's base War value.** For example, a 5 remains worth 5 at any level.
- Card progression grants points that can be used for skills.
- Card level can gate whether a card may use an unlocked suit skill.

The precise way cards earn XP and how their points are spent is not yet defined.

## Suit Progression and Talent Trees

- Suits can level up independently.
- Suits can have talent trees.
- A suit skill must be unlocked before an eligible card can use it.
- An individual card must also meet that skill's minimum card-level requirement.

**Example rule:** If a suit skill is unlocked but requires card level 10, a level-9 card of that suit cannot use it; a level-10 card of that suit can, subject to any future rules.

Suit themes, skills, costs, and tree structures are not yet specified.

## Progression Layers

1. **Player:** XP, levels, feature access, points, skills/talents.
2. **Suit:** XP/levels and suit talent trees.
3. **Card:** XP/levels, points, and eligibility for unlocked suit skills; fixed base card value.
4. **Economy and time:** Regular/premium currencies, power-ups, and idle progression.

These layers describe conceptual systems, not a finalized dependency hierarchy.

## Open Design Questions

- How does the traditional War round resolve once skills are introduced?
- What is the difference between *show*, *view*, *flip*, and *play*?
- How do players obtain, lose, and manage decks and cards?
- How are piles used in play?
- How are regular and premium currencies earned and spent?
- What activities run on timers? What happens while the app is closed?
- How do player, suit, and card XP accumulate?
- What types of skill points exist, and which are respecable?
- What do power-ups affect, and how long do they last?
- What is the relationship between skills and talents?
- Which activities support quick sessions versus long sessions?

## Working Instructions for Codex

When this file is provided as project context:

1. Treat it as the current **brainstorming source of truth**, not as an implementation specification.
2. Keep **confirmed ideas** distinct from **suggestions** and **unresolved questions**.
3. Do not generate code unless explicitly requested.
4. When implementation is requested, favor small, testable Kotlin mechanics that can be developed as standalone exercises and later integrated.
5. Prefer straightforward, modular designs with minimal dependencies and complexity appropriate for learning Kotlin.
6. Preserve the rule that leveling a card never changes its base War value.
7. Do not silently decide monetization, balancing, progression formulas, skill effects, or timer behavior.
8. When asked to update this log, append or revise the relevant sections while preserving previously confirmed requirements. Mark newly proposed ideas as proposals.

## Change Log

- **v0.1:** Core deck, pile, and card actions; short, extended, and idle gameplay goals; learn by building mechanics separately.
- **v0.2:** Regular/premium currency; time; power-ups; player XP and unlocks; multiple respecable skill-point types; individual card levels with fixed base value; suit levels and talent trees; card-level requirements for unlocked suit skills.
