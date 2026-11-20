# Development Questions and Decisions

Use this as the running conversation for the build. Write under **Your response**. Answered items stay here as a decision record; add new questions only when they affect the current milestone. Proposed interpretations remain separate from confirmed answers.

## Current milestone

Establish the deck baseline and start an Android deck playground with placeholder cards. The first visible result is creating a real deck and displaying its count. Basic deck actions follow incrementally. Game mechanics are outside the initial build.

## Open questions

### Q1 — Should new decks be shuffled by default?

Your updated `DeckOptions` defaults `shuffleDeck` to `true`; previously the unused option defaulted to `false`. The factory now honors the option. Is shuffled creation the default you want, or should a new deck start ordered until Shuffle is selected?

**Your response:**

For UX when a deck is created there can be an animation to unwarap, unbox, then shuffle the cards. This animation could be turned off by the user. For right now it can be set to false.

### Q2 — How many decks should the first screen manage?

The factory already supports multiple decks. Proposed starting scope: one active deck on the first screen, with multiple-deck selection later. Would you like that starting scope or multiple decks on the first screen?

**Your response:**

1 deck

## Recorded responses and review

The responses below summarize your additions to `DEVELOPMENT_PLAN.md`; your original notes remain there.

### D1 — Platform direction

**Your answer:** Android first, then an iOS version after major milestones, working on one main feature at a time.

**Plan impact:** Start with Android and keep deck behavior independent of the screen. The shared-code approach and iOS milestone timing have not been selected.

### D2 — Build baseline

**Your answer:** The existing build runs.

**Review:** Recorded as user-verified. No build was run during this documentation review. Automated deck checks remain a separate milestone item.

### D3 — Standard deck composition

**Your answer:** Standard decks have 52 cards without jokers or 54 with them. Special and custom decks can come later.

**Review:** The factory matches this initial composition. The 52/54 count describes a newly created full deck; drawing cards will reduce the remaining count.

### D4 — Valid cards and future effects

**Your answer:** Standard cards must have a suit. Big and Little Jokers use BJ and LJ and are suitless for now. Suited jokers and effects that remove rank or suit are future possibilities. Current card values are not War-specific; skills, power-ups, buffs, and debuffs may affect values later.

**Explanation requested:** “Valid card combinations” means which rank/suit pairings the model permits. A five of hearts is valid now; a five without a suit is not. A suitless BJ is valid now; a BJ of hearts is deferred. The factory currently creates the intended combinations, but the `Card` constructor does not enforce these restrictions.

**Plan impact:** Keep future effects out of the initial deck build. The concept log's existing rule that card leveling does not change base War value can coexist with later temporary value effects; their mechanics are not decided here.

### D5 — Live deck state and controlled changes

**Your answer:** Players should see the current deck after an action. Actions operate on the live deck. Callers should not change anything without a setter; you anticipate a deck manager and requested an explanation.

**Explanation requested:** The deck can own its live collection while callers request specific changes through actions such as adding, drawing, or shuffling. Those methods are controlled entry points, like setters with a specific purpose; a general setter that replaces everything is not required.

`getCards()` currently returns that same collection as a Kotlin read-only `List`. Ordinary callers cannot call list mutation methods through that type, but a previously obtained list still reflects later changes. This is a read-only interface, not an immutable copy or a strict mutation boundary.

A snapshot is a copy representing one moment. A screen can receive a fresh snapshot after each action and still display the current live deck. Neither a snapshot nor a live list automatically makes a mobile screen refresh; screen state must be updated when the deck changes.

**Proposed interpretation:** Keep mutation behind deck actions and refresh the screen after each action. A separate deck manager can be introduced when there is a concrete responsibility for it. Your requirement does not force the UI to hold the backing collection directly.

**Your follow-up, if needed:**

### D6 — Shuffle implementation review

**Your request:** Review the updated shuffle functionality and provide feedback.

**Review:** `Deck.shuffleDeck()` shuffles the owned collection in place. The factory calls it once per deck, after standard cards and optional jokers are added, only when `deckOptions.shuffleDeck` is true. This is a straightforward implementation for the current scope; no functional defect was found by source inspection.

The default now enables shuffling; Q1 records that behavior choice. A shuffle may occasionally produce the same order, so “the order must change every time” would be an incorrect check. Useful checks cover preserved card count/composition and ordered creation when shuffling is disabled. Runtime behavior was not independently tested in this review.

### D7 — Remaining interactions

**Your answer:** The remaining actions are to be implemented.

**Review:** Shuffling is now implemented. Drawing, returning, discard piles, and other deck actions remain future work. Individual-card identity can wait until a feature needs it.

### D8 — Initial scope and README

**Your answer:** Create a minimal technical README. The initial product is a basic deck of cards with creation, drawing, discard piles, shuffling, and basic actions. Game mechanics are excluded from the initial build.

**Action taken:** Added a minimal README separating implemented features from planned actions. War and progression remain later experiments in the development plan.
