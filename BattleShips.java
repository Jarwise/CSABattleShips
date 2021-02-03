import java.util.Scanner;

public class BattleShips {
    public static void main(String[] args){
        PlayerGrid mygrid = new PlayerGrid(10, 10);
        System.out.println("\n  My grid: ");
        mygrid.image();

        Grid opgrid = new Grid(10, 10);  // opponent's grid
        System.out.println("  His grid: ");
        opgrid.image();

        mygrid.setShips();
        opgrid.setShips();

        System.out.println("\n\n  Let's play!!\n");
        Grid.Image(opgrid, mygrid);
        Boolean gameOver = false;
        Boolean turn = true; // true = your turn; false = opponent's turn
        String coor;
        while(!gameOver){
            if(turn){

                System.out.println("  It is your turn, choose coordinates: (format - B1)");
                Scanner scany = new Scanner(System.in);
                coor = scany.next();
                scany.close();
                while(true){
                    if((coor.charAt(0)-'A') >= 0 && (coor.charAt(0)-'A') < mygrid.getCol() && (coor.charAt(1)-'0') >= 0  && (coor.charAt(1)-'0') < mygrid.getRow()){break;}
                    else{
                        System.out.println("I'm sorry it looks like you have entered incorrect data, try again");
                        continue;
                    }
                }
                System.out.println("  Given coordinates: "+coor);
                int X = coor.charAt(1) - '0';
                int Y = coor.charAt(0) - 'A';
                if(opgrid.set(X, Y)){
                    Grid.Image(opgrid, mygrid);
                    if(opgrid.get(X, Y) == 2){System.out.println("******************* HIT!! ********************\n");}
                    else{System.out.println("---------------- MISS -----------------\n"); turn = false;}
                }
                else{
                    System.out.println("  You have already attacked this field!");
                    Grid.Image(opgrid, mygrid);
                }
                continue;
            }
            if(!turn){
                System.out.println("Samo's code here"); break;
            }
        }
        //scany.close();
    }

}
