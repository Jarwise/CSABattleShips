# CSABattleShips

DESCRIPTION OF THE GAME:\
In this repository, you can find a one player Battleships game, where the human player plays against a simple algorithm.\
The point of the game is to track down all of the opponent's ships in his grid, while not getting all of your ships shot down in a meanwhile.

You start the game by placing your ships into the grid (for now the set of ships consists of 6 ships (5, 4, 3, 3, 2, 2) but can be easily changed), you can place ships into the grid by clicking on the chosen field (once for horizontal, twice for verical) and confirming by clicking the "ok" button. You can also notice that the grid doesn't let you place the ship to the field where it would stick-out of the board or right next to another ship (the green fields) therefore your ships have to be more spread out, fortunately, bot generating his ships also has to comply to the same rules.

![alt text](https://github.com/Jarwise/CSABattleShips/blob/main/setships.png?raw=true)

When you are done with placing your ships you will begin to see two grids - your opponent's, which will be empty at this point and your task is to try to find all the enemy ships, and your grid, where your opponent tries to shoot your ships down and you can see its progress and at the bottom you can find the info-text field which gives you info of whos turn it is. Two 'players' are taking turns with an exception of when someone gets a "HIT" on an opponent's ship, then this player gets an extra turn. The point of the game is to find all of the opponent's ships, the first one to do si wins.

![alt text](https://github.com/Jarwise/CSABattleShips/blob/main/game.png?raw=true)

Size of the grids is easily adjustable at the start of BattleShips.java file.

TEAM:\
Maťo - user inteface and implementation of the grids\
Samo - bot's algorithm - looking for player's ships

CODE:\
The code is divider only into three main parts: 
 - BattleShips.java - the main method
 - Grid.java - class for both grids and all the methods for the grid
 - PlayerGrid.java - subclass extends Grid.java and add methods specific for players's grid, 
   like manual setting ships and previously also picturing player's grid differently
 - Pair.java - only a simler implementation of Pair<int, int>
