import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class gamepanel extends JFrame implements ActionListener{
    private mainMenu panel;
    gamepanel(){
        panel = new mainMenu();
        
        this.add(panel);
        this.setSize(1080, 720);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public void goto_selectionMenu(){
        
        
    }
    @Override
    public void actionPerformed(ActionEvent e){
        
    }
    public static void main(String[] args) {
        
        gamepanel prueba = new gamepanel();
    }
    
}

