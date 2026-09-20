# Backyard Adventure

Un pequeño juego 2D de vista cenital desarrollado con Java Swing. Controla a un personaje infantil por el patio trasero, esquiva los árboles y recoge las 5 estrellas para ganar. 

## Controles
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

## Estructura
```
src/
  Main.java       # entry point, creates the game window
  GamePanel.java  # game loop, input handling, rendering
  Player.java     # the kid character (movement, placeholder sprite)
  Item.java       # collectible stars
  Direction.java  # facing direction enum
```

## Añadan los sprites nubs
`Player.java` contiene un método `loadSprite(...)` comentado y un punto de integración en `draw()`; una vez que dispongas de los archivos de imagen de los *sprites*, cárgalos allí (por ejemplo, mediante `ImageIO.read`) y utiliza `g.drawImage(...)` en lugar de las formas provisionales. Para la animación, amplía la lógica existente de `animFrame` a fin de seleccionar el fotograma correcto de la hoja de *sprites* (*sprite sheet*).

## PENDIENTE:
- Los fukin sprites (idle / walk frames per direction)
- Efectos de sonido al colectar esterllas
- Añadir niveles multiples / un timer / un high score
