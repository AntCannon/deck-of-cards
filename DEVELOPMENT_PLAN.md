# WarPG Development Plan

This is a learning roadmap: steps, milestones, and completion goals rather than implementation instructions or finished code. Build each small feature alongside a simple mobile interface, using placeholder visuals until the gameplay works.

## Current milestone: card movement foundation

Implement the supporting models and deck manager before further card interactions. Keep this a learning exercise: the steps below describe logical goals, not code or implementation instructions.

1. **Card identity** — Distinguish individual cards, even when rank and suit match.
2. **Pile** — Define a named collection of cards, covering hands and discard piles.
3. **Shoe** — Define a collection of decks.
4. **Card location and origin** — Distinguish where a card currently is from which deck it came from.
5. **Movement record** — Define what a completed move records. Preserve the full history, including returns.
6. **Deck manager** — Establish its responsibility for coordinating the shoe, decks, piles, and card movement.
7. **One movement path** — Start with moving a selected card from a deck into a pile.
8. **Movement validation** — Ensure invalid moves leave everything unchanged and valid moves neither duplicate nor lose cards.
9. **Additional movement paths** — Add pile-to-pile movement and returning cards to decks.
10. **Tracking queries** — Make it possible to inspect a card's history and identify cards currently outside their original deck.

**First milestone:** Move one identifiable card from a deck into a named pile, then inspect its location and movement record.

**Confirmed tracking decision:** Keep full movement history. Returning a card must not erase its earlier removal or intervening moves. Current location and historical movement are separate information.

This order takes precedence over the earlier roadmap's placement of piles after deck interactions and card identity near progression. The single-deck Android playground with placeholder UI remains the next visible application goal; game mechanics remain outside the initial build. Returning cards to a different deck is still an unresolved rule.

See [Features and Definitions](FEATURES.md) for the consolidated requirements. The original review and inline responses below are preserved as historical context, not a fresh assessment of implementation progress on this branch.

## Earlier decisions and review

Android is the first target, with iOS work after major milestones. The initial build is a basic deck playground with placeholder UI and deck actions; War and progression are later experiments, outside the initial build. You confirmed that the existing build runs. Shuffle is now implemented and enabled by default. Baseline checks and the first mobile screen remain ahead.

Your inline responses below are preserved. Explanations, shuffle feedback, recorded answers, and the two current milestone questions are in [Development Questions and Decisions](https://github.com/AntCannon/deck-of-cards/blob/feature/ac/deck-shuffle-and-mobile-plan/DEVELOPMENT_QUESTIONS.md). The roadmap's later milestones remain proposals.

## Current codebase review

Reviewed the Kotlin source, Gradle configuration, README, and `WARPG_CONCEPT.md`.

- **Foundation:** `Card`, `Rank`, and `Suit` describe cards. `Deck` stores cards and supports adding and listing them. `createDecks` creates multiple decks with optional jokers and rejects nonpositive deck counts.
- **Current behavior:** Default options create one deck with 54 cards. Standard decks without jokers contain 52 cards.
- **Shuffle update:** `DeckOptions.shuffleDeck` is now honored by the factory after all cards are added. It defaults to `true`.
  - I updated this option review the functionality and provide feedback on implementation.
- **Missing interactions:** Drawing, returning, and piles are not implemented. Gameplay is deferred beyond the initial build.
  - to be implemented
- **State concern:** `Deck.getCards()` exposes the backing collection through a read-only list type. Previously obtained lists reflect later deck changes; decide whether callers should receive a snapshot or a live view.
  - In a game when a player takes an action they should see the current deck. Actions should be taken on a live deck.
- **Model decisions:** Aces currently have value 1, and jokers have values 14 and 15. These are existing model values, not confirmed War rules. The card model also permits suitless standard cards and suited jokers; decide what combinations are valid.
  - Response in section 1
- **Future progression concern:** Cards currently have rank/suit equality and no individual identity. Tracking separate copies of the same card will need a decision before individual card progression.
- **Application structure:** This is a Kotlin/JVM project using a Java 24 toolchain. There is no mobile application module or UI. `Main.kt` creates decks and prints a count; helper functions can print card details.
- **Verification and documentation:** Kotlin test dependencies are configured, but there are no test source files. The README now summarizes the technical baseline and initial scope. You confirmed that the build runs; this review used source inspection without rerunning it.

`WARPG_CONCEPT.md` remains the source of confirmed ideas. The sequence below is proposed; it does not finalize the open gameplay, economy, or progression rules.

## Working approach

For each milestone:

1. Pick one small behavior and describe its expected result.
2. Identify any rule you need to decide before building it.
3. Build a standalone Kotlin exercise when the mechanic needs exploration.
4. Connect the behavior to a basic mobile screen.
5. Check the normal path and relevant edge cases.
6. Record what you learned and choose the next small step.

Keep game rules independent of the UI. Use text, rectangles, simple suit symbols, and ordinary buttons as placeholders. Add meaningful checks for rules and state changes as you build them.

## 1. Choose the first mobile target and establish a baseline

- Choose Android first or a shared Android/iOS direction. Android first is a proposed scope reduction for learning, not a confirmed platform requirement.
  - Since I will be doing one main feature at a time I can do Android first then do the IOS version after major milestones.
- Confirm that the existing project builds and runs.
  - Existing build runs.
- Establish checks for deck size, rank/suit composition, optional jokers, multiple decks, and invalid deck counts.
  - Deck size: A standard deck's size should only be 52 or 54 depending on jokers. In the future there can be special decks, and custom decks.
  - Ranks Suit composition: Right now this is just a regular deck of cards standard Big and Little Jokers will have a Rank of BJ and LJ respectively and be suitless. In the future we can develop Jokers that have suits. Values for cards right now are not being calculated with War in mind. There will be options to set card values with skills, power ups, buffs and debuffs. There a standard card should not be suitless. Later on there can be a status that removes a suit/rank from a card removing any rank / card skills it may have making it a rankless / suitless card.
- Decide which card combinations are valid and what callers should see when reading a deck.
  - not sure what you mean by card combinations. I will have to make a deck manager but generally callers should not change anything without a setter. Explain this concept more.
- Add a short README describing the current project and the next milestone.
  - You can create a simple minimal technical Readme. Right now this is just a simple deck of cards where users can create a deck, draw a card, remove a card to a discard pile, shuffle a deck and do basic actions. Game mechanics are not included i hte initial build

**Done when:** You can explain the current models and demonstrate that deck creation behaves as intended.

## 2. Create the smallest mobile shell

- Add a mobile app structure suited to the chosen target, keeping the existing card logic reusable.
- Create one deck playground screen with a title, deck count, card count, card display area, and a create-deck action.
- Show an explicit empty state before a deck exists.
- Use placeholder cards with readable rank and suit labels.
- Connect deck creation to the screen and display real card data.

**Done when:** The app runs on an emulator or device, and creating a deck visibly changes the screen.

## 3. Build deck interactions alongside the UI

- Verify the implemented shuffle behavior and confirm the intended default.
- Add drawing a card, including an empty-deck outcome.
- Define returning a card, then add that interaction.
- Define deck deletion and what happens to cards outside the deck before adding it.
- Display the current card and remaining count after each action.
- Make unavailable actions clear and show brief feedback for each interaction.

**Done when:** You can create, shuffle, draw from, return cards to, and delete a deck through the playground, including empty states.

## 4. Explore piles and card presentation

- Define pile ownership and the meaning of transferring or discarding cards.
- Build a small pile exercise and expose it on the playground.
- Show pile counts and simple card previews.
- Clarify the differences between viewing, showing, flipping, and playing before adding those actions.
- Add a placeholder card back if face-down cards are needed.
- Check that transfers preserve the expected total number of cards and do not accidentally duplicate them.

**Done when:** You can follow a card between the relevant locations and explain why each action changes its state.

## 5. Build one playable War experiment

- Write down the first rules: ace ordering, joker inclusion, dealing, winner collection, ties, insufficient cards during a tie, and match completion.
- Define a restart or exit path for a match that runs longer than desired.
- Build round resolution as a standalone mechanic, then connect it to a battle screen.
- Show player and opponent card counts, two placeholder played cards, a round result, and a next-round action.
- Add tie handling and the match-end state in small increments.
- Check wins, losses, ties, card conservation, and exhausted-player cases.

**Done when:** You can play and restart a complete match using the mobile UI, with no unresolved behavior in the chosen rule set.

## 6. Make the basic mobile experience comfortable

- Review readability, touch targets, scrolling, and spacing on small screens.
- Ensure suits and outcomes remain understandable without relying only on color.
- Prevent repeated taps from resolving the same action more than once.
- Decide what happens during rotation, backgrounding, and reopening the app.
- Add simple navigation only when multiple screens need it.
- Keep placeholder art while correcting confusing interactions.

**Done when:** The core loop is understandable and behaves predictably during ordinary phone use.

## 7. Introduce progression one experiment at a time

- Choose one player XP reward and level rule as an explicit experiment.
- Add a minimal progress display and verify the reward is applied once.
- Decide how individual card copies are identified before tracking their progress.
- Explore card XP separately; card levels must never change base War value.
- Explore suit progression and one proposed skill before planning a full talent tree.
- Check both suit-skill unlock status and the individual card's required level.
- Define point spending and respec rules before exposing those actions.

**Done when:** One small progression loop works visibly and its rules are documented. Expand only after you understand that loop.

## 8. Add saving, then explore time-based progression

- Choose which progress should survive closing the app and whether active matches are saved.
- Add local saving and check a fresh install, restart, and missing or invalid saved data.
- Select one idle activity and explicitly decide its timer, reward, offline behavior, and limits.
- Add a simple timer or claim panel using placeholder content.
- Check repeated claims and clock changes against the chosen rules.

**Done when:** Progress survives reopening, and one time-based activity behaves consistently under its documented rules.

## Later decisions

Defer premium currency, monetization, multiple skill-point systems, extensive talent trees, power-ups, and broad balance work until their rules and purpose are clearer. Final artwork, elaborate animations, cloud accounts, and online features are outside the initial roadmap unless a concrete need emerges.

## Earlier mobile starting sequence (after the current foundation milestone)

1. Choose the first mobile target.
2. Run the existing project and inspect a generated deck.
3. Check deck creation against a few expected outcomes.
4. Sketch the deck playground on paper.
5. Start the mobile shell and display one placeholder card.

After the card movement foundation, the first mobile milestone is a screen that creates a real deck and displays its card count. Complete that before expanding the screen or adding RPG systems.
