import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Reglas extends JPanel implements ActionListener {
    JButton volver;
    String regreso = "menuFut";
    

    Reglas() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        setBackground(new Color(115, 115, 115));

        JTextArea texto = new JTextArea();
        texto.setEditable(false);
        texto.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 18));
        texto.setForeground(Color.WHITE);
        texto.setBackground(new Color(115,115,115));
        texto.setMargin(new Insets(6, 6, 6, 6));
        texto.setText("Reglas del juego DUPO:\n\n"
                + "• Selecciona un deporte y luego JUGAR.\n"
                + "• En Fútbol, controla al jugador rojo con las flechas (próximamente).\n"
                + "• Por ahora, el jugador corre y patea la pelota automáticamente.\n\n"
                + "¡Diviértete!");
        gbc.gridx=2;
        gbc.gridy=2;
        add(new JScrollPane(texto), gbc);

        volver = new JButton("Volver");
        volver.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 20));
        volver.setBackground(new Color(235, 56, 12));
        volver.setForeground(Color.white);
        volver.addActionListener(this);
        gbc.gridx=2;
        gbc.gridy=3;
        add(volver, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cambiarPanel(regreso);
    }

    void cambiarPanel(String nombre) {
        JFrame marco = (JFrame) SwingUtilities.getWindowAncestor(this);
        JPanel contenedor = (JPanel) marco.getContentPane().getComponent(0);
        CardLayout layout = (CardLayout) contenedor.getLayout();
        layout.show(contenedor, nombre);
    }

    void definirRegreso(String destino) {
        regreso = destino;
    }
}