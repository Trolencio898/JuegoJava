import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuDeportes extends JPanel implements ActionListener {
    JButton[] botones;
    final String[] opciones = {"Futbol", "Baloncesto", "Tenis", "Voleibol", "Boxeo", "Futbol Americano", "Salir"};

    MenuDeportes() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel titulo = new JLabel("Selecciona un deporte", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);
        gbc.gridy = 0;
        add(titulo, gbc);

        botones = new JButton[opciones.length];
        for (int i = 0; i < opciones.length; i++) {
            JButton btn = new JButton(opciones[i]);
            btn.setFont(new Font("Arial", Font.BOLD, 24));
            btn.setBackground(new Color(70, 130, 200));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.addActionListener(this);
            gbc.gridy = i + 1;
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
        } else if (texto.equals("Tenis")) {
            cambiarPanel("menuTenis");
        } else if (texto.equals("Baloncesto")) {
            cambiarPanel("menuBaloncesto");
        } else if (texto.equals("Voleibol")) {
            cambiarPanel("menuVoleibol");
        } else if (texto.equals("Boxeo")) {
            cambiarPanel("menuBoxeo");
        } else if (texto.equals("Futbol Americano")) {
            cambiarPanel("menuFutAmericano");
        } else if (texto.equals("Salir")) {
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(this, "Deporte '" + texto + "' no implementado aun.");
        }
    }

    void cambiarPanel(String nombre) {
        JFrame marco = (JFrame) SwingUtilities.getWindowAncestor(this);
        JPanel contenedor = (JPanel) marco.getContentPane().getComponent(0);
        CardLayout layout = (CardLayout) contenedor.getLayout();
        layout.show(contenedor, nombre);
    }
}