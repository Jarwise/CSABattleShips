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
        hitmiss.setHorizontalAlignment(SwingConstants.CENTER);
        shout.add(scream);
        shout.add(hitmiss);
        shout.setLayout(new GridLayout(2, 1));
        shout.setTitle("What's happening");
        shout.setSize(200, 150);
        shout.setLocation(450, 550);
        shout.setVisible(false);

        PlayerGrid mygrid = new PlayerGrid(10, 10, "Player's grid");
        Grid opgrid = new Grid(10, 10, "Opponent's grid (attack here)");  // opponent's grid
        mygrid.visible(true);

        mygrid.setShips();
        opgrid.setShips();
        System.out.println("  His grid: "); opgrid.image(); System.out.println("BTW You are not supposed to see this ;)\n");
        System.out.println("  My grid: "); mygrid.image();

        System.out.println("\n\n  Let's play!!\n");
        opgrid.visible(true);
        mygrid.move();
        mygrid.clean(); opgrid.clean();
        int scoreP = 0, scoreO = 0, win = 19; // out of (5+4+3+3+2+2) = 19
        mygrid.setScore(scoreO, scoreP); opgrid.setScore(scoreO, scoreP);
        Boolean gameOver = false;
        Boolean turn = true; // true = your turn; false = opponent's turn
        mygrid.disableButtons(true);
        mygrid.onlyGrid();
        while(!gameOver){
            if(turn){
                shout.setVisible(true); scream.setText("It is your turn");
                opgrid.disableButtons(false);
                if(opgrid.setPressed()){
                    if(opgrid.get(opgrid.getLastCoordinatesX(), opgrid.getLastCoordinatesY()) == 2 || opgrid.get(opgrid.getLastCoordinatesX(), opgrid.getLastCoordinatesY()) == 5){
                        scoreP++; 
                        mygrid.setScore(scoreO, scoreP); opgrid.setScore(scoreO, scoreP); 
                        hitmiss.setText("*** YOU HIT! ***");
                        if(scoreP == win){
                            scream.setText("################ GAME OVER ################"); hitmiss.setText("------- YOU WON -------");
                            shout.setSize(500, 100); shout.setLocation(250, 550);  gameOver = true;}
                    }
                    else{hitmiss.setText("--- YOU MISSED ---"); turn = false; opgrid.disableButtons(true);}
                }
                
                continue;
            }
            if(!turn){
                scream.setText("It's your Opponent's turn"); 
                try{
                    Thread.sleep(3000);}
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
                    //System.out.println("Your opponent attacked " + alphabet[turny] + turnx + ".");
                    if(mygrid.get(turnx, turny) == 2 || mygrid.get(turnx, turny) == 5){
                        scoreO++; 
                        opgrid.setScore(scoreO, scoreP);
                        hitmiss.setText("*** OPPONENT HIT! ***");
                        if(scoreO == win){scream.setText("################ GAME OVER ################"); hitmiss.setText("------- YOU LOSE -------");
                        gameOver = true;}
                    }
                    else{opgrid.setScore(scoreO, scoreP); hitmiss.setText("--- OPPONENT MISSED ---"); turn = true;}
                }
                continue;

            }
        }
    }
}
