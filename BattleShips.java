import java.util.Random;
import java.awt.*;  
import javax.swing.*;
  
public class BattleShips {
    public static Random rand = new Random();
    public static void main(String[] args){
        JFrame shout = new JFrame();
        JLabel scream = new JLabel(" ");
        JLabel hitmiss = new JLabel(" ");
        scream.setHorizontalAlignment(SwingConstants.CENTER);
        hitmiss.setHorizontalAlignment(SwingConstants.CENTER);   // info text field
        shout.add(scream);
        shout.add(hitmiss);
        shout.setLayout(new GridLayout(2, 1));
        shout.setTitle("What's happening");
        shout.setSize(200, 150);
        shout.setLocation(400, 525);
        shout.setVisible(false);

        PlayerGrid mygrid = new PlayerGrid(10, 10, "Player's grid");      // your grid
        Grid opgrid = new Grid(10, 10, "Opponent's grid (attack here)");  // opponent's grid
        mygrid.visible(true);

        mygrid.setShips();
        opgrid.setShips();                                                  // setting ships (both players)
        System.out.println("  His grid: "); opgrid.image(); System.out.println("BTW You are not supposed to see this ;)\n");
        System.out.println("  My grid: "); mygrid.image();

        System.out.println("\n  Let's play!!  \n");
        opgrid.visible(true);
        mygrid.move();
        mygrid.clean(); opgrid.clean();
        int scoreP = 0, scoreO = 0, win = 19; // out of (5+4+3+3+2+2) = 19
        mygrid.setScore(scoreO, scoreP); opgrid.setScore(scoreO, scoreP);
        Boolean gameOver = false;
        Boolean turn = true; // true = your turn; false = opponent's turn
        mygrid.disableButtons(true);
        mygrid.onlyGrid();
        shout.setVisible(true); 
        while(!gameOver){           // the actual game - turn(you); !turn(bot)
            if(turn){
                scream.setText("It is your turn");
                opgrid.disableButtons(false);
                if(opgrid.setPressed()){
                    if(opgrid.get(opgrid.getLastCoordinatesX(), opgrid.getLastCoordinatesY()) == 2 || opgrid.get(opgrid.getLastCoordinatesX(), opgrid.getLastCoordinatesY()) == 5){
                        scoreP++; 
                        mygrid.setScore(scoreO, scoreP); opgrid.setScore(scoreO, scoreP); 
                        hitmiss.setText("*** YOU HIT! ***");
                        if(scoreP == win){
                            scream.setText("################ GAME OVER ################"); hitmiss.setText("------- YOU WON -------");
                            shout.setSize(500, 100); shout.setLocation(250, 525);  gameOver = true;}
                    }
                    else{hitmiss.setText("--- YOU MISSED ---"); turn = false; opgrid.disableButtons(true);}
                }
                
                continue;
            }
            if(!turn){
                scream.setText("It's your Opponent's turn"); 
                try{
                    Thread.sleep(2000);}
                catch(InterruptedException ex){
                    Thread.currentThread().interrupt();}
                hitmiss.setText(" ");
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
                    scream.setText("It's your Opponent's turn"); hitmiss.setText(" ");
                    if(mygrid.get(turnx, turny) == 2 || mygrid.get(turnx, turny) == 5){
                        if((turnx+1 < mygrid.getRow() && mygrid.get(turnx+1,turny) == 2) || (turnx-1 >= 0 && mygrid.get(turnx-1,turny) == 2)){
                            if(turny+1 < mygrid.getCol()){
                                mygrid.matrix[turnx][turny+1] = 6;
                            }
                            if(turny-1 >= 0){
                                mygrid.matrix[turnx][turny-1] = 6;
                            }
                        }
                        if((turny+1 < mygrid.getCol() && mygrid.get(turnx,turny+1) == 2) || (turny-1 >= 0 && mygrid.get(turnx,turny-1) == 2)){
                            if(turnx+1 < mygrid.getRow()){
                                mygrid.matrix[turnx+1][turny] = 6;
                            }
                            if(turnx-1 >= 0){
                                mygrid.matrix[turnx-1][turny] = 6;
                            }
                        }
                        scoreO++; 
                        opgrid.setScore(scoreO, scoreP);
                        hitmiss.setText("*** OPPONENT HIT! ***");
                        if(scoreO == win){scream.setText("################ GAME OVER ################"); hitmiss.setText("------- YOU LOSE -------");
                        shout.setSize(500, 100); shout.setLocation(250, 525); gameOver = true;}
                    }
                    else{opgrid.setScore(scoreO, scoreP); hitmiss.setText("--- OPPONENT MISSED ---"); turn = true;}
                }
                continue;

            }
        }
    }
}
