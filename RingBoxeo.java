import java.awt.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class RingBoxeo extends JPanel {
    JButton volver;
    // --- Espectadores ---
    int filas = 3;
    int espectadoresPorFila = 25;
    int espectadorAncho = 12;
    int espectadorAlto = 18;
    Color colorSuperior = new Color(220, 50, 50);
    Color colorInferior = new Color(30, 30, 120);
    Color colorGrada = new Color(100, 70, 40);

    int[][] espectadorX;
    int[][] espectadorYBase;
    int[][] espectadorY;
    double[][] desfase;

    double tiempoOla = 0;
    double amplitud = 12;
    double velocidad = 0.05;
    int olaActiva = 0;

    // --- Ring ---
    int ringX = 90;
    int ringY = 155;
    int ringAncho = 900;
    int ringAlto = 400;

    // --- Boxeadores ---
    int b1X = 200;
    int b1Y = 340;
    int b1Ancho = 40;
    int b1Alto = 80;

    int b2X = 760;
    int b2Y = 340;
    int b2Ancho = 40;
    int b2Alto = 80;

    // Punios
    int punio1X, punio1Y;
    int punio2X, punio2Y;
    int punioAncho = 20;
    int punioAlto = 14;

    // Control de golpe
    int golpes = 0;
    boolean golpeando = false;
    int frameGolpe = 0;
    int totalFramesGolpe = 10;

    // Texto celebracion
    String textoCelebracion = "";
    int tiempoCelebracion = 0;

    RingBoxeo() {
        setBackground(new Color(42, 25, 94));
        espectadorX    = new int[filas][espectadoresPorFila];
        espectadorYBase = new int[filas][espectadoresPorFila];
        espectadorY    = new int[filas][espectadoresPorFila];
        desfase        = new double[filas][espectadoresPorFila];

        // ===== BOTÓN =====
        volver = new JButton("VOLVER");
        volver.setBounds(20, 20, 120, 40);
        volver.setBackground(Color.RED);
        volver.setForeground(Color.WHITE);

        volver.addActionListener(e -> {
            
            
            cambiarPanel("menuBoxeo");
        });

        add(volver);
        volver.setVisible(true);

        int espacioX = ringAncho / (espectadoresPorFila - 1);

        for (int i = 0; i < espectadoresPorFila; i++) {
            int x = ringX + i * espacioX;
            for (int f = 0; f < filas; f++) {
                espectadorX[f][i] = x;
            }
        }

        int separacion = 8;
        int yInicio = ringY - 20;

        for (int f = 0; f < filas; f++) {
            int yBase = yInicio - f * (espectadorAlto + separacion);
            for (int i = 0; i < espectadoresPorFila; i++) {
                espectadorYBase[f][i] = yBase;
                espectadorY[f][i]     = yBase;
                desfase[f][i] = (i * 2 * Math.PI / espectadoresPorFila) + (f * Math.PI / filas);
            }
        }

        resetPunios();
    }

    void cambiarPanel(String nombre) {
        JFrame marco = (JFrame) SwingUtilities.getWindowAncestor(this);
        JPanel contenedor = (JPanel) marco.getContentPane().getComponent(0);
        CardLayout layout = (CardLayout) contenedor.getLayout();
        layout.show(contenedor, nombre);
    }

    

    void resetPunios() {
        punio1X = b1X + b1Ancho;
        punio1Y = b1Y + 22;
        punio2X = b2X - punioAncho;
        punio2Y = b2Y + 22;
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

    // Golpe visual: punio se extiende y regresa con seno
    void actualizarGolpe() {
        if (!golpeando) return;

        float progreso = (float) frameGolpe / totalFramesGolpe;
        float extension = (float) Math.sin(progreso * Math.PI);
        int alcance = 28;

        // Siempre relativo a b1X/b2X actuales para que no se queden atras
        punio1X = (int)(b1X + b1Ancho + extension * alcance);
        punio1Y = b1Y + 22;
        punio2X = (int)(b2X - punioAncho - extension * alcance);
        punio2Y = b2Y + 22;

        frameGolpe++;

        if (frameGolpe >= totalFramesGolpe) {
            golpeando = false;
            frameGolpe = 0;
            golpes++;
            resetPunios();
            if (golpes % 4 == 0) {
                activarOla();
            }
        }
    }

    void iniciarGolpe() {
        if (!golpeando) {
            golpeando = true;
            frameGolpe = 0;
        }
    }

    void dibujarGradas(Graphics g) {
        int separacion = 8;
        int altoGrada = 25;
        int yInicio = ringY - 20;

        for (int f = 0; f < filas; f++) {
            int y = yInicio - f * (espectadorAlto + separacion) - 5;
            g.setColor(colorGrada);
            g.fillRect(ringX - 10, y, ringAncho + 20, altoGrada);
            g.setColor(Color.DARK_GRAY);
            g.drawRect(ringX - 10, y, ringAncho + 20, altoGrada);
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

    void dibujarRing(Graphics g) {
        // Lona
        g.setColor(new Color(210, 190, 150));
        g.fillRect(ringX, ringY, ringAncho, ringAlto);

        // Borde plataforma
        g.setColor(new Color(60, 60, 60));
        g.fillRect(ringX - 20, ringY - 10, ringAncho + 40, 18);
        g.fillRect(ringX - 20, ringY + ringAlto - 8, ringAncho + 40, 18);
        g.fillRect(ringX - 20, ringY - 10, 18, ringAlto + 20);
        g.fillRect(ringX + ringAncho + 2, ringY - 10, 18, ringAlto + 20);

        // Cuerdas
        Graphics2D g2 = (Graphics2D) g;
        int[] yCuerdas = {ringY + 55, ringY + 120, ringY + 185};
        Color[] coloresCuerdas = {Color.RED, Color.WHITE, Color.RED};
        for (int i = 0; i < 3; i++) {
            g.setColor(coloresCuerdas[i]);
            g2.setStroke(new BasicStroke(5));
            g2.drawLine(ringX, yCuerdas[i], ringX + ringAncho, yCuerdas[i]);
        }
        g2.setStroke(new BasicStroke(1));

        // Postes esquinas: 1/4 del alto del ring
        int altoPoste = ringAlto / 4;
        int anchoPoste = 12;
        int[][] postes = {
            {ringX - 10, ringY - 10},
            {ringX + ringAncho - 2, ringY - 10},
            {ringX - 10, ringY + ringAlto - altoPoste + 8},
            {ringX + ringAncho - 2, ringY + ringAlto - altoPoste + 8}
        };
        Color[] coloresPostes = {Color.RED, Color.BLUE, Color.RED, Color.BLUE};
        for (int i = 0; i < 4; i++) {
            g.setColor(coloresPostes[i]);
            g.fillRect(postes[i][0], postes[i][1], anchoPoste, altoPoste);
        }

        // Linea central punteada
        g.setColor(new Color(180, 160, 120));
        g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{8}, 0));
        g2.drawLine(ringX + ringAncho / 2, ringY, ringX + ringAncho / 2, ringY + ringAlto);
        g2.setStroke(new BasicStroke(1));
    }

    void dibujarBoxeadores(Graphics g) {
        // Boxeador 1 - rojo
        g.setColor(new Color(200, 30, 30));
        g.fillRect(b1X, b1Y, b1Ancho, b1Alto);
        g.setColor(new Color(230, 180, 130));
        g.fillOval(b1X + 5, b1Y - 25, 30, 30);
        g.setColor(new Color(200, 30, 30));
        g.fillRect(punio1X, punio1Y, punioAncho, punioAlto);

        // Boxeador 2 - azul
        g.setColor(new Color(30, 60, 200));
        g.fillRect(b2X, b2Y, b2Ancho, b2Alto);
        g.setColor(new Color(230, 180, 130));
        g.fillOval(b2X + 5, b2Y - 25, 30, 30);
        g.setColor(new Color(30, 60, 200));
        g.fillRect(punio2X, punio2Y, punioAncho, punioAlto);
    }

    void dibujarCelebracion(Graphics g) {
        if (tiempoCelebracion <= 0) return;
        g.setColor(new Color(255, 220, 0));
        g.setFont(new Font("Arial", Font.BOLD, 34));
        FontMetrics fm = g.getFontMetrics();
        int tw = fm.stringWidth(textoCelebracion);
        g.drawString(textoCelebracion, (getWidth() - tw) / 2, ringY + ringAlto / 2);
        tiempoCelebracion--;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarGradas(g);
        dibujarPublico(g);
        dibujarRing(g);
        dibujarBoxeadores(g);
        dibujarCelebracion(g);
    }
}