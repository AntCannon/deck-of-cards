# WarPG — Development Handoff

**Checkpoint:** November 23, 2026. This records context for another computer/session; inspect the current repository before resuming.

## How to work with the user

Read [AGENTS.md](AGENTS.md). The user implements the code and wants focused reviews and logical steps. Do not write application changes unless explicitly requested. The latest completed task reconciled the new chat decisions into the development plan; no movement redesign has been implemented.

## Repository checkpoint and sync

- Repository: `https://github.com/AntCannon/deck-of-cards.git`.
- Active branch at this checkpoint: `feature/ac/card-pile-shoe-classes`, tracking the matching origin branch.
- Origin updates in `Card.kt` and `DeckFactory.kt` are committed as `8359837`. Earlier commits on that branch: `79aa54e` (containers/display), `ca519c5` (transfers/factories), and `5182703` (earlier documentation).
- This documentation commit includes the November 23 development-plan update, the `resourceDocs/` chat update, `AGENTS.md`, and this handoff. The user has requested committing and pushing all of these changes.
- Verify the feature branch's remote state before switching computers. Do not assume `main` contains the feature implementation; use the feature branch and its latest documentation commit.
- The user's earlier documentation changes were separately pushed to `main`; do not reset either branch or discard local changes to reconcile history.

Check Git status rather than recreating completed edits listed here. On another computer, fetch/pull the intended branch before reading this handoff and inspect build prerequisites before assuming an offline build will work.

## What is implemented

| Area | Current behavior |
| --- | --- |
| Cards | Generated UUID, rank/suit/code, immutable nullable `originContainerId`. Deck creation assigns origin to all standard cards and both jokers. Standalone random cards have null origin. |
| Containers | `CardContainer` defines ID/name/type, read-only contents and removal lists, computed count, add/draw/shuffle/shift operations. `Deck` and `Pile` implement it using explicit mutable backing fields. |
| Pile roles | `HAND`, `DISCARD`, and `STANDARD`; `Pile` defaults to `STANDARD`. |
| Creation | Factory creates multiple standard decks with optional jokers/shuffle; random card factory and empty named pile factory also exist. |
| Movement | A top-level `moveCard` in `Manager.kt` validates nonempty source and different container IDs, then draws the first card and adds it to the destination. It preserves the card object and origin. |
| Draw/shift | Drawing records a departure in `removedCards`. Shifting rotates top to bottom without adding a departure; empty draw/shift actions reject the operation. |
| Display | Configurable console output; `ViewOptions` has nested `Decks` and `Cards` groups. Card options pass through deck display; `viewCards` accepts `CardContainer`. `Main` demonstrates shifting. |

No state-owning manager, registry, movement record/history model, current-location query, atomic transaction model, Android UI, persistence, battle loop, XP, or idle implementation exists at this checkpoint. Shoe work is deliberately deferred.

## Review findings that matter next

1. **Movement is sequential, not atomic.** `Manager.kt` changes the source and removal list before adding to the target. If a target rejects the addition, prior state is not restored. Existing concrete add methods simply append, but the interface does not guarantee success.
2. **Duplicate identity/membership is possible.** Public `addCard` methods do not reject duplicate card IDs or enforce one live container per card. Direct mutation methods can bypass a future manager. UUID defaults alone do not prevent supplied or copied IDs from being reused.
3. **Removal lists are not full history.** They lack destinations, arrival/return events, and a global movement sequence. They are not a reliable current-location index.
4. **Defaults disagree with an earlier answer.** Current deck defaults are no jokers and shuffle enabled. The earlier user answer requested shuffle disabled for now. Flag the discrepancy; do not silently change it during review.

The card-origin gap was fixed in commit `8359837`: both jokers now receive the deck origin, and the property is named `originContainerId`. Do not repeat that resolved finding without checking for a regression.

## Next milestone and logical sequence

The next task is planning/learning about **atomic card movement with complete transfer history**, guided by the user's next request. Use the detailed November 23 section of [DEVELOPMENT_PLAN.md](DEVELOPMENT_PLAN.md).

1. Define the smallest transfer request and its validation rules.
2. Decide how a central manager owns/registers containers and authoritative state, fitting the existing classes before any refactor.
3. Define immutable records for completed transfers and clear success/failure outcomes. Record card/source/destination IDs and ordering; exact types and optional timestamps/positions are still design choices.
4. Stage independent changes, validate invariants, and publish the whole accepted state including history once. Failed operations preserve contents, positions, removal logs, and history. Avoid shallow copies sharing mutable collections and staged changes followed by separate live mutations.
5. Derive current location from authoritative contents initially. Add an index only if useful and kept consistent.
6. Verify the success and failure cases, then introduce the single-deck Android playground with placeholder UI.

**Completion example:** deck → hand → discard → original deck yields three ordered transfers for one card ID, unchanged origin, and accurate current location. An intentionally rejected transfer changes nothing. Check duplicates, empty/missing sources, missing destination/card, invalid positions if supported, same-container requests, and destination rejection.

**Only immediate open rules:** Should shuffle/shift have separate history events, and may a return target a different deck? Start with the original-deck example without deciding broader rules silently. Do not turn these into an exhaustive future-system questionnaire.

## Product decisions to preserve

- WarPG I: offline single-player Android, local saves, no premium currency or required server; manageable learning/release scope.
- WarPG II: later online single-player with story/deeper progression and premium currency.
- WarPG PvP: separate competitive design; matchmaking and balance later.
- The initial deck playground has no game mechanics, but the full first release includes gameplay. Add XP, battles, mini-games, saves, and idle work incrementally after the foundation, with placeholder UI alongside them.
- Shoe and iOS remain deferred. Existing card/player/suit progression ideas remain recorded; card levels do not increase base War value.
- The chat update's alternative hand classification and UI-last sequence are suggestions, not reversals of the user's explicit pile classification and early UI decisions.

## Validation and references

Last check on November 23: `./gradlew compileKotlin test --offline` succeeded. Tests reported `NO-SOURCE`: no automated tests exist. No runtime scenario checks were performed during that review. The development-plan update was documentation-only.

Read in this order:

1. [Development plan — newest dated section](DEVELOPMENT_PLAN.md).
2. [November 23 chat update](resourceDocs/waRPG_Chat_Update_2026-11-23.md) — actual filename casing matters on other operating systems.
3. [Features](FEATURES.md) and [original concept](WARPG_CONCEPT.md) — preserve their requirements, but older implementation-status statements are stale.

Suggested structures in the chat update (maps, `GameState`, result types, rules interfaces, module layouts) are examples. They are not code to paste into the project or permission to undertake a redesign.
