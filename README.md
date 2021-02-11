# CSABattleShips

In this repository, you can find a one player Battleships game, where the human player plays against a simple algorithm. The point of the game is to track down all of the opponent's ships in his grid, while not getting all of your ships shot down in a meanwhile.

![alt text](https://github.com/Jarwise/CSABattleShips/blob/main/setships.png?raw=true)

You start the game by placing your ships into the grid (for now the set of ships consists of 6 ships (5, 4, 3, 3, 2, 2) but can be easily changed), you can place ships into the grid by inserting coordinated of the wanted fields in format "vB6" where 'v' stands for vertically and 'h' for horizontally rotated, letter specifies the column and number the row of the upper left corner of the ship in the grid. You can also notice that the grid doesn't let you place the ship to the field where it would stick out of the board or right next to another ship (the green fields) therefore your ships have to be more spread out, fortunately, bot generating his ships also has to comply to the same rules.

When you are done with placing your ships you will begin to see two grids every iteration - opponent's grid, which will be empty at this point and your task is to try to find all the enemy ships in this one, and your grid, where your opponent tries to shoot your ships and you can see its progress. Two 'players' are taking turns with an exception of when someone gets an opponent's ship, then this player gets an extra turn. The point of the game is to find all of the opponent's ships the first to win.

Size of the grids is easily adjustable at the start of BattleShips.java file.
