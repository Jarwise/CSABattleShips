import java.lang.Math;
import java.awt.*;  
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Grid {
int columns, rows;
int [][] matrix; //hmm, any better name?
JFrame frame = new JFrame();
JLabel text;
JButton[][] buttons;
JButton ok = new JButton("ok");
Pair exCo = new Pair(0, 0); // the last used coordinates
public static Boolean actionEnabled = true;

    public Grid(int row, int col, String title){
        this.columns = col;
        this.rows = row;
        this.matrix = new int[row][col];
        this.reset(0);

        ActionListener actionListener = new ActionListener() {
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
        
        ActionListener okAction = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JButton src = (JButton) e.getSource();
                if(src.getText().equals("ok")){src.setText("__");}
            }
        };
        ok.addActionListener(okAction);
        JPanel buttonFrame = new JPanel();
        buttons = new JButton[row][col];
        for(int i = 0; i < row; i++){
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
        info.setLayout(new BoxLayout(info, BoxLayout.PAGE_AXIS));
        frame.add(buttonFrame, BorderLayout.CENTER);
        frame.add(info, BorderLayout.PAGE_END);
        frame.setTitle(title);
        frame.setSize(50*row, 50*(col));
        frame.setVisible(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLocation(500, 0);
    }
    
    public void disableButtons(Boolean b){ Grid.actionEnabled = !b;};

    public void visible(Boolean b){ this.frame.setVisible(b); }

    public void onlyGrid(){ this.ok.setVisible(false); this.text.setVisible(false); this.frame.setSize(50*this.rows, 50*this.columns); }

    public void move(){ this.frame.setLocation(1, 0); }

    public void setScore(int opscore, int myscore){ this.text.setText("                                     SCORE:    Opponent   "+opscore+" : "+myscore+"   Player"); }

    public void turn(){ this.text.setText("IT'S YOUR TURN!"); this.ok.setVisible(false); }

    public void image(){
        System.out.print("\n");
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.columns; j++){
                int x = this.matrix[i][j];
                switch (x) {
                    case 0: System.out.print(black+" ■"+reset); break;  // empty not yet attacked field
                    case 1: System.out.print(" X"); break;  // empty attacked field
                    case 2: System.out.print(red+" ■"+reset); break;   // "hit" attacked ship
                    case 3: System.out.print(black+" ■"+reset); break; // opponents range of the ship (invisible)
                    case 4: System.out.print(black+" ■"+reset); break;   // opponents ships (invisible)
                    default: System.out.print(" O"); break; // debug (the rest)
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
        //this.image();
    }

    public int getCol(){ return(this.columns); }  //getter methods
        
    public int getRow(){ return(this.rows); }

    public int getLastCoordinatesX(){ return(this.exCo.a()); }
    
    public int getLastCoordinatesY(){ return(this.exCo.b()); }

    public Boolean set(int x, int y){  //setter method
        exCo.set(x, y);
        switch(this.matrix[x][y]){
            case 0: this.matrix[x][y] = 1; this.buttons[x][y].setBackground(Color.GRAY); this.buttons[x][y].setText("X"); return(true);
            case 1: return(false);
            case 2: return(false);
            case 3: this.matrix[x][y] = 1; this.buttons[x][y].setBackground(Color.GRAY); this.buttons[x][y].setText("X"); return(true);
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
                    if(fully){this.matrix[x][y] = 5; this.buttons[x][y].setBackground(Color.PINK); this.buttons[x][y].setText("#");// fully destroyed ship
                        for(int j=1; j<6; j++){if(y+j>=this.columns){break;} if(this.matrix[x][y+j]==3||this.matrix[x][y+j]==1){break;} if(this.matrix[x][y+j]==2||this.matrix[x][y+j]==5){this.matrix[x][y+j]=5; this.buttons[x][y+j].setBackground(Color.PINK); this.buttons[x][y+j].setText("#");}}
                        for(int j=1; j<6; j++){if(y-j<0){break;} if(this.matrix[x][y-j]==3||this.matrix[x][y-j]==1){break;} if(this.matrix[x][y-j]==2||this.matrix[x][y-j]==5){this.matrix[x][y-j]=5; this.buttons[x][y-j].setBackground(Color.PINK); this.buttons[x][y-j].setText("#");}} // fully destroyed ship
                    }
                }
                if(v > 0){  //vertical
                    int i = 1; Boolean fully = true; while(fully){
                        if(x+i < this.rows){if(this.matrix[x+i][y] == 4){fully = false; break;} if(this.matrix[x+i][y]==3||this.matrix[x+i][y]==1){break;}} else{break;} i++;
                    }
                    i = 1; while(fully){
                        if(x-i >= 0){if(this.matrix[x-i][y] == 4){fully = false; break;} if(this.matrix[x-i][y]==3||this.matrix[x-i][y]==1){break;}} else{break;} i++;
                    }
                    if(fully){this.matrix[x][y] = 5;  this.buttons[x][y].setBackground(Color.PINK); this.buttons[x][y].setText("#");
                        for(int j=1; j<6; j++){if(x+j>=this.rows){break;} if(this.matrix[x+j][y]==3||this.matrix[x+j][y]==1){break;} if(this.matrix[x+j][y]==2||this.matrix[x+j][y]==5){this.matrix[x+j][y]=5; this.buttons[x+j][y].setBackground(Color.PINK); this.buttons[x+j][y].setText("#");}}
                        for(int j=1; j<6; j++){if(x-j<0){break;} if(this.matrix[x-j][y]==3||this.matrix[x-j][y]==1){break;} if(this.matrix[x-j][y]==2||this.matrix[x-j][y]==5){this.matrix[x-j][y]=5; this.buttons[x-j][y].setBackground(Color.PINK); this.buttons[x-j][y].setText("#");}} // fully destroyed ship
                    }
                }
                return(true);
            default: return(true);
        } 
    }

    public int get(int x, int y){
        return(this.matrix[x][y]);
    }

    public Boolean setPressed(){
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

    public void clean(){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.columns; j++){
                this.buttons[i][j].setText("");
            }
        }
    }

    private void reset(int value){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.columns; j++){
                this.matrix[i][j] = value;
            }
        }
    }

    public static void Image(Grid a, Grid b, int scoreP, int scoreO){
        System.out.print(" Opponent's grid (attack here)   Your grid (cry here)\n\n  ");
        char c = 'A';
        for(int i = 0; i < a.getCol(); i++){    // letter of a column
            System.out.print(" "+c);
            c++;
        }
        System.out.println();
        for(int i = 0; i < a.getRow(); i++){
            if(i < 10) System.out.print(" ");
            System.out.print(i);
            for(int j = 0; j < a.getCol(); j++){
                switch(a.get(i, j)){
                    case 0: System.out.print(" ■"); break;  // empty not yet attacked field
                    case 1: System.out.print(blue+" X"+reset); break;  // empty attacked field
                    case 2: System.out.print(red+" #"+reset); break;  // "hit" attacked ship
                    case 3: System.out.print(" ■"); break;  case 4: System.out.print(" ■"); break;
                    case 5: System.out.print(red+" ."+reset); break; // fully destroyed ship
                    default: System.out.print(" O"); break; // debug (the rest)
                }
            }
            System.out.print("          ");
            for(int j = 0; j < b.getCol(); j++){
                switch(b.get(i, j)){
                    case 0: System.out.print(" ■"); break;  // empty not yet attacked field
                    case 1: System.out.print(red+" X"+reset); break;  // empty attacked field
                    case 2: System.out.print(blue+" #"+reset); break;  // "hit" attacked ship (your)
                    case 3: System.out.print(green+" ■"+reset); break;  // useless 
                    case 4: System.out.print(blue+" ■"+reset); break;  // your ships (unattacked)
                    case 5: System.out.print(blue+" ."+reset); break; // fully destroyed ship (your)
                    default: System.out.print(" O"); break; // debug (the rest)
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("SCORE:            Player  "+blue+scoreP+reset+":"+red+scoreO+reset+"  Bot \n");
    }

    public static final String reset = "\u001B[0m";
    public static final String red = "\u001B[31m";
    public static final String green = "\u001B[32m";
    public static final String yellow = "\u001B[33m";
    public static final String blue = "\u001B[34m";
    public static final String black = "\u001B[30m";
}
