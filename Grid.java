import java.lang.Math;

public class Grid {
int columns, rows;
int [][] matrix; //hmm, any better name?

    public Grid(int row, int col){
        this.columns = col;
        this.rows = row;
        this.matrix = new int[row][col];
        this.reset(0);
    }

    public void image(){
        System.out.print("\n");
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.columns; j++){
                int x = this.matrix[i][j];
                switch (x) {
                    case 0: System.out.print(" ■"); break;  // empty not yet attacked field
                    case 1: System.out.print(" X"); break;  // empty attacked field
                    case 2: System.out.print(red+" ■"+reset); break;   // "hit" attacked ship
                    case 3: System.out.print(yellow+" ■"+reset); break; // opponents range of the ship (invisible)
                    case 4: System.out.print(green+" ■"+reset); break;   // opponents ships (invisible)
                    default: System.out.print(blue+" O"+reset); break; // debug (the rest)
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    public void setShips(){
        int[] ships = {5, 4, 3, 3, 2, 2};
        int X, Y, V, H, index = 0;
        while(index != ships.length){
            X = (int)(Math.random()*this.rows);
            Y = (int)(Math.random()*this.columns);
            V = (int)(Math.random()*2); // the direction of the ship
            H = (-1)*V +1;
            Boolean okey = true;
            for(int i = 0; i < ships[index]; i++){
                if(X+V*i >= this.rows || Y+H*i >= this.columns){ okey = false; break; }
                else if(this.matrix[X+V*i][Y+H*i] != 0){ okey = false; break; }
            }
            if(okey){
                for(int i = 0; i < ships[index]; i++){
                    this.matrix[X+V*i][Y+H*i] = 4;
                    if(X+V*i+1 < this.rows){if(this.matrix[X+V*i+1][Y+H*i] == 0){this.matrix[X+V*i+1][Y+H*i] = 3;}}
                    if(X+V*i-1 >= 0){if(this.matrix[X+V*i-1][Y+H*i] == 0){this.matrix[X+V*i-1][Y+H*i] = 3;}}
                    if(Y+H*i+1 < this.columns){if(this.matrix[X+V*i][Y+H*i+1] == 0){this.matrix[X+V*i][Y+H*i+1] = 3;}}
                    if(Y+H*i-1 >= 0){if(this.matrix[X+V*i][Y+H*i-1] == 0){this.matrix[X+V*i][Y+H*i-1] = 3;}}
                }
                index++;
            }
        }
        this.image();
    }

    private void reset(int value){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.columns; j++){
                this.matrix[i][j] = value;
            }
        }
    }
    public static final String reset = "\u001B[0m";
    public static final String black = "\u001B[30m";
    public static final String red = "\u001B[31m";
    public static final String green = "\u001B[32m";
    public static final String yellow = "\u001B[33m";
    public static final String blue = "\u001B[34m";
    public static final String purple = "\u001B[35m";
    public static final String cyan = "\u001B[36m";
    public static final String white = "\u001B[37m";
}
