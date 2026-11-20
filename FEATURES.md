# WarPG Features and Definitions

This document consolidates the game concept, development-plan responses, running decisions, and the new container and card-tracking requirements. It describes features and their meaning, not implementation instructions.

**Status labels:** Confirmed means requested or agreed, not necessarily implemented. Future means an expressed idea outside the initial build. Proposed means a suggestion that still needs agreement. Unresolved means behavior has not yet been defined.

## Product scope

- **Confirmed:** Build a basic mobile deck playground first, using placeholder visuals and adding one feature at a time.
- **Confirmed:** Android first, followed by iOS work after major milestones.
- **Confirmed:** The first screen manages one deck. The underlying model can support multiple decks.
- **Confirmed:** Establish the deck manager, shoe, and piles before adding further card interactions.
- **Confirmed:** Game mechanics are outside the initial build.
- **Future:** War / I Declare War gameplay with RPG and idle progression.
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

“Set of cards” and “set of decks” describe collections here; they do not select a Kotlin collection type or decide ordering and uniqueness rules. Two cards from different decks can have the same rank and suit, so they must not accidentally become indistinguishable when tracking their removal.

The shoe groups decks; piles group cards. A pile is not automatically a new deck, and placing decks in a shoe does not yet imply merging their cards into a single draw order.

## Deck manager and state

**Confirmed responsibilities:** Manage the shoe, decks, piles, and cards. Support the requirement to know which cards have been removed from each deck.

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
| Default shuffle | New decks should use `false` for shuffle for now, per the latest response. | Confirmed choice; code still defaults to `true` |
| Draw a card | Take a card from a deck; draw position and empty-deck behavior remain undefined. | Confirmed; not implemented |
| Return a card | Put a card back into a deck; destination restrictions and insertion position remain undefined. | Confirmed; not implemented |
| Delete a deck | Remove a deck; treatment of cards outside it remains undefined. | Confirmed; not implemented |
| Create a pile | Create a collection of cards that can be named. | Confirmed; not implemented |
| Player's hand | Represent a player's hand as a pile. | Confirmed; not implemented |
| Discard pile | Represent discarded cards as a pile. | Confirmed; not implemented |
| Transfer to/from a pile | Move a card into or out of a pile. | Confirmed; not implemented |
| Discard a pile | A requested action whose effect on the pile and its contents is not yet defined. | Confirmed action; semantics unresolved |
| Shoe | Group decks in a shoe. | Confirmed; not implemented |
| Deck manager | Manage the shoe, decks, piles, and cards. | Confirmed; not implemented |
| Track removed cards | Identify cards removed from a particular deck. | Confirmed; not implemented |

Naming a pile is required; unique names, renaming, pile ordering, and capacity limits have not been specified. Shoe dealing, cross-deck shuffling, and shoe capacity are not implied by the definition of a shoe.

## Removed-card tracking

**Required outcome:** For a deck, determine which cards have been removed. A remaining-card count alone does not satisfy this requirement.

For example, after taking the five of hearts from deck A, the system must be able to identify that card as removed from deck A. A five of hearts in deck B must not be confused with that copy.

**Proposed supporting concepts:**

- Individual card identity, so equal rank/suit labels do not collapse distinct copies.
- A relationship between a card and the deck it came from.
- Current location, so a card can be found in a hand, discard pile, or other supported location.
- Consistent transfers, so moving a card does not create another copy or silently lose it.

These are proposed ways to support the requirement, not a chosen storage design. Tracking a card's source does not mean it is still physically present in that deck.

**Current milestone decisions still needed:**

1. Does “removed” mean cards currently outside the deck, a history of every removal even after return, or both?
2. When a removed card is returned, must it return to its original deck, or may it join another deck?

These decisions affect tracking behavior. A permanent event history, timestamps, and an audit UI are not assumed requirements.

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

- Regular currency and premium currency.
- Power-ups.
- Time-based activities and idle progression.
- Progress from short sessions, long sessions, and idle activities contributes to overall advancement.

Currency sources and sinks, monetization, power-up effects and duration, timers, offline calculations, and balance remain undefined. Premium currency does not by itself specify purchases or a monetization model.

**Proposed supporting feature:** Save progress locally before exploring offline rewards. Saved fields, active-match saving, reward claims, clock-change behavior, and offline limits have not been agreed. Cloud accounts and online features are not initial requirements.

## Current build order

1. Establish definitions and responsibilities for the deck manager, shoe, and piles.
2. Settle removed-card tracking semantics and how individual copies are distinguished.
3. Build the supporting models before further card interactions.
4. Expose the foundation through the single-deck Android playground with placeholders.
5. Add basic card actions and verify transfers and removed-card tracking together.
6. Explore gameplay and progression only after the basic deck playground works.

This updates the earlier plan's ordering: piles and the manager are foundational work, rather than additions after drawing and returning cards. It also brings individual-card identity forward for tracking instead of postponing it solely until progression.

## Related documents

- [Development plan](https://github.com/AntCannon/deck-of-cards/blob/feature/ac/deck-shuffle-and-mobile-plan/DEVELOPMENT_PLAN.md): general learning roadmap on the planning branch; the build order above reflects the latest prerequisite decision.
- [Questions and decisions](https://github.com/AntCannon/deck-of-cards/blob/feature/ac/deck-shuffle-and-mobile-plan/DEVELOPMENT_QUESTIONS.md): your responses and explanations on the planning branch; the answered Q1/Q2 establish disabled default shuffle and one deck on the first screen.
- [Game concept log](WARPG_CONCEPT.md): original game concept and longer-term mechanics.

This feature document records the latest requirements without changing application code. Implementation status refers to the reviewed `feature/ac/deck-shuffle-and-mobile-plan` branch, whose shuffle and planning changes are not yet on `main`. No new build or test was run.
