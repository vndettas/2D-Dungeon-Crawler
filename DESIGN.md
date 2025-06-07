DESIGN

MVC

All graphics are painted in GameGraphics class as a **View** part. Game graphics is panel of a window by GameWindow. Main **MODEL** class that responsible for handling game lose/win conditions, Location, player, inventory - **StateEngine**.     
Mediation between user and GameState class is KeyListener which implements KeyListener and handles input from keyboard.
Check USER MANUAL for details. Both threads using metadata from StateEngine class for update logic and repaint gameGraphics panel
when needed as a part of **Controller**. Class GameThread mainly responsible for player movement and EnemyThread for enemy logic(movement, collision, intersection with player). Map logic is handled
by Location. Map as in most 2d games is divided by Tiles with constant size, those tiles are stored in 2 dimensional array.
Enum used for Tile type logic. Enemies and Items are handled by abstract classes and their subclasses.

**Helper**

Class helper contains Constants, variables(FPS, DELAY BETWEEN FRAMES, WINDOW MEASUREMENTS) that should be global so you can get access to them by simply including the package.
Class Debugger its imitation of c++/c macros that allows to use custom debug when needed(by changing variable inside class).
