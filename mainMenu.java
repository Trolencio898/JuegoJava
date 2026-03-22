import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

public class mainMenu extends JPanel implements ActionListener{
    Image fondo;
    private JButton btnPlay = new JButton("JUGAR");
    private JButton btnExit = new JButton("SALIR");
    private JButton btnRules = new JButton("REGLAS");
    private JLabel mainTitle = new JLabel("DUPO");
    mainMenu(){
        this.setLayout(null);
        fondo = new ImageIcon(getClass().getResource("/Imagenes/cancha2.jpg")).getImage();
        //BOTON JUGAR
        btnPlay.setBackground(new Color(0,204,92));
        btnPlay.setForeground(Color.white);
        btnPlay.setFont(new Font("Arial",Font.BOLD,30));
        btnPlay.setBorderPainted(false);
        btnPlay.setFocusPainted(false);
        btnPlay.addActionListener(this);
        this.add(btnPlay).setBounds(100,300,180,50);
        //BOTON REGLAS
        btnRules.setBackground(new Color(68,86,71));
        btnRules.setForeground(Color.white);
        btnRules.setFont(new Font("Arial",Font.BOLD,30));
        btnRules.setBorderPainted(false);
        btnRules.setFocusPainted(false);
        btnRules.addActionListener(this);
        this.add(btnRules).setBounds(100,400,180,50);
        //BOTON SALIR
        btnExit.setBackground(new Color(68,86,71));
        btnExit.setForeground(Color.white);
        btnExit.setFont(new Font("Arial",Font.BOLD,30));
        btnExit.setBorderPainted(false);
        btnExit.setFocusPainted(false);
        btnExit.addActionListener(this);
        this.add(btnExit).setBounds(100,500,180,50);
        //TITULO DEL JUEGO
        mainTitle.setFont(new Font("Bauhaus 93", Font.BOLD, 60));
        mainTitle.setForeground(new Color(0,190,80));
        this.add(mainTitle).setBounds(110, 50, 180, 100);;
    }
    @Override
    public void paintComponent (Graphics g){
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        Graphics2D g2d = (Graphics2D) g;
        AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
        g2d.setComposite(composite);
        g2d.setColor(new Color(31,53,35));
        g2d.fillRect(80, 0, 220, 720);
        
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==btnExit){
            System.exit(0);
        } else if (e.getSource()==btnPlay) {
            
        }
    }
}
 
