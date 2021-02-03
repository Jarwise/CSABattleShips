import java.util.Scanner;

public class PlayerGrid extends Grid {
    public static Scanner scany = new Scanner(System.in);

    public PlayerGrid(int row, int col){
        super(row, col);
    }

    public void setShips(){
        this.image();
        int[] ships = {5, 4, 3, 3, 2, 2};
        int index = 0;
        String coor;
        //Scanner scany = new Scanner(System.in);
        while(index != ships.length){
            System.out.println("  Unplaced ships: ");
            for(int i = index; i < ships.length; i++){
                for(int j = 0; j < ships[i]; j++){
                    System.out.print(blue+"■ "+reset);
                }
                System.out.print(" ("+ships[i]+")");
                if(i < ships.length-1) System.out.print(",  ");
            }
            System.out.println("\n\n  Chose coordinates for the upcoming ship (its left upper corner)");
            System.out.println("  Use following format: "+green+"vB5"+reset+" (v for vertical, h for horozintal)");
            
            while(true){                        // waiting for correct format of coordinates (many ifs)
                coor = scany.next();
                if(coor.charAt(1) >= 'A' && coor.charAt(1) < ('A'+super.columns) && (coor.charAt(2)-'0') >= 0 && (coor.charAt(2)-'0') < super.rows && 
                (coor.charAt(0) == 'v' || coor.charAt(0) == 'h') ){ 
                    Boolean okey = true;
                    if(coor.charAt(0) == 'v') for(int i = 0; i < ships[index]; i++){
                        if(coor.charAt(2)-'0'+i >= super.rows){
                            System.out.println("It looks like this type of ship cannot be placed on the given coordinates");
                            okey = false;
                            break;
                        }
                        else if(super.matrix[coor.charAt(2)-'0'+i][coor.charAt(1)-'A'] != 0){
                            System.out.println("It looks like those coordinates are occupied");
                            okey = false;
                            break;
                        }
                    }
                    if(coor.charAt(0) == 'h') for(int i = 0; i < ships[index]; i++){
                        if(coor.charAt(1)-'A'+i >= super.columns){
                            System.out.println("It looks like this type of ship cannot be placed on the given coordinates");
                            okey = false;
                            break;
                        }
                        else if(super.matrix[coor.charAt(2)-'0'][coor.charAt(1)-'A'+i] != 0){
                            System.out.println("It looks like those coordinates are occupied");
                            okey = false;
                            break;
                        }
                    }
                    if(!okey) continue;
                    break; 
                }
                else{ 
                    System.out.println("I'm sorry it looks like you have entered incorrect data, try again");
                    continue;
                }
            }

            System.out.println("  Given coordinates: "+coor);
            if(coor.charAt(0) == 'h'){
                for(int i = 0; i < ships[index]; i++){     // adding those yellow fields (ship range)
                    super.matrix[coor.charAt(2)-'0'][coor.charAt(1)-'A'+i] = 4;
                    if(coor.charAt(2)-'0'+1 < super.rows){ if(super.matrix[coor.charAt(2)-'0'+1][coor.charAt(1)-'A'+i] == 0){super.matrix[coor.charAt(2)-'0'+1][coor.charAt(1)-'A'+i] = 3;}}
                    if(coor.charAt(2)-'0'-1 >= 0){ if(super.matrix[coor.charAt(2)-'0'-1][coor.charAt(1)-'A'+i] == 0){super.matrix[coor.charAt(2)-'0'-1][coor.charAt(1)-'A'+i] = 3;}}
                    if(coor.charAt(1)-'A'+i+1 < super.columns){ if(super.matrix[coor.charAt(2)-'0'][coor.charAt(1)-'A'+i+1] == 0){super.matrix[coor.charAt(2)-'0'][coor.charAt(1)-'A'+i+1] = 3;}}
                    if(coor.charAt(1)-'A'+i-1 >= 0){ if(super.matrix[coor.charAt(2)-'0'][coor.charAt(1)-'A'+i-1] == 0){super.matrix[coor.charAt(2)-'0'][coor.charAt(1)-'A'+i-1] = 3;}}
                }
            }
            if(coor.charAt(0) == 'v'){
                for(int i = 0; i < ships[index]; i++){     // adding those yellow fields (ship range)
                    super.matrix[coor.charAt(2)-'0'+i][coor.charAt(1)-'A'] = 4;
                    if(coor.charAt(2)-'0'+i+1 < super.rows){ if(super.matrix[coor.charAt(2)-'0'+i+1][coor.charAt(1)-'A'] == 0){super.matrix[coor.charAt(2)-'0'+i+1][coor.charAt(1)-'A'] = 3;}}
                    if(coor.charAt(2)-'0'+i-1 >= 0){ if(super.matrix[coor.charAt(2)-'0'+i-1][coor.charAt(1)-'A'] == 0){super.matrix[coor.charAt(2)-'0'+i-1][coor.charAt(1)-'A'] = 3;}}
                    if(coor.charAt(1)-'A'+1 < super.columns){ if(super.matrix[coor.charAt(2)-'0'+i][coor.charAt(1)-'A'+1] == 0){super.matrix[coor.charAt(2)-'0'+i][coor.charAt(1)-'A'+1] = 3;}}
                    if(coor.charAt(1)-'A'-1 >= 0){ if(super.matrix[coor.charAt(2)-'0'+i][coor.charAt(1)-'A'-1] == 0){super.matrix[coor.charAt(2)-'0'+i][coor.charAt(1)-'A'-1] = 3;}}
                }
            }
            this.image();
            index++;

        }
        //scany.close();
    }

    public void image(){
        System.out.print("\n  ");
        char c = 'A';
        for(int i = 0; i < super.columns; i++){    // letter of a column
            System.out.print(" "+c);
            c++;
        }
        System.out.print("\n");
        for(int i = 0; i < super.rows; i++){
            if(i < 10) System.out.print(" ");
            System.out.print(i);                      // number of a row
            for(int j = 0; j < super.columns; j++){
                int x = super.matrix[i][j];
                switch (x) {
                    case 0: System.out.print(" ■"); break;  // empty not yet attacked field
                    case 1: System.out.print(red+" X"+reset); break;  // empty attacked field
                    case 2: System.out.print(" #"); break;  // "hit" attacked ship
                    case 3: System.out.print(green+" ■"+reset); break; // ship range
                    case 4: System.out.print(blue+" ■"+reset); break; // your ship
                    default: System.out.print(" O"); break; // debug (the rest)
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
}
