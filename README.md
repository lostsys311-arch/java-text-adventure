# Text Adventure

A Java text adventure game that runs on desktop (CLI) and Android.

## Gameplay

Explore a castle, collect items, and find your way through rooms including:
- Castle Entrance
- Great Hall
- Kitchen
- Treasury
- Armory
- Dungeon

**Commands:** `go <dir>`, `look`, `take <item>`, `drop <item>`, `inventory`, `help`, `quit`

## Building

### Desktop (CLI)
```bash
./gradlew :core:run
```

### Android (APK)
```bash
./gradlew :app:assembleDebug
```
APK output: `app/build/outputs/apk/debug/app-debug.apk`

## Structure
```
core/       - Game logic (Java library, reused by both targets)
app/        - Android app
```
