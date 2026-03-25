import java.awt.*;
import javax.swing.JPanel;

public class CampoAmericano extends JPanel {

    // --- Espectadores (mismo patron que Campo.java) ---
    int filas = 3;
    int espectadoresPorFila = 25;
    int espectadorAncho = 12;
    int espectadorAlto = 18;
    Color colorSuperior = new Color(30, 100, 30);
    Color colorInferior = new Color(200, 200, 50);
    Color colorGrada = new Color(100, 70, 40);

    int[][] espectadorX;
    int[][] espectadorYBase;
    int[][] espectadorY;
    double[][] desfase;

    double tiempoOla = 0;
    double amplitud = 12;
    double velocidad = 0.05;
    int olaActiva = 0;

    // --- Cancha ---
    int canchaX = 50;
    int canchaY = 150;
    int canchaAncho = 980;
    int canchaAlto = 420;

    // --- Jugador (corredor) ---
    int jugadorX = 120;
    int jugadorY = 390;
    int jugadorAncho = 22;
    int jugadorAlto = 36;

    // --- Balon ---
    int balonX = 120;
    int balonY = 383;
    int balonAncho = 14;
    int balonAlto = 10;

    CampoAmericano() {
        espectadorX    = new int[filas][espectadoresPorFila];
        espectadorYBase = new int[filas][espectadoresPorFila];
        espectadorY    = new int[filas][espectadoresPorFila];
        desfase        = new double[filas][espectadoresPorFila];

        setBackground(new Color(66, 107, 25));

        int espacioX = canchaAncho / (espectadoresPorFila - 1);

        for (int i = 0; i < espectadoresPorFila; i++) {
            int x = canchaX + i * espacioX;
            for (int f = 0; f < filas; f++) {
                espectadorX[f][i] = x;
            }
        }

        int separacion = 8;
        int yInicio = canchaY - 20;

        for (int f = 0; f < filas; f++) {
            int yBase = yInicio - f * (espectadorAlto + separacion);
            for (int i = 0; i < espectadoresPorFila; i++) {
                espectadorYBase[f][i] = yBase;
                espectadorY[f][i]     = yBase;
                desfase[f][i] = (i * 2 * Math.PI / espectadoresPorFila) + (f * Math.PI / filas);
            }
        }
    }

    void activarOla() {
        olaActiva = 1;
        tiempoOla = 0;
    }

    void actualizarOla() {
        if (olaActiva == 0) return;
        for (int f = 0; f < filas; f++) {
            for (int i = 0; i < espectadoresPorFila; i++) {
                int d = (int)(amplitud * Math.sin(tiempoOla + desfase[f][i]));
                espectadorY[f][i] = espectadorYBase[f][i] + d;
            }
        }
        tiempoOla += velocidad;
        if (tiempoOla > 4 * Math.PI) {
            olaActiva = 0;
            for (int f = 0; f < filas; f++)
                for (int i = 0; i < espectadoresPorFila; i++)
                    espectadorY[f][i] = espectadorYBase[f][i];
        }
    }

    void dibujarGradas(Graphics g) {
        int separacion = 8;
        int altoGrada = 25;
        int yInicio = canchaY - 20;

        for (int f = 0; f < filas; f++) {
            int y = yInicio - f * (espectadorAlto + separacion) - 5;
            g.setColor(colorGrada);
            g.fillRect(canchaX - 10, y, canchaAncho + 20, altoGrada);
            g.setColor(Color.DARK_GRAY);
            g.drawRect(canchaX - 10, y, canchaAncho + 20, altoGrada);
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

    void dibujarCancha(Graphics g) {
        // Pasto
        g.setColor(new Color(100, 144, 44));
        g.fillRect(canchaX, canchaY, canchaAncho, canchaAlto);

        // Franjas alternas de pasto
        g.setColor(new Color(28, 105, 28));
        int franjaAncho = canchaAncho / 10;
        for (int i = 0; i < 10; i += 2) {
            g.fillRect(canchaX + i * franjaAncho, canchaY, franjaAncho, canchaAlto);
        }

        // Borde exterior
        g.setColor(Color.WHITE);
        g.drawRect(canchaX, canchaY, canchaAncho, canchaAlto);

        // Zonas de anotacion (endzones) a cada lado - 1/10 del ancho
        int endzone = canchaAncho / 10;

        g.setColor(new Color(180, 30, 30));
        g.fillRect(canchaX, canchaY, endzone, canchaAlto);
        g.setColor(new Color(30, 30, 180));
        g.fillRect(canchaX + canchaAncho - endzone, canchaY, endzone, canchaAlto);

        // Lineas de yardas (8 lineas internas)
        g.setColor(Color.WHITE);
        int seccion = (canchaAncho - 2 * endzone) / 8;
        for (int i = 1; i < 8; i++) {
            int lx = canchaX + endzone + i * seccion;
            g.drawLine(lx, canchaY, lx, canchaY + canchaAlto);
        }

        // Linea de medio campo
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g.setColor(Color.WHITE);
        g.drawLine(canchaX + canchaAncho / 2, canchaY, canchaX + canchaAncho / 2, canchaY + canchaAlto);
        g2.setStroke(new BasicStroke(1));

        // Bordes de endzones
        g.setColor(Color.WHITE);
        g.drawRect(canchaX, canchaY, endzone, canchaAlto);
        g.drawRect(canchaX + canchaAncho - endzone, canchaY, endzone, canchaAlto);

        // Postes de gol (H) en cada endzone - simples lineas
        dibujarPoste(g, canchaX + endzone / 2, canchaY);
        dibujarPoste(g, canchaX + canchaAncho - endzone / 2, canchaY);
    }

    void dibujarPoste(Graphics g, int cx, int baseY) {
        g.setColor(Color.YELLOW);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        // Palo vertical
        g2.drawLine(cx, baseY, cx, baseY - 60);
        // Barra horizontal
        g2.drawLine(cx - 20, baseY - 40, cx + 20, baseY - 40);
        g2.setStroke(new BasicStroke(1));
    }

    void dibujarJugador(Graphics g) {
        // Cuerpo
        g.setColor(new Color(180, 30, 30));
        g.fillRect(jugadorX, jugadorY, jugadorAncho, jugadorAlto);
        // Cabeza
        g.setColor(new Color(230, 180, 130));
        g.fillOval(jugadorX + 3, jugadorY - 18, 16, 16);
        // Casco
        g.setColor(new Color(180, 30, 30));
        g.fillArc(jugadorX + 3, jugadorY - 20, 16, 14, 0, 180);
    }

    void dibujarBalon(Graphics g) {
        g.setColor(new Color(139, 90, 43));
        g.fillOval(balonX, balonY, balonAncho, balonAlto);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarCancha(g);
        dibujarJugador(g);
        dibujarBalon(g);
        dibujarGradas(g);
        dibujarPublico(g);
    }
}