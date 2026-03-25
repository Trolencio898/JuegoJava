import java.awt.*;
import javax.swing.JPanel;

public class Campo extends JPanel {

    int filas = 3;
    int espectadoresPorFila = 25;
    int espectadorAncho = 12;
    int espectadorAlto = 18;
    Color colorSuperior = Color.YELLOW;
    Color colorInferior = Color.BLUE;
    Color colorGrada = new Color(150, 100, 50);

    int[][] espectadorX;
    int[][] espectadorYBase;
    int[][] espectadorY;
    double[][] desfase;
    double tiempoOla = 0;
    double amplitud = 12;
    double velocidad = 0.05;
    int olaActiva = 0;

    int campoX = 50;
    int campoY = 150;
    int campoAncho = 900;
    int campoAlto = 500;

    int jugadorX = 150;
    int jugadorY = 388;
    int jugadorAncho = 25;
    int jugadorAlto = 25;

    int pelotaX = 700;
    int pelotaY = 388;
    int pelotaAncho = 25;
    int pelotaAlto = 25;

    Campo() {
        espectadorX = new int[filas][espectadoresPorFila];
        espectadorYBase = new int[filas][espectadoresPorFila];
        espectadorY = new int[filas][espectadoresPorFila];
        desfase = new double[filas][espectadoresPorFila];

        int espacioX = campoAncho / (espectadoresPorFila - 1);
        for (int i = 0; i < espectadoresPorFila; i++) {
            int x = campoX + i * espacioX;
            for (int f = 0; f < filas; f++) {
                espectadorX[f][i] = x;
            }
        }

        int separacion = 8;
        int altoGrada = 25;
        int yInicio = campoY - 20;
        for (int f = 0; f < filas; f++) {
            int yBase = yInicio - f * (espectadorAlto + separacion);
            for (int i = 0; i < espectadoresPorFila; i++) {
                espectadorYBase[f][i] = yBase;
                desfase[f][i] = (i * 2 * Math.PI / espectadoresPorFila) + (f * Math.PI / filas);
            }
        }
    }

    void activarOla() {
        olaActiva = 1;
    }

    public void desactivarOla(){
        olaActiva = 0;
    }
    void actualizarOla() {
        if (olaActiva == 0) return;
        for (int f = 0; f < filas; f++) {
            for (int i = 0; i < espectadoresPorFila; i++) {
                int desplazamiento = (int)(amplitud * Math.sin(tiempoOla + desfase[f][i]));
                espectadorY[f][i] = espectadorYBase[f][i] + desplazamiento;
            }
        }
        tiempoOla += velocidad;
    }

    void dibujarGradas(Graphics g) {
        int separacion = 8;
        int altoGrada = 25;
        int yInicio = campoY - 20;
        for (int f = 0; f < filas; f++) {
            int y = yInicio - f * (espectadorAlto + separacion) - 5;
            g.setColor(colorGrada);
            g.fillRect(campoX - 10, y, campoAncho + 20, altoGrada);
            g.setColor(Color.DARK_GRAY);
            g.drawRect(campoX - 10, y, campoAncho + 20, altoGrada);
        }
    }

    void dibujarPublico(Graphics g) {
        for (int f = 0; f < filas; f++) {
            for (int i = 0; i < espectadoresPorFila; i++) {
                int x = espectadorX[f][i];
                int y = (olaActiva == 1 ? espectadorY[f][i] : espectadorYBase[f][i]);
                g.setColor(colorSuperior);
                g.fillRect(x, y, espectadorAncho, espectadorAlto / 2);
                g.setColor(colorInferior);
                g.fillRect(x, y + espectadorAlto / 2, espectadorAncho, espectadorAlto / 2);
            }
        }
    }

    void dibujarCampo(Graphics g) {
        this.setBackground(new Color(33, 115, 33));
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
        dibujarGradas(g);
        dibujarPublico(g);
    }
}