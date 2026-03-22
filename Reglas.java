import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Reglas extends JPanel implements ActionListener {
    JButton volver;
    String regreso = "menuFut";

    Reglas() {
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);

        JTextArea texto = new JTextArea();
        texto.setEditable(false);
        texto.setFont(new Font("Arial", Font.PLAIN, 18));
        texto.setForeground(Color.WHITE);
        texto.setBackground(Color.DARK_GRAY);
        texto.setText("Reglas del juego DUPO:\n\n"
                + "• Selecciona un deporte y luego JUGAR.\n"
                + "• En Fútbol, controla al jugador rojo con las flechas (próximamente).\n"
                + "• Por ahora, el jugador corre y patea la pelota automáticamente.\n\n"
                + "¡Diviértete!");
        add(new JScrollPane(texto), BorderLayout.CENTER);

        volver = new JButton("Volver");
        volver.setFont(new Font("Arial", Font.BOLD, 20));
        volver.addActionListener(this);
        add(volver, BorderLayout.SOUTH);
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