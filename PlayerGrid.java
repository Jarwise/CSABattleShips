import java.util.Scanner;
import java.awt.*;

public class PlayerGrid extends Grid {
    public static Scanner scany = new Scanner(System.in);

    public PlayerGrid(int row, int col, String title){
        super(row, col, title);
    }

    public void setShips(){
        int[] ships = {5, 4, 3, 3, 2, 2};
        int index = 0;
        String label;
        while(index != ships.length){
            label = "Unplaced ships: ";
            for(int i = index; i < ships.length; i++){
                for(int j = 0; j < ships[i]; j++){
                    label += "■";
                }
                label += " ("+ships[i]+")";
                if(i < ships.length-1) label += ",  ";
            }
            super.text.setText(label);
           
            Boolean placed = false;
            while(!placed){
                for(int i = 0; i < super.rows; i++){
                    for(int j = 0; j < super.columns; j++){
                        if(super.buttons[i][j].getText().equals("x")){
                            Boolean okey = true;
                            for(int a = 0; a < super.rows; a++){for(int b = 0; b < super.columns; b++){if(super.matrix[a][b] == 10){super.matrix[a][b] = 0; super.buttons[a][b].setText(""); super.buttons[a][b].setBackground(null);}}}
                            super.buttons[i][j].setText("H");
                            for(int k = 0; k < ships[index]; k++){
                                if(j+k >= super.columns){okey = false; break;}
                                else if(super.matrix[i][j+k] != 0){okey = false; break;}
                            }
                            if(okey){
                                for(int k = 0; k < ships[index]; k++){
                                    super.matrix[i][j+k] = 10;
                                    super.buttons[i][j+k].setBackground(Color.BLUE);
                                }
                            }
                        }
                        if(super.buttons[i][j].getText().equals("v")){
                            Boolean okey = true;
                            for(int a = 0; a < super.rows; a++){for(int b = 0; b < super.columns; b++){if(super.matrix[a][b] == 10){super.matrix[a][b] = 0; super.buttons[a][b].setText(""); super.buttons[a][b].setBackground(null);}}}
                            super.buttons[i][j].setText("V");
                            for(int k = 1; k < ships[index]; k++){
                                if(i+k >= super.rows){okey = false; break;}
                                else if(super.matrix[i+k][j] != 0){okey = false; break;}
                            }
                            if(okey){
                                for(int k = 0; k < ships[index]; k++){
                                    super.matrix[i+k][j] = 10;
                                    super.buttons[i+k][j].setBackground(Color.BLUE);
                                }
                            }
                            else super.buttons[i][j].setText("");
                        }
                        if(super.ok.getText().equals("__")){
                            super.ok.setText("ok");
                            Boolean didSomething = false;
                            for(int a = 0; a < super.rows; a++){
                                for(int b = 0; b < super.columns; b++){
                                    if(super.matrix[a][b] == 10){
                                        super.matrix[a][b] = 4; super.buttons[a][b].setText("");  didSomething = true;
                                        super.buttons[a][b].setBackground(Color.BLUE);
                                        if(a+1<super.rows){if(super.matrix[a+1][b] == 0){super.matrix[a+1][b] = 3; super.buttons[a+1][b].setBackground(Color.green);}}
                                        if(a-1>=0){if(super.matrix[a-1][b] == 0){super.matrix[a-1][b] = 3; super.buttons[a-1][b].setBackground(Color.green);}}
                                        if(b+1<super.columns){if(super.matrix[a][b+1] == 0){super.matrix[a][b+1] = 3; super.buttons[a][b+1].setBackground(Color.green);}}
                                        if(b-1>=0){if(super.matrix[a][b-1] == 0){super.matrix[a][b-1] = 3; super.buttons[a][b-1].setBackground(Color.green);}}
                                    }
                                }
                            }
                            if(didSomething) placed = true;
                        }
                    }
                }
            }
            index++;
        }
        super.text.setText("All ships placed");
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
