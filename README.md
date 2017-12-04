# Java Game Engine
A game engine I am working on in my free time! This engine is currently
only in two dimensions but I plan to implement it into three dimensions
soon!

## How it works so far
#### Rendering Images
We render images by keeping a pixel list of all the pixels on the screen. When we want to draw an image we first get all the pixels from the image then change the specific pixel in our screen pixels list. 

#### Shaders
We have a map of locations and light we want the location to be. We then go to all those locations and make them lighter.

#### Collision
Each sprite has it's own hitbox and when the entity tries to move
we check all the other entities hitboxs that have a hitbox component

## Game Colllection
I am current have worked on two games. Those are:
- [Frameshift](https://github.com/Avery246813579/Frameshift) - A game with a custom engine I wrote a few years back
- [Solenoid](https://github.com/Avery246813579/Solenoid) - A game I wrote for Ludum Dare a few years back. Also has a custom engine

## TODO 
This section contains a list of things I want to add to this engine!

#### Features
- Maps (Levels)
- Viewport (Camera)
- Terrain ()
- Music and Sound
- Custom Font
- Projectiles
- Text Attached to Canvas vs Viewport
- Language and Text
- Backgrounds
- Input with listeners
- Sprite Sheet
- Tilemap
- AI
- Particle Effects 

#### Enhances
- Improve Shader Speed
- More advanced collision with more types and better detection

#### Bugs
- If you get too close with new close hitbox detection you can get stuck

Games I want to make: 
- Animal Crossing
- Terreria 
- Pokemon
- Stardew Valley
- Cuphead
- League of Legends 
- Civ
- Undertale