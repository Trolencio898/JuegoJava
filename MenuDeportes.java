import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MenuDeportes extends JPanel implements ActionListener, MouseListener {
    JButton[] botones;
    final String[] opciones = {"Futbol", "Baloncesto", "Tenis", "Voleibol", "Boxeo", "Futbol Americano", "Salir"};
    Color original = new Color(115, 115, 115);
    Color onHover = new Color(235, 56, 12);

    MenuDeportes() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel mainTitle = new JLabel("DUPO",SwingConstants.CENTER);
        mainTitle.setFont(new Font("Bauhaus 93", Font.BOLD, 70));
        mainTitle.setForeground(Color.ORANGE);
        gbc.gridy = 0;
        add(mainTitle,gbc);

        JLabel titulo = new JLabel("Selecciona un deporte", SwingConstants.CENTER);
        titulo.setFont(new Font("Bauhaus 93", Font.BOLD, 30));
        titulo.setForeground(Color.white);
        gbc.gridy = 1;
        add(titulo, gbc);

        

        botones = new JButton[opciones.length];
        for (int i = 0; i < opciones.length; i++) {
            JButton btn = new JButton(opciones[i]);
            btn.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 24));
            btn.setBackground(original);
            btn.setForeground(Color.WHITE);
            btn.setFocusable(false);
            btn.setBorderPainted(false);
           //btn.setFocusPainted(false);
            btn.addActionListener(this);
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e){
                    btn.setBackground(onHover);
                }
                @Override
                public void mouseExited(MouseEvent e){
                    btn.setBackground(original);
                }
            });
            gbc.gridy = i + 2;
            gbc.fill=1;
            add(btn, gbc);
            botones[i] = btn;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton fuente = (JButton) e.getSource();
        String texto = fuente.getText();

        if (texto.equals("Futbol")) {
            cambiarPanel("menuFut");
        } else if (texto.equals("Baloncesto")) {
            cambiarPanel("menuBaloncesto");
        } else if (texto.equals("Tenis")) {
            cambiarPanel("menuTenis");
        } else if (texto.equals("Voleibol")) {
            cambiarPanel("menuVoleibol");
        } else if (texto.equals("Boxeo")) {
            cambiarPanel("menuBoxeo");
        } else if (texto.equals("Futbol Americano")) {
            cambiarPanel("menuFutAmericano");
        } else if (texto.equals("Salir")) {
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(this, "Deporte '" + texto + "' no implementado aún.");
        }
    }

    void cambiarPanel(String nombre) {
        JFrame marco = (JFrame) SwingUtilities.getWindowAncestor(this);
        JPanel contenedor = (JPanel) marco.getContentPane().getComponent(0);
        CardLayout layout = (CardLayout) contenedor.getLayout();
        layout.show(contenedor, nombre);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
        g2d.setComposite(composite);
        g2d.setColor(new Color(51, 51, 51));
        g2d.fillRoundRect(340, 40, 390, 620,20,20);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }
@Override
    public void mousePressed(MouseEvent e) {
        
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        
    }
    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}