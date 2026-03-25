import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFutAmericano extends JPanel implements ActionListener {

    JButton jugar, reglas, salir;

    MenuFutAmericano() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel titulo = new JLabel("FUTBOL AMERICANO", SwingConstants.CENTER);
        titulo.setFont(new Font("Bauhaus 93", Font.BOLD, 50));
        titulo.setForeground(Color.WHITE);
        gbc.gridy = 0;
        add(titulo, gbc);

        jugar = new JButton("JUGAR");
        jugar.setFont(new Font("Arial", Font.BOLD, 30));
        jugar.setBackground(new Color(30, 100, 30));
        jugar.setForeground(Color.WHITE);
        jugar.setFocusPainted(false);
        jugar.addActionListener(this);
        gbc.gridy = 1;
        add(jugar, gbc);

        reglas = new JButton("REGLAS");
        reglas.setFont(new Font("Arial", Font.BOLD, 30));
        reglas.setBackground(Color.DARK_GRAY);
        reglas.setForeground(Color.WHITE);
        reglas.setFocusPainted(false);
        reglas.addActionListener(this);
        gbc.gridy = 2;
        add(reglas, gbc);

        salir = new JButton("SALIR");
        salir.setFont(new Font("Arial", Font.BOLD, 30));
        salir.setBackground(Color.RED);
        salir.setForeground(Color.WHITE);
        salir.setFocusPainted(false);
        salir.addActionListener(this);
        gbc.gridy = 3;
        add(salir, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugar) {
            cambiarPanel("futAmericano");
            iniciarAnimacion();
        } else if (e.getSource() == reglas) {
            definirRegreso();
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

    void iniciarAnimacion() {
        JFrame marco = (JFrame) SwingUtilities.getWindowAncestor(this);
        JPanel contenedor = (JPanel) marco.getContentPane().getComponent(0);
        Component[] comps = contenedor.getComponents();
        for (Component c : comps) {
            if (c instanceof FutbolAmericano) {
                ((FutbolAmericano) c).iniciar();
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
                ((Reglas) c).definirRegreso("menuFutAmericano");
                break;
            }
        }
    }
}