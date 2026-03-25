import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFut extends JPanel implements ActionListener {
    JButton jugar, reglas, salir;
    Image fondo;

    MenuFut() {
        fondo = new ImageIcon("./imagenes/cancha2.jpg").getImage();

        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel titulo = new JLabel("FÚTBOL", SwingConstants.CENTER);
        titulo.setFont(new Font("Bauhaus 93", Font.BOLD, 60));
        titulo.setForeground(Color.ORANGE);
        gbc.gridy = 0;
        add(titulo, gbc);

        jugar = new JButton("JUGAR");
        jugar.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 30));
        jugar.setBackground(new Color(235, 56, 12));
        jugar.setForeground(Color.WHITE);
        jugar.setFocusPainted(false);
        jugar.setMargin(new Insets(13, 5, 5, 5));
        jugar.addActionListener(this);
        gbc.gridy = 1;
        gbc.fill=1;
        add(jugar, gbc);

        reglas = new JButton("REGLAS");
        reglas.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 30));
        reglas.setBackground(new Color(115, 115, 115));
        reglas.setForeground(Color.WHITE);
        reglas.setFocusPainted(false);
        reglas.setMargin(new Insets(13, 5, 5, 5));
        reglas.addActionListener(this);
        gbc.gridy = 2;
        
        add(reglas, gbc);

        salir = new JButton("SALIR");
        salir.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 30));
        salir.setBackground(new Color(115, 115, 115));
        salir.setForeground(Color.WHITE);
        salir.setFocusPainted(false);
        salir.setMargin(new Insets(13, 5, 5, 5));
        salir.addActionListener(this);
        gbc.gridy = 3;
        add(salir, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
        g2d.setComposite(composite);
        g2d.setColor(new Color(51, 51, 51));
        g2d.fillRoundRect(400, 150, 270, 370,20,20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugar) {
            cambiarPanel("futbol");
            iniciarAnimacionFutbol();
        } else if (e.getSource() == reglas) {
            cambiarPanel("reglas");
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

    void iniciarAnimacionFutbol() {
        JFrame marco = (JFrame) SwingUtilities.getWindowAncestor(this);
        JPanel contenedor = (JPanel) marco.getContentPane().getComponent(0);
        Component[] comps = contenedor.getComponents();
        for (Component c : comps) {
            if (c instanceof Futbol) {
                Futbol futbol = (Futbol) c;
                futbol.iniciar();
                break;
            }
        }
    }
}