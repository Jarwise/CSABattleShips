import java.lang.Math;
import java.awt.*;  
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Grid {
int columns, rows;
int [][] matrix; //hmm, any better name?
JFrame frame = new JFrame();     // constructing both value grid (matrix) and GUI grid (buttons)
JLabel text;
JButton[][] buttons;
JButton ok = new JButton("ok");
Pair exCo = new Pair(0, 0); // the last used coordinates
public Boolean actionEnabled = true;

    public Grid(int row, int col, String title){
        this.columns = col;
        this.rows = row;
        this.matrix = new int[row][col];
        this.reset(0);

        ActionListener actionListener = new ActionListener() {      //action performed by pressing any of the grid buttons
            public void actionPerformed(ActionEvent event) {
                if(actionEnabled){
                    JButton src = (JButton) event.getSource();
                    switch(src.getText()){
                        case "H": src.setText("v"); break; //for placing ships
                        default:  src.setText("x"); break;
                    }
                }
            }
        };
        
        ActionListener okAction = new ActionListener() {           //action performed by clicking "ok" button
            public void actionPerformed(ActionEvent e){
                JButton src = (JButton) e.getSource();
                if(src.getText().equals("ok")){src.setText("__");}
            }
        };
        ok.addActionListener(okAction);
        JPanel buttonFrame = new JPanel();
        buttons = new JButton[row][col];
        for(int i = 0; i < row; i++){                           // creating grid of buttons
            for(int j = 0; j < col; j++){
                buttons[i][j] = new JButton("");
                buttons[i][j].addActionListener(actionListener);
                buttonFrame.add(buttons[i][j]);
            }
        }
        buttonFrame.setLayout(new GridLayout(row, col));
        text = new JLabel("INFO LINE");
        text.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel info = new JPanel();
        info.add(text);
        info.add(ok);
        info.setLayout(new BoxLayout(info, BoxLayout.PAGE_AXIS));       //setting up GUI grid (frame)
        frame.add(buttonFrame, BorderLayout.CENTER);
        frame.add(info, BorderLayout.PAGE_END);
        frame.setTitle(title);
        frame.setSize(50*row, 50*(col));
        frame.setVisible(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLocation(500, 0);
    }
    
    public void disableButtons(Boolean b){ this.actionEnabled = !b;}; //disable buttons so you cannot click when it's not your turn

    public void visible(Boolean b){ this.frame.setVisible(b); } // makes grid invisible or visible

    public void onlyGrid(){ this.ok.setVisible(false); this.text.setVisible(false); this.frame.setSize(50*this.rows, 50*this.columns); } //deletes button and labels from a frame, only buttons grid is left

    public void move(){ this.frame.setLocation(1, 0); } //moves GUI (grid) into left upper corner of the screen

    public void setScore(int opscore, int myscore){ this.text.setText("                                     SCORE:    Opponent   "+opscore+" : "+myscore+"   Player"); }
     // update score

    public void image(){                // grafical representation of the grid in the console, <not used since the implementation of GUI>
        System.out.print("\n");
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.columns; j++){
                int x = this.matrix[i][j];
                switch (x) {  // DEFINITION OF FIELD STATES
                    case 0: System.out.print(" ■"); break;  // empty not yet attacked field
                    case 1: System.out.print(" X"); break;  // empty attacked field
                    case 2: System.out.print(red+" ■"+reset); break;   // "hit" attacked ship
                    case 3: System.out.print(green+" ■"+reset); break; // range of the ship
                    case 4: System.out.print(blue+" ■"+reset); break;  // SHIP
                    case 5: System.out.print(blue+" ■"+reset); break;  // fully destroyed SHIP
                    case 6: System.out.print(green+" ■"+reset); break; // range around fully destroyed SHIP
                    default: System.out.print(" O"); break; // debug (the rest)
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    public void setShips(){ // bot's generation of ships
        int[] ships = {5, 4, 3, 3, 2, 2};  // change list of the ships here :D
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
        //this.image();
    }

    public int getCol(){ return(this.columns); }  //getter methods for columns and rows
        
    public int getRow(){ return(this.rows); }

    public int getLastCoordinatesX(){ return(this.exCo.a()); } //getter for the last pressed coordinates
    
    public int getLastCoordinatesY(){ return(this.exCo.b()); }

    public Boolean set(int x, int y){  //setter method for clicking on any button - changin state of the field
        exCo.set(x, y);                //returns true if you can click on the field, false if it has already been pressed
        switch(this.matrix[x][y]){
            case 0: this.matrix[x][y] = 1; this.buttons[x][y].setBackground(Color.GRAY); this.buttons[x][y].setText("X"); return(true);
            case 1: return(false);
            case 2: return(false);
            case 3: this.matrix[x][y] = 1; this.buttons[x][y].setBackground(Color.GRAY); this.buttons[x][y].setText("X"); return(true);
            case 5: return(false);
            case 6: this.matrix[x][y] = 1; this.buttons[x][y].setBackground(Color.GRAY); this.buttons[x][y].setText("X"); return(true);
            case 4: this.matrix[x][y] = 2; this.buttons[x][y].setBackground(Color.RED); this.buttons[x][y].setText("■");
                int v = 0;
                if(x-1 >= 0){if(this.matrix[x-1][y] == 3 || this.matrix[x-1][y] == 1){v--;}  if(this.matrix[x-1][y]==4 || this.matrix[x-1][y]==2){v+=10;}}
                if(x+1 < this.rows){if(this.matrix[x+1][y] == 3 || this.matrix[x+1][y] == 1){v--;} if(this.matrix[x+1][y]==4 || this.matrix[x+1][y]==2){v+=10;} }
                if(y-1 >= 0){if(this.matrix[x][y-1] == 3 || this.matrix[x][y-1] == 1){v++;} if(this.matrix[x][y-1]==4 ||this.matrix[x][y-1]==2){v-=10;} }
                if(y+1 < this.columns){if(this.matrix[x][y+1] == 3 || this.matrix[x][y+1] == 1){v++;} if(this.matrix[x][y+1]==4 || this.matrix[x][y+1]==2){v-=10;} }
                if(v < 0){  //horizontal
                    int i = 1; Boolean fully = true; while(fully){
                        if(y+i < this.columns){if(this.matrix[x][y+i] == 4){fully = false; break;} if(this.matrix[x][y+i]==3||this.matrix[x][y+i]==1){break;}} else{break;} i++;
                    }
                    i = 1; while(fully){
                        if(y-i >= 0){if(this.matrix[x][y-i] == 4){fully = false; break;} if(this.matrix[x][y-i]==3||this.matrix[x][y-i]==1){break;}} else{break;} i++;
                    } 
                    if(fully){this.pinky(x,y);
                        for(int j=1; j<6; j++){if(y+j>=this.columns){break;} if(this.matrix[x][y+j]==3||this.matrix[x][y+j]==1){break;} if(this.matrix[x][y+j]==2||this.matrix[x][y+j]==5){this.pinky(x,y+j);}}
                        for(int j=1; j<6; j++){if(y-j<0){break;} if(this.matrix[x][y-j]==3||this.matrix[x][y-j]==1){break;} if(this.matrix[x][y-j]==2||this.matrix[x][y-j]==5){this.pinky(x,y-j);}}
                    }
                }
                if(v > 0){  //vertical
                    int i = 1; Boolean fully = true; while(fully){
                        if(x+i < this.rows){if(this.matrix[x+i][y] == 4){fully = false; break;} if(this.matrix[x+i][y]==3||this.matrix[x+i][y]==1){break;}} else{break;} i++;
                    }
                    i = 1; while(fully){
                        if(x-i >= 0){if(this.matrix[x-i][y] == 4){fully = false; break;} if(this.matrix[x-i][y]==3||this.matrix[x-i][y]==1){break;}} else{break;} i++;
                    }
                    if(fully){this.pinky(x,y);
                        for(int j=1; j<6; j++){if(x+j>=this.rows){break;} if(this.matrix[x+j][y]==3||this.matrix[x+j][y]==1){break;} if(this.matrix[x+j][y]==2||this.matrix[x+j][y]==5){this.pinky(x+j,y);}}
                        for(int j=1; j<6; j++){if(x-j<0){break;} if(this.matrix[x-j][y]==3||this.matrix[x-j][y]==1){break;} if(this.matrix[x-j][y]==2||this.matrix[x-j][y]==5){this.pinky(x-j,y);}}
                    }
                }
                return(true);
            default: return(true);
        } 
    }

    public int get(int x, int y){ return(this.matrix[x][y]); } // getter for value of a singe field

    public Boolean setPressed(){   // pressing a button - calls set method
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.columns; j++){
                if(this.buttons[i][j].getText().equals("x")){
                    this.buttons[i][j].setText("");
                    return(this.set(i, j));
                }
            }
        }
        return(false);
    }

    public void clean(){  // clears all the buttons <not the values>
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.columns; j++){
                this.buttons[i][j].setText("");
            }
        }
    }

    private void reset(int value){  // sets all the values to <value>
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.columns; j++){
                this.matrix[i][j] = value;
            }
        }
    }

    public void pinky(int x, int y){  // fully destroyed ship
        this.matrix[x][y] = 5;        // if ship is fully destroyer, this method is called
        this.buttons[x][y].setBackground(Color.PINK); 
        this.buttons[x][y].setText("#");
        if(x+1 < this.rows){if(this.matrix[x+1][y] == 3){
            this.matrix[x+1][y] = 6;
        }}
        if(x-1 >= 0){if(this.matrix[x-1][y] == 3){
            this.matrix[x-1][y] = 6;
        }}
        if(y+1 < this.columns){if(this.matrix[x][y+1] == 3){
            this.matrix[x][y+1] = 6;
        }}
        if(y-1 >= 0){if(this.matrix[x][y-1] == 3){
            this.matrix[x][y-1] = 6;
        }}
    }

    public static final String reset = "\u001B[0m"; // colors used in the console version of the game <not used anymore>
    public static final String red = "\u001B[31m";
    public static final String green = "\u001B[32m";
    public static final String blue = "\u001B[34m";
    public static final String black = "\u001B[30m";
}
