# WarPG Features and Definitions

This document consolidates the game concept, development-plan responses, running decisions, and the new container and card-tracking requirements. It describes features and their meaning, not implementation instructions.

**Status labels:** Confirmed means requested or agreed, not necessarily implemented. Future means an expressed idea outside the initial build. Proposed means a suggestion that still needs agreement. Unresolved means behavior has not yet been defined.

## Product scope

- **Confirmed:** WarPG I is an offline, single-player Android idle RPG with local state and saves. It does not require a server or premium currency.
- **Confirmed:** Build a basic Android deck playground first, using placeholder visuals and adding one feature at a time. The playground is the first milestone, not the complete WarPG I release.
- **Deferred:** iOS is outside the WarPG I schedule and may be reconsidered after the Android release.
- **Confirmed:** The first screen manages one deck. The underlying model can support multiple decks.
- **Confirmed:** Establish safe card movement, piles, state ownership, and movement history before adding further card interactions. Shoe implementation is deferred.
- **Confirmed:** Game mechanics are outside the initial deck-playground milestone but are part of the planned WarPG I release.
- **Confirmed for WarPG I:** Add War / I Declare War gameplay, XP, mini-games, battles, idle progression, and local saving incrementally after the foundation.
- **Future:** WarPG II is an online, single-player game with deeper progression, storyline, and premium currency. PvP is a separate future experience.
- **Future:** Short activities lasting a few minutes and longer activities, with both and idle activities contributing to overall progress.
- **Confirmed learning approach:** Explore mechanics through small standalone exercises before integration.

## Core definitions

| Term | Definition | Status |
| --- | --- | --- |
| Card | An individual card with a rank and, where applicable, a suit. | Confirmed |
| Rank | A card designation such as 5, K, BJ, or LJ. Rank and gameplay value are distinct concepts. | Confirmed |
| Suit | A card's suit: spades, diamonds, clubs, or hearts for standard cards. | Confirmed |
| Deck | A collection of cards. A newly created standard deck contains 52 cards, or 54 with jokers. Its remaining count can decrease as cards leave it. | Confirmed |
| Pile | A collection of cards that can be named. A player's hand and the discard pile are both piles. | Confirmed |
| Shoe | A collection of decks. | Confirmed |
| Deck manager | Manages the shoe, decks, piles, and cards. | Confirmed |
| Removed-card tracking | The ability to identify which cards have been removed from a deck, rather than only knowing how many remain. | Confirmed |
| Full movement history | A record of each completed card movement, including removals, moves between piles, and returns. Returning a card does not erase earlier records. | Confirmed |

“Set of cards” and “set of decks” describe collections here; they do not select a Kotlin collection type or decide ordering and uniqueness rules. Two cards from different decks can have the same rank and suit, so they must not accidentally become indistinguishable when tracking their removal.

The shoe groups decks; piles group cards. A pile is not automatically a new deck, and placing decks in a shoe does not yet imply merging their cards into a single draw order.

## Deck manager and state

**Confirmed responsibilities:** Manage the shoe, decks, piles, and cards. Support the requirement to know which cards have been removed from each deck and retain their full movement history, including returns.

**Confirmed state behavior:** Actions affect the current live deck, and the player should see the current state after each action. Callers should change state through controlled methods rather than freely editing the underlying collection.

**Proposed responsibility boundary:** The manager coordinates actions involving multiple containers, while each container owns its contents. This is a design proposal, not a requirement to put every card rule in the manager.

The UI can display updated snapshots or another suitable representation of current state. “Live deck” does not require exposing a mutable collection directly to the screen.

## Card composition and values

- Standard cards must have a suit.
- Initial jokers are suitless Big Joker (`BJ`) and Little Joker (`LJ`).
- A full standard deck contains one of each standard rank/suit pairing, plus the two jokers when enabled.
- Current model values are ace = 1, numbered cards = their number, jack = 11, queen = 12, king = 13, little joker = 14, and big joker = 15. These are implementation values, not a settled War ruleset.
- **Future:** Special and custom decks, suited jokers, and effects that remove rank or suit and the associated skills.
- **Future:** Skills, power-ups, buffs, and debuffs can affect card values; formulas and duration are unresolved.
- **Confirmed progression constraint:** Leveling a card does not itself increase its base War value. Future effects on value are a separate mechanic.

## Initial deck and container features

| Feature | Intended behavior or definition | State |
| --- | --- | --- |
| No deck | Support a state in which no deck exists. | Confirmed; no mobile UI yet |
| Create/get a deck | Obtain a deck; standard deck creation supports optional jokers and multiple decks. Other acquisition methods are undefined. | Creation implemented |
| Shuffle a deck | Reorder the cards currently in that deck. | Implemented |
| Default shuffle | New decks use `false` for shuffle for now, while callers may explicitly enable it. | Implemented |
| Draw a card | Take the first card from a deck and record the departure; an empty draw is rejected. | Basic behavior implemented; complete manager-owned history is not implemented |
| Return a card | Put a card back into a deck; destination restrictions and insertion position remain undefined. | A basic transfer can target a deck; return rules and atomic history are not implemented |
| Delete a deck | Remove a deck; treatment of cards outside it remains undefined. | Confirmed; not implemented |
| Create a pile | Create a named collection of cards. | Implemented; the factory currently creates the default `STANDARD` type |
| Player's hand | Represent a player's hand as a `HAND` pile. | Model implemented through `PileType`; no session or UI uses it yet |
| Discard pile | Represent discarded cards as a `DISCARD` pile. | Model implemented through `PileType`; no session or UI uses it yet |
| Transfer to/from a pile | Move a card into or out of a pile. | Basic first-card transfer implemented; it is sequential, not atomic |
| Discard a pile | A requested action whose effect on the pile and its contents is not yet defined. | Confirmed action; semantics unresolved |
| Shoe | Group decks in a shoe. | Confirmed definition; implementation deferred |
| Deck manager | Manage registered containers, cards, transfers, and history. | Only a top-level transfer function exists; no state-owning manager or registry exists |
| Track removed cards | Identify cards removed from a particular deck. | Per-container departure lists exist; they are not full history or current-location data |
| Full movement history | Preserve completed card movements, including returns, separately from current location. | Confirmed requirement; implementation is part of the current foundation milestone |

Naming a pile is required; unique names, renaming, pile ordering, and capacity limits have not been specified. Shoe dealing, cross-deck shuffling, and shoe capacity are not implied by the definition of a shoe.

## Removed-card tracking and full movement history

**Required outcome:** For a deck, determine which cards have been removed. A remaining-card count alone does not satisfy this requirement.

**Confirmed decision:** Keep full movement history, not only a list of cards currently outside a deck. Record completed movements in order, including removals from decks, transfers between piles, and returns. Returning a card must not delete its earlier movement records.

The history should distinguish the individual card and its source and destination for each completed move. Current location answers where the card is now; history answers how it moved. An invalid move must leave card locations unchanged and must not appear as a completed movement.

For example, after taking the five of hearts from deck A, the system must be able to identify that card as removed from deck A. A five of hearts in deck B must not be confused with that copy.

If that card moves from deck A to a player's hand, then to the discard pile, then back to deck A, all three movements remain in its history. It is no longer currently outside deck A, but its earlier removal remains visible in the history.

**Proposed supporting concepts:**

- Individual card identity, so equal rank/suit labels do not collapse distinct copies.
- A relationship between a card and the deck it came from.
- Current location, so a card can be found in a hand, discard pile, or other supported location.
- Consistent transfers, so moving a card does not create another copy or silently lose it.

These are proposed ways to support the requirement, not a chosen storage design. Tracking a card's source does not mean it is still physically present in that deck.

**Current milestone decision still needed:** When a removed card is returned, must it return to its original deck, or may it join another deck?

Full history is confirmed; its storage design, persistence across app restarts, retention scope, timestamps, and a dedicated history UI have not been specified.

## Card presentation and actions

- **View a card:** Requested; exact meaning remains unresolved.
- **Show a card:** Requested; relationship to viewing or revealing remains unresolved.
- **Flip a card:** Requested; face visibility rules remain unresolved.
- **Play a card:** Requested for later gameplay; not automatically the same as drawing or showing.
- **Discard a card:** The initial scope includes moving a card to a discard pile. Any permanent removal behavior must be defined separately.

Do not equate deleting a deck, discarding a pile, discarding a card, or removing a card from a deck without an explicit rule.

## Mobile UI and experience

**Confirmed:** Use a basic Android UI with placeholders while building features. Start with one deck.

**Proposed initial screen:** A deck playground showing an empty state, a create-deck action, deck/card counts, and readable placeholder cards. Add interaction controls and pile displays as their features are built.

**Proposed usability work:** Clear unavailable actions, feedback after actions, readable touch targets, suit/outcome information that does not depend only on color, and protection against accidental repeated actions. Rotation, backgrounding, and reopening behavior remain to be chosen.

**Future UX idea from your response:** Creating a deck can animate unwrapping, unboxing, and shuffling. The user can turn the animation off. Animation settings and shuffle behavior are distinct; the future animation does not override the current choice to disable shuffle by default.

## Future War gameplay

The game is inspired by War / I Declare War and should preserve that identity while introducing RPG depth.

**Proposed playable experiment:** Deal cards, reveal opposing cards, resolve a round, collect winnings, handle ties, finish a match, and restart. A basic battle screen would show played cards, counts, and results.

Ace ordering, joker participation, dealing and collection rules, ties, insufficient cards during ties, match completion, and the effect of skills on rounds are unresolved. Existing rank values do not settle these rules.

## Future player progression

- Gain XP and levels.
- Gate skills, talents, and features by player XP/level.
- Require point spending in addition to eligibility for some unlocks.
- Support multiple types of skill points.
- Spend and respec points.

XP sources, thresholds, point types, costs, unlock rules, and respec rules remain undefined.

## Future individual card progression

- Individual cards can level up.
- Card progression grants points for skills.
- Card level can determine eligibility to use an unlocked suit skill.
- Card leveling never increases base War value.

XP sources, point spending, and progression formulas remain undefined. Distinguishing individual copies will also support this feature.

## Future suit progression and talents

- Suits level independently and can have talent trees.
- A suit skill must first be unlocked.
- A card must also meet that skill's minimum card-level requirement.
- Example: an unlocked skill requiring card level 10 cannot be used by a level-9 card of that suit; a level-10 card meets that level condition, subject to any other future rules.

Suit themes, skill effects, tree structure, costs, and the distinction between skills and talents remain undefined.

## Future economy, power-ups, and time

- **WarPG I:** Time-based activities and idle progression are confirmed release goals. Regular currency and power-ups remain recorded ideas whose exact first-release scope and rules are unresolved.
- **WarPG II:** Premium currency is reserved for the later online release.
- Progress from short sessions, long sessions, and idle activities contributes to overall advancement.

Currency sources and sinks, power-up effects and duration, timers, offline calculations, and balance remain undefined. WarPG I has no premium currency or required monetization design.

**Proposed supporting feature:** Save progress locally before exploring offline rewards. Saved fields, active-match saving, reward claims, clock-change behavior, and offline limits have not been agreed. Cloud accounts and online features are not initial requirements.

## Current build order

1. Establish baseline automated checks for deck creation, identity, origin, optional shuffle behavior, and existing reorder behavior.
2. Define the smallest selected-card transfer request, immutable completed-movement record, and explicit success/failure outcomes.
3. Decide how a central manager registers containers and owns authoritative state before replacing or restricting the existing container mutation methods.
4. Implement all-or-nothing movement and derive current location from authoritative container contents.
5. Verify deck to hand to discard to deck movement, stable identity/origin, card conservation, ordered history, and unchanged state after every rejected transfer.
6. Expose the verified foundation through the single-deck Android playground with placeholders.
7. Add a basic War session, one XP reward, local saving, and one idle activity as separate playable increments.

Shoe, iOS, online infrastructure, premium currency, and PvP remain outside the current milestone.

The detailed logical implementation order and first movement milestone are in the [development plan](DEVELOPMENT_PLAN.md#current-milestone-card-movement-foundation).

## Related documents

- [Development plan](DEVELOPMENT_PLAN.md): the current logical implementation order, followed by the earlier learning roadmap and preserved responses.
- [November 23 chat update](resourceDocs/waRPG_Chat_Update_2026-11-23.md): preserved discussion, confirmed release direction, and illustrative architecture examples.
- [Game concept log](WARPG_CONCEPT.md): original game concept and longer-term mechanics.

Implementation status in this document reflects the inspected `feature/ac/card-pile-shoe-classes` branch on November 23, 2026. The current source compiles; Gradle reports `test NO-SOURCE`, so behavioral verification is still required.
