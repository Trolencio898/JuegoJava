import java.awt.*;
import javax.swing.JPanel;

public class Campo extends JPanel {
    // Coordenadas de la cancha
    int campoX = 50;
    int campoY = 150;
    int campoAncho = 900;
    int campoAlto = 500;

    // Jugador (cuadrado rojo)
    int jugadorX = 150;
    int jugadorY = 388;
    int jugadorAncho = 25;
    int jugadorAlto = 25;

    // Pelota (círculo naranja)
    int pelotaX = 700;
    int pelotaY = 388;
    int pelotaAncho = 25;
    int pelotaAlto = 25;

    boolean corriendo = true; // para detener el hilo si es necesario

    void dibujarCampo(Graphics g) {
        g.setColor(new Color(34, 139, 34));
        g.fillRect(campoX, campoY, campoAncho, campoAlto);
        g.setColor(Color.WHITE);
        g.drawRect(campoX, campoY, campoAncho, campoAlto);
        g.drawLine(campoX + campoAncho/2, campoY, campoX + campoAncho/2, campoY + campoAlto);
        g.drawOval(campoX + campoAncho/2 - 50, campoY + campoAlto/2 - 50, 100, 100);
        g.drawRect(campoX, campoY + 100, 150, 300);
        g.drawRect(campoX + campoAncho - 150, campoY + 100, 150, 300);
        g.drawRect(campoX, campoY + 175, 50, 150);
        g.drawRect(campoX + campoAncho - 50, campoY + 175, 50, 150);
        g.fillOval(campoX + 90, campoY + 245, 10, 10);
        g.fillOval(campoX + campoAncho - 100, campoY + 245, 10, 10);
        g.fillOval(campoX + campoAncho/2 - 5, campoY + campoAlto/2 - 5, 10, 10);
    }

    void dibujarJugador(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(jugadorX, jugadorY, jugadorAncho, jugadorAlto);
    }

    void dibujarPelota(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval(pelotaX, pelotaY, pelotaAncho, pelotaAlto);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarCampo(g);
        dibujarJugador(g);
        dibujarPelota(g);
    }
}