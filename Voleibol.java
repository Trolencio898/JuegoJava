import java.awt.*;
import javax.swing.*;

public class Voleibol extends JPanel {

    int suelo = 500;
    int campoY = 150;

    JButton volver;

    // ===== JUGADORES (IA vs IA) =====
    int j1X = 150;
    int j1Y = suelo - 60;
    int j1VelY = 0;
    boolean j1Saltando = false;

    int j2X = 750;
    int j2Y = suelo - 60;
    int j2VelY = 0;
    boolean j2Saltando = false;

    // ===== PELOTA =====
    int pelotaX = 500;
    int pelotaY = 200;
    int dirX = 4;
    int dirY = 3;

    // ===== MARCADOR =====
    int puntos1 = 0;
    int puntos2 = 0;

    // ===== PUBLICO =====
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
    double velocidadOla = 0.15;

    int olaActiva = 0;
    int contadorOla = 0;

    Thread hilo;
    boolean animando = false;

    Voleibol() {
        setLayout(null);
        setBackground(new Color(135, 206, 235));

        // ===== BOTÓN =====
        volver = new JButton("VOLVER");
        volver.setBounds(20, 20, 120, 40);
        volver.setBackground(Color.RED);
        volver.setForeground(Color.WHITE);

        volver.addActionListener(e -> {
            detener();
            cambiarPanel("menuVoleibol");
        });

        add(volver);
        volver.setVisible(true);

        // ===== PUBLICO =====
        espectadorX = new int[filas][espectadoresPorFila];
        espectadorYBase = new int[filas][espectadoresPorFila];
        espectadorY = new int[filas][espectadoresPorFila];
        desfase = new double[filas][espectadoresPorFila];

        int espacioX = 900 / (espectadoresPorFila - 1);

        for (int i = 0; i < espectadoresPorFila; i++) {
            int x = 50 + i * espacioX;
            for (int f = 0; f < filas; f++) {
                espectadorX[f][i] = x;
            }
        }

        int separacion = 10;
        int yInicio = campoY - 50;

        for (int f = 0; f < filas; f++) {
            int yBase = yInicio - f * (espectadorAlto + separacion);
            for (int i = 0; i < espectadoresPorFila; i++) {
                espectadorYBase[f][i] = yBase;
                espectadorY[f][i] = yBase;
                desfase[f][i] = (i * 2 * Math.PI / espectadoresPorFila) + (f * Math.PI / filas);
            }
        }
    }

    void cambiarPanel(String nombre) {
        JFrame marco = (JFrame) SwingUtilities.getWindowAncestor(this);
        JPanel contenedor = (JPanel) marco.getContentPane().getComponent(0);
        CardLayout layout = (CardLayout) contenedor.getLayout();
        layout.show(contenedor, nombre);
    }

    void iniciar() {
        if (animando) return;
        animando = true;

        hilo = new Thread(() -> {
            while (animando) {
                actualizar();
                repaint();
                try { Thread.sleep(16); } catch (Exception e) {}
            }
        });
        hilo.start();
    }

    void detener() {
        animando = false;
    }

    void reiniciar() {
        pelotaX = getWidth()/2;
        pelotaY = 200;
        dirX = (Math.random() > 0.5) ? 4 : -4;
        dirY = 3;

        j1X = 150;
        j2X = 750;
    }

    void actualizar() {

        // ===== GRAVEDAD =====
        j1VelY += 1;
        j1Y += j1VelY;

        if (j1Y >= suelo - 60) {
            j1Y = suelo - 60;
            j1Saltando = false;
        }

        j2VelY += 1;
        j2Y += j2VelY;

        if (j2Y >= suelo - 60) {
            j2Y = suelo - 60;
            j2Saltando = false;
        }

        // ===== PELOTA =====
        pelotaX += dirX;
        pelotaY += dirY;

        if (pelotaY <= 0) dirY *= -1;

        // ===== IA JUGADOR 1 =====
        if (pelotaX < getWidth()/2) {
            if (j1X < pelotaX) j1X += 3;
            else j1X -= 3;

            if (!j1Saltando && pelotaY < j1Y && Math.random() < 0.1) {
                j1VelY = -14;
                j1Saltando = true;
            }
        }

        // ===== IA JUGADOR 2 =====
        if (pelotaX > getWidth()/2) {
            if (j2X < pelotaX) j2X += 3;
            else j2X -= 3;

            if (!j2Saltando && pelotaY < j2Y && Math.random() < 0.1) {
                j2VelY = -14;
                j2Saltando = true;
            }
        }

        // ===== COLISIONES =====
        if (pelotaX > j1X && pelotaX < j1X + 40 &&
            pelotaY > j1Y && pelotaY < j1Y + 60) {

            dirX = 5;
            dirY = -8;
        }

        if (pelotaX > j2X && pelotaX < j2X + 40 &&
            pelotaY > j2Y && pelotaY < j2Y + 60) {

            dirX = -5;
            dirY = -8;
        }

        // ===== RED =====
        int redX = getWidth()/2;
        if (pelotaX > redX - 5 && pelotaX < redX + 5 && pelotaY > 300) {
            dirX *= -1;
        }

        // ===== PUNTOS =====
        if (pelotaY >= suelo) {

            if (pelotaX < getWidth()/2) puntos2++;
            else puntos1++;

            olaActiva = 1;
            contadorOla = 0;
            tiempoOla = 0;

            reiniciar();
        }

        // ===== LIMITES =====
        if (j1X < 0) j1X = 0;
        if (j1X > getWidth()/2 - 50) j1X = getWidth()/2 - 50;

        if (j2X < getWidth()/2 + 10) j2X = getWidth()/2 + 10;
        if (j2X > getWidth() - 50) j2X = getWidth() - 50;

        // ===== OLA =====
        if (olaActiva == 1) {
            contadorOla++;

            if (contadorOla > 120) {
                olaActiva = 0;
                for (int f = 0; f < filas; f++) {
                    for (int i = 0; i < espectadoresPorFila; i++) {
                        espectadorY[f][i] = espectadorYBase[f][i];
                    }
                }
            } else {
                for (int f = 0; f < filas; f++) {
                    for (int i = 0; i < espectadoresPorFila; i++) {
                        int d = (int)(amplitud * Math.sin(tiempoOla + desfase[f][i]));
                        espectadorY[f][i] = espectadorYBase[f][i] + d;
                    }
                }
                tiempoOla += velocidadOla;
            }
        }
    }

    void dibujarGradas(Graphics g) {
        int yInicio = campoY - 50;

        for (int f = 0; f < filas; f++) {
            int y = yInicio - f * (espectadorAlto + 10) - 5;

            g.setColor(colorGrada);
            g.fillRect(40, y, getWidth() - 80, 20);

            g.setColor(Color.DARK_GRAY);
            g.drawRect(40, y, getWidth() - 80, 20);
        }
    }

    void dibujarPublico(Graphics g) {
        for (int f = 0; f < filas; f++) {
            for (int i = 0; i < espectadoresPorFila; i++) {

                int x = espectadorX[f][i];
                int y = espectadorY[f][i];

                g.setColor(colorSuperior);
                g.fillRect(x, y, espectadorAncho, espectadorAlto / 2);

                g.setColor(colorInferior);
                g.fillRect(x, y + espectadorAlto / 2, espectadorAncho, espectadorAlto / 2);
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(135, 206, 235));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(new Color(240, 200, 150));
        g.fillRect(0, suelo, getWidth(), 200);

        g.setColor(Color.WHITE);
        g.fillRect(getWidth()/2 - 3, 250, 6, 250);

        dibujarGradas(g);
        dibujarPublico(g);

        g.setColor(Color.RED);
        g.fillRect(j1X, j1Y, 40, 60);

        g.setColor(Color.BLUE);
        g.fillRect(j2X, j2Y, 40, 60);

        g.setColor(Color.ORANGE);
        g.fillOval(pelotaX, pelotaY, 20, 20);

        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.BLACK);
        g.drawString(puntos1 + " - " + puntos2, getWidth()/2 - 50, 50);
    }
}