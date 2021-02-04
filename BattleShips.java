import java.util.Scanner;
import java.util.Random;
  
public class BattleShips {
       public static Scanner scany = new Scanner(System.in);
       public static char[] alphabet = new char[]{'A','B','C','D','E','F','G','H','I','J',};
       public static Random rand = new Random();
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
        int scoreP = 0, scoreO = 0, win = 19; // out of (5+4+3+3+2+2) = 19
        Grid.Image(opgrid, mygrid, scoreP, scoreO);
        Boolean gameOver = false;
        Boolean turn = true; // true = your turn; false = opponent's turn
        String coor;
        while(!gameOver){
            if(turn){
                System.out.println("  It is YOUR TURN, choose coordinates: (format: B1)"); 
                while(true){
                    coor = scany.next();
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
                    if(opgrid.get(X, Y) == 2 || opgrid.get(X, Y) == 5){
                        scoreP++; 
                        Grid.Image(opgrid, mygrid, scoreP, scoreO); 
                        System.out.println("******************* HIT!! ********************\n");
                        if(scoreP == win){System.out.println("\n ################# GAME OVER ################\n -----------------YOU-WIN---------------"); gameOver = true;}
                    }
                    else{Grid.Image(opgrid, mygrid, scoreP, scoreO); System.out.println("---------------- MISS -----------------\n"); turn = false;}
                }
                else{
                    System.out.println("  You have already attacked this field!");
                    Grid.Image(opgrid, mygrid, scoreP, scoreO);
                }
                continue;
            }
            if(!turn){
                int turnx = 0;
                int turny = 0;
                Boolean goodturn = false;
                for(int i = 0; i < 10; i++){
                    for(int j = 0; j < 10; j++){
                        if((i+1 < 10 && mygrid.get(i+1,j) == 2) || (j+1 < 10 && mygrid.get(i,j+1) == 2) || (i-1 >= 0 && mygrid.get(i-1,j) == 2) || (j-1 >= 0 && mygrid.get(i,j-1) == 2)){
                            if(mygrid.get(i,j) == 0 || mygrid.get(i,j) == 3 || mygrid.get(i,j) == 4){
                                turnx = i;
                                turny = j;
                                goodturn = true;
                            }
                        }
                        if(goodturn){
                            break;
                        }
                    }
                    if(goodturn){
                        break;
                    }
                }
                if(!goodturn){
                    while(!(mygrid.get(turnx,turny) == 0 || mygrid.get(turnx,turny) == 3 || mygrid.get(turnx,turny) == 4)){
                        turnx = rand.nextInt(10);
                        turny = rand.nextInt(10);
                    }
                }
                while(true){
                    if(turny >= 0 && turny < mygrid.getCol() && turnx >= 0  && turnx < mygrid.getRow()){break;}
                    else{
                        continue;
                    }
                }

                if(mygrid.set(turnx, turny)){
                    System.out.println("It is the OPPONENT's turn...");
                    try{
                        Thread.sleep(3000);}
                    catch(InterruptedException ex){
                        Thread.currentThread().interrupt();}
                    System.out.println("Your opponent attacked " + alphabet[turny] + turnx + ".");
                    if(mygrid.get(turnx, turny) == 2 || mygrid.get(turnx, turny) == 5){
                        scoreO++; 
                        Grid.Image(opgrid, mygrid, scoreP, scoreO); 
                        System.out.println("******************* HIT!! ********************\n");
                        if(scoreO == win){System.out.println("\n ################# GAME OVER ################\n -----------------YOU-LOSE---------------"); gameOver = true;}
                    }
                    else{Grid.Image(opgrid, mygrid, scoreP, scoreO); System.out.println("---------------- MISS -----------------\n"); turn = true;}
                }
                continue;

            }
        }
        scany.close();
    }

}
