# Backyard Adventure

A small top-down 2D game built with Java Swing. Walk a kid character around
a backyard, dodge the trees, and collect all 5 stars to win.

Currently uses simple placeholder shapes for the character and items —
built so real sprite images can be dropped in later (see `Player.java`).

## Controls
- Move: Arrow keys or `W` `A` `S` `D`

## Requirements
- Java 17 or newer (JDK)

## Build & Run

```bash
# From the project root
mkdir -p bin
javac -d bin src/*.java
java -cp bin Main
```

## Project structure
```
src/
  Main.java       # entry point, creates the game window
  GamePanel.java  # game loop, input handling, rendering
  Player.java     # the kid character (movement, placeholder sprite)
  Item.java       # collectible stars
  Direction.java  # facing direction enum
```

## Adding your own sprite
`Player.java` has a commented-out `loadSprite(...)` method and a hook in
`draw()` — once you have sprite image files, load them there (e.g. via
`ImageIO.read`) and swap in `g.drawImage(...)` instead of the placeholder
shapes. For animation, extend the existing `animFrame` logic to pick the
right frame from a sprite sheet.

## Ideas for next steps
- Swap in real sprite images (idle / walk frames per direction)
- Add sound effects for collecting stars
- Add multiple levels / a timer / a high score
