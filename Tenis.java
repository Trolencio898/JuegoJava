import java.awt.*;
import javax.swing.*;

public class Tenis extends JPanel {
    JButton volver;
    int campoX = 50;
    int campoY = 150;
    int campoAncho = 900;
    int campoAlto = 500;

    int pelotaX = 470;
    int pelotaY = 350;
    int pelotaAncho = 15;
    int pelotaAlto = 15;
    int direccionX = 1;
    int direccionY = 0;
    int velocidad = 4;

    int jugadorIzqX = campoX + 20;
    int jugadorIzqY = 300;
    int jugadorDerX = campoX + campoAncho - 50;
    int jugadorDerY = 300;
    int jugadorAncho = 20;
    int jugadorAlto = 30;
    int colorIzqSup = 0xFF4444;
    int colorIzqInf = 0xAA3333;
    int colorDerSup = 0x4444FF;
    int colorDerInf = 0x3333AA;

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
    double velocidadOla = 0.1;
    int celebrando = 0;
    int contadorCelebracion = 0;

    Thread hilo;
    int animando = 0;
    int superficie;
    Color colorCancha;

    Tenis() {
        setOpaque(true);
        setBackground(new Color(204, 102, 45));

        espectadorX = new int[filas][espectadoresPorFila];
        espectadorYBase = new int[filas][espectadoresPorFila];
        espectadorY = new int[filas][espectadoresPorFila];
        desfase = new double[filas][espectadoresPorFila];

        // ===== BOTÓN =====
        volver = new JButton("VOLVER");
        volver.setBounds(20, 20, 120, 40);
        volver.setBackground(Color.RED);
        volver.setForeground(Color.WHITE);

        volver.addActionListener(e -> {
            detener();
            cambiarPanel("menuTenis");
        });

        add(volver);
        volver.setVisible(true);

        int espacioX = campoAncho / (espectadoresPorFila - 1);
        for (int i = 0; i < espectadoresPorFila; i++) {
            int x = campoX + i * espacioX;
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
                desfase[f][i] = (i * 2 * Math.PI / espectadoresPorFila) + (f * Math.PI / filas);
                espectadorY[f][i] = yBase;
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
        if (animando == 1) return;
        superficie = (int)(Math.random() * 4);
        if (superficie == 0) {
            colorCancha = new Color(180, 80, 40);
        } else if (superficie == 1) {
            colorCancha = new Color(128, 128, 128);
        } else if (superficie == 2) {
            colorCancha = new Color(50, 100, 200);
        } else {
            colorCancha = new Color(34, 139, 34);
        }
        animando = 1;
        hilo = new Thread() {
            public void run() {
                while (animando == 1) {
                    moverPelota();
                    if (celebrando == 1) {
                        actualizarCelebracion();
                    }
                    repaint();
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        hilo.start();
    }

    void detener() {
        animando = 0;
    }

    void moverPelota() {
        pelotaX += direccionX * velocidad;
        pelotaY += direccionY * velocidad;

        if (pelotaY <= campoY || pelotaY + pelotaAlto >= campoY + campoAlto) {
            direccionY = -direccionY;
        }

        int golpeIzq = (pelotaX <= jugadorIzqX + jugadorAncho && pelotaX + pelotaAncho >= jugadorIzqX &&
                        pelotaY + pelotaAlto >= jugadorIzqY && pelotaY <= jugadorIzqY + jugadorAlto) ? 1 : 0;
        if (golpeIzq == 1) {
            direccionX = 1;
            direccionY = (int)(Math.random() * 3) - 1;
        }

        int golpeDer = (pelotaX + pelotaAncho >= jugadorDerX && pelotaX <= jugadorDerX + jugadorAncho &&
                        pelotaY + pelotaAlto >= jugadorDerY && pelotaY <= jugadorDerY + jugadorAlto) ? 1 : 0;
        if (golpeDer == 1) {
            direccionX = -1;
            direccionY = (int)(Math.random() * 3) - 1;
        }

        if (pelotaX < campoX) {
            punto(1);
        }
        if (pelotaX + pelotaAncho > campoX + campoAncho) {
            punto(0);
        }

        int medio = campoY + campoAlto / 2;
        int desplazamientoIzq = (int)(Math.sin(System.currentTimeMillis() * 0.005) * 15);
        int desplazamientoDer = -desplazamientoIzq;
        int offsetVertical = (int)((pelotaY - medio) * 0.2);
        if (offsetVertical > 30) offsetVertical = 30;
        if (offsetVertical < -30) offsetVertical = -30;

        jugadorIzqY = medio - jugadorAlto / 2 + desplazamientoIzq + offsetVertical;
        jugadorDerY = medio - jugadorAlto / 2 + desplazamientoDer + offsetVertical;

        jugadorIzqX = campoX + 20 + (int)((pelotaX - campoX) * 0.1);
        if (jugadorIzqX < campoX + 10) jugadorIzqX = campoX + 10;
        if (jugadorIzqX > campoX + 200) jugadorIzqX = campoX + 200;
        jugadorDerX = campoX + campoAncho - 50 - (int)((campoX + campoAncho - pelotaX) * 0.1);
        if (jugadorDerX < campoX + campoAncho - 200) jugadorDerX = campoX + campoAncho - 200;
        if (jugadorDerX > campoX + campoAncho - 20) jugadorDerX = campoX + campoAncho - 20;
    }

    void punto(int izquierda) {
        pelotaX = campoX + campoAncho / 2;
        pelotaY = campoY + campoAlto / 2;
        direccionX = 1;
        direccionY = 0;
        celebrando = 1;
        contadorCelebracion = 0;
    }

    void actualizarCelebracion() {
        contadorCelebracion++;
        if (contadorCelebracion > 120) {
            celebrando = 0;
            for (int f = 0; f < filas; f++) {
                for (int i = 0; i < espectadoresPorFila; i++) {
                    espectadorY[f][i] = espectadorYBase[f][i];
                }
            }
            tiempoOla = 0;
            return;
        }
        for (int f = 0; f < filas; f++) {
            for (int i = 0; i < espectadoresPorFila; i++) {
                int desplazamiento = (int)(amplitud * Math.sin(tiempoOla + desfase[f][i]));
                espectadorY[f][i] = espectadorYBase[f][i] + desplazamiento;
            }
        }
        tiempoOla += velocidadOla;
    }

    void dibujarCancha(Graphics g) {
        g.setColor(colorCancha);
        g.fillRect(campoX, campoY, campoAncho, campoAlto);

        g.setColor(Color.WHITE);
        g.drawRect(campoX, campoY, campoAncho, campoAlto);

        int centroX = campoX + campoAncho / 2;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(centroX, campoY, centroX, campoY + campoAlto);

        int yLineaServicio1 = campoY + campoAlto / 4;
        int yLineaServicio2 = campoY + 3 * campoAlto / 4;
        g2d.drawLine(campoX, yLineaServicio1, centroX, yLineaServicio1);
        g2d.drawLine(campoX, yLineaServicio2, centroX, yLineaServicio2);
        g2d.drawLine(centroX, yLineaServicio1, campoX + campoAncho, yLineaServicio1);
        g2d.drawLine(centroX, yLineaServicio2, campoX + campoAncho, yLineaServicio2);

        int centroY = campoY + campoAlto / 2;
        g2d.drawLine(centroX - 15, centroY, centroX + 15, centroY);
        g2d.drawLine(centroX, centroY - 10, centroX, centroY + 10);

        g2d.setStroke(new BasicStroke(1));
    }

    void dibujarGradas(Graphics g) {
        int separacion = 10;
        int altoGrada = 20;
        int yInicio = campoY - 50;
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
                int y = espectadorY[f][i];
                g.setColor(colorSuperior);
                g.fillRect(x, y, espectadorAncho, espectadorAlto / 2);
                g.setColor(colorInferior);
                g.fillRect(x, y + espectadorAlto / 2, espectadorAncho, espectadorAlto / 2);
            }
        }
    }

    void dibujarJugador(Graphics g, int x, int y, int colorSup, int colorInf) {
        g.setColor(new Color(colorSup));
        g.fillRect(x, y, jugadorAncho, jugadorAlto / 2);
        g.setColor(new Color(colorInf));
        g.fillRect(x, y + jugadorAlto / 2, jugadorAncho, jugadorAlto / 2);
        g.setColor(Color.BLACK);
        g.fillOval(x + jugadorAncho / 4, y - 8, jugadorAncho / 2, jugadorAncho / 2);
    }

    void dibujarPelota(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval(pelotaX, pelotaY, pelotaAncho, pelotaAlto);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarCancha(g);
        dibujarGradas(g);
        dibujarPublico(g);
        dibujarJugador(g, jugadorIzqX, jugadorIzqY, colorIzqSup, colorIzqInf);
        dibujarJugador(g, jugadorDerX, jugadorDerY, colorDerSup, colorDerInf);
        dibujarPelota(g);
    }
}