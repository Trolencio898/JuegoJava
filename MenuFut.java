import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFut extends JPanel implements ActionListener {
    JButton jugar, reglas, salir;

    MenuFut() {
        setBackground(new Color(34, 139, 34));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel titulo = new JLabel("FÚTBOL", SwingConstants.CENTER);
        titulo.setFont(new Font("Bauhaus 93", Font.BOLD, 60));
        titulo.setForeground(Color.WHITE);
        gbc.gridy = 0;
        add(titulo, gbc);

        jugar = new JButton("JUGAR");
        jugar.setFont(new Font("Arial", Font.BOLD, 30));
        jugar.setBackground(Color.BLUE);
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
        // Buscar el panel con nombre "futbol" dentro del contenedor
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