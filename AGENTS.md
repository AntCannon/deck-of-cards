# Guidance for Codex

## Collaboration and learning

The user is learning Kotlin and wants to implement the application themselves. Default to reviewing their work and explaining logical steps, responsibilities, and outcomes. Do not implement application changes or rewrite their classes unless they explicitly ask you to do so. A request to review is not permission to fix code. Provide code examples when explicitly requested, without automatically editing files.

When reviewing, inspect the actual current files first. Lead with concrete defects and their effects, link to relevant lines, distinguish unfinished features from bugs, and acknowledge fixes without repeatedly reopening resolved issues. State what was compiled or tested; compilation alone is not behavioral verification. Do not invent additional requirements to fill design gaps.

Keep explanations focused on the current milestone. Do not produce exhaustive future-feature questions. Discuss large architectural changes before replacing working classes. Preserve unrelated edits, staged changes, and the user's chosen branch. Commit/push only when requested, to the destination authorized in that request; do not infer a standing preference for `main` from prior pushes.

## Start of a session

1. Read [HANDOFF.md](HANDOFF.md) for the last checkpoint and immediate next work.
2. Read the newest dated section of [DEVELOPMENT_PLAN.md](DEVELOPMENT_PLAN.md).
3. Consult [FEATURES.md](FEATURES.md), [WARPG_CONCEPT.md](WARPG_CONCEPT.md), and the relevant [chat update](resourceDocs/waRPG_Chat_Update_2026-11-23.md) for background.
4. Check Git status and inspect the relevant source before making recommendations. Handoff status can become stale; do not reset or overwrite local work to match it.

Follow the user's latest explicit instructions. Preserve historical requirements and distinguish confirmed decisions from proposals. The November 23 plan reconciles conflicts in older documents; those older implementation-status tables are not the current source of code facts. The chat update contains illustrative snippets, not instructions to implement every example.

## Current scope and invariants

- WarPG I is an offline, single-player Android idle RPG with local saves, XP, mini-games, battles, and idle activities. No premium currency or required server in this release.
- WarPG II is later online single-player work; PvP is a separate future experience. Defer iOS, shoe implementation, servers, matchmaking, and complex progression for the current milestone.
- Retain the early single-deck Android playground and placeholder visuals; build UI alongside features after the safe movement foundation.
- `Deck` and `Pile` implement `CardContainer`. Piles have container type `PILE` and a `PileType` of `HAND`, `DISCARD`, or `STANDARD`; these classifications do not require subclasses.
- Card UUID and origin remain stable when a card moves. Origin is separate from current location. Full transfer history includes returns.
- A successful transfer preserves card uniqueness and total membership; a failed transfer must leave state and history unchanged. All-or-nothing movement is the next requirement, not an already implemented guarantee.
- Prefer deriving locations from authoritative container contents initially. A separate location index is a proposal and must not diverge from contents.
- Shuffling/shifting are internal reorders, not departures. Whether they get their own history events is unresolved.
- Card levels never increase base War value. Buff/debuff effects are separate, still undefined mechanics.

## Project and verification

The current project is Kotlin/JVM, not yet an Android app. Source is under `src/main/kotlin`. Check `build.gradle.kts` and the wrapper for current versions; the handoff checkpoint uses Kotlin 2.4.21 and Java toolchain 24. Explicit backing fields are intentional: public `List<Card>` properties have mutable storage usable within their owning classes. Do not flag this valid Kotlin feature as a compile error based on older language versions.

Use the repository's Gradle wrapper for relevant verification. `./gradlew compileKotlin test` compiles and runs existing tests; `--offline` is suitable only when dependencies and the toolchain are cached. A new computer may need initial dependency/toolchain downloads. Report `NO-SOURCE` honestly: it means no tests ran.

Use relative paths in project documentation so it works across computers. Update the handoff when requested or when completing an authorized documentation handoff; keep durable guidance here and transient status there. Do not implement code merely because a handoff lists it as the next task.
