import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;  
import javax.swing.*;

public class Test {
    public static void main(String[] args){
        int a = 10, b = 10;
        Grid grid = new Grid(a, b, "JO");
        Grid gridB = new Grid(a, b, "LO!");
        grid.setShips();
        gridB.setShips();

        JFrame frame = new JFrame();
        JFrame frameB = new JFrame();

        JFrame textFrame = new JFrame();
        JLabel text = new JLabel("Im saying something");
        text.setHorizontalAlignment(SwingConstants.CENTER);
        textFrame.add(text);
        textFrame.setVisible(true);
        textFrame.setSize(300, 300);
        textFrame.setLocationRelativeTo(null);
        textFrame.setLocation(500, 500);
        //text.setBounds(50, 50, 100, 30);

        JButton[][] buttons = new JButton[a][b];
        JButton[][] buttonsB = new JButton[a][b];
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JButton src = (JButton) event.getSource();
                switch(src.getText()){
                    default:  src.setText("x"); break;
                }
            }
        };
        
        for(int i = 0; i < a; i++){
            for(int j = 0; j < b; j++){
                int x = grid.get(i, j);
                int y = gridB.get(i, j);
                String s = "H";
                if(x == 0) s = "";
                if(x == 1) s = "X"; if(x == 4) s = "■";
                if(x == 2) s = "#"; if(x == 3) s = ""; 
                buttons[i][j] = new JButton(s);
                buttons[i][j].addActionListener(actionListener);
                frame.add(buttons[i][j]);
                if(y == 0) s = ""; if(y == 1) s = "X"; if(y == 4) s = "■"; if(y == 2) s = "#"; if(y == 3) s = ""; 
                buttonsB[i][j] = new JButton(s);
                buttonsB[i][j].addActionListener(actionListener);
                frameB.add(buttonsB[i][j]);
            }
        }

        JTextField textik = new JTextField("Ich habe eine liebe kleine schichte");
        textik.setBounds(50, 510, 200,30);
        frame.add(textik);
        frame.setLayout(new GridLayout(a+1, b));
        frame.setSize(500, 500);  
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLocation(500, 0);

        frameB.setLayout(new GridLayout(a+1, b));
        frameB.setSize(500, 500);  
        frameB.setVisible(true);

        while(true){
            for(int i = 0; i < a; i++){
                for(int j = 0; j < b; j++){
                    String s = buttons[i][j].getText();

                    if(s.equals("x")){
                        textik.setText(i+", "+j);
                        switch(grid.get(i, j)){
                            case 0: grid.set(i, j); buttons[i][j].setText("X"); buttons[i][j].setBackground(Color.red); break;
                            case 1: grid.set(i, j); buttons[i][j].setText("X"); buttons[i][j].setBackground(Color.red); break;
                            case 2: grid.set(i, j); buttons[i][j].setText("#"); buttons[i][j].setBackground(Color.blue); break;
                            case 3: grid.set(i, j); buttons[i][j].setText("X"); buttons[i][j].setBackground(Color.red); break;
                            case 4: grid.set(i, j); buttons[i][j].setText("#"); buttons[i][j].setBackground(Color.blue); break;
                            case 5: grid.set(i, j); buttons[i][j].setText("."); buttons[i][j].setBackground(Color.blue); break;
                        }
                    }
                }
            }
        }  
    }
}