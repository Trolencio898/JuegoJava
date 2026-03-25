import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBaloncesto extends JPanel implements ActionListener {
    JButton jugar, reglas, salir;
    Image fondoImage;
    MenuBaloncesto() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        fondoImage = new ImageIcon(getClass().getResource("/Imagenes/fondobasquet.jpg")).getImage();

        JLabel titulo = new JLabel("BALONCESTO", SwingConstants.CENTER);
        titulo.setFont(new Font("Bauhaus 93", Font.BOLD, 60));
        titulo.setForeground(Color.orange);
        gbc.gridy = 0;
        add(titulo, gbc);

        jugar = new JButton("JUGAR");
        jugar.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 30));
        jugar.setBackground(new Color(235, 56, 12));
        jugar.setForeground(Color.WHITE);
        jugar.setFocusPainted(false);
        jugar.addActionListener(this);
        gbc.gridy = 1;
        gbc.fill=1;
        add(jugar, gbc);

        reglas = new JButton("REGLAS");
        reglas.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 30));
        reglas.setBackground(new Color(115, 115, 115));
        reglas.setForeground(Color.WHITE);
        reglas.setFocusPainted(false);
        reglas.addActionListener(this);
        gbc.gridy = 2;
        add(reglas, gbc);

        salir = new JButton("SALIR");
        salir.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 30));
        salir.setBackground(new Color(115, 115, 115));
        salir.setForeground(Color.WHITE);
        salir.setFocusPainted(false);
        salir.addActionListener(this);
        gbc.gridy = 3;
        add(salir, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugar) {
            cambiarPanel("baloncesto");
            iniciarAnimacionBaloncesto();
        } else if (e.getSource() == reglas) {
            cambiarPanel("reglas");
            definirRegreso();
        } else if (e.getSource() == salir) {
            cambiarPanel("deportes");
        }
    }

    void cambiarPanel(String nombre) {
        JFrame marco = (JFrame) SwingUtilities.getWindowAncestor(this);
        JPanel contenedor = (JPanel) marco.getContentPane().getComponent(0);
        CardLayout layout = (CardLayout) contenedor.getLayout();
        layout.show(contenedor, nombre);
    }

    void iniciarAnimacionBaloncesto() {
        JFrame marco = (JFrame) SwingUtilities.getWindowAncestor(this);
        JPanel contenedor = (JPanel) marco.getContentPane().getComponent(0);
        Component[] comps = contenedor.getComponents();
        for (Component c : comps) {
            if (c instanceof Baloncesto) {
                Baloncesto baloncesto = (Baloncesto) c;
                baloncesto.iniciar();
                break;
            }
        }
    }

    void definirRegreso() {
        JFrame marco = (JFrame) SwingUtilities.getWindowAncestor(this);
        JPanel contenedor = (JPanel) marco.getContentPane().getComponent(0);
        Component[] comps = contenedor.getComponents();
        for (Component c : comps) {
            if (c instanceof Reglas) {
                Reglas reglas = (Reglas) c;
                reglas.definirRegreso("menuBaloncesto");
                break;
            }
        }
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(fondoImage,0,0, getWidth(), getHeight(), this);
        Graphics2D g2d = (Graphics2D)g;
        AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
        g2d.setComposite(composite);
        g2d.setColor(new Color(51, 51, 51));
        g2d.fillRoundRect(340, 170, 385, 350,20,20);
    }
    

}