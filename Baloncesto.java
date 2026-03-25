import java.awt.*;
import javax.swing.*;

public class Baloncesto extends JPanel {
    JButton volver;
    int campoX = 50;
    int campoY = 150;
    int campoAncho = 900;
    int campoAlto = 500;

    int jugadorIzqX = campoX + 30;
    int jugadorDerX = campoX + campoAncho - 50;
    int jugadorAncho = 20;
    int jugadorAlto = 60;

    int pelotaX, pelotaY;
    int pelotaTam = 18;
    int dirPelotaX = 0;
    int lanzando = 0;

    int aroDerX = campoX + campoAncho - 60;
    int aroY = campoY + campoAlto / 2 - 10;
    int aroAncho = 40;
    int aroAlto = 5;

    int fase = 0;
    int contadorFase = 0;

    int rojoBaseY = campoY + campoAlto / 2 - jugadorAlto / 2;
    int rojoY = rojoBaseY;

    int azulBaseX = jugadorDerX;
    int azulX = azulBaseX;
    int azulBaseY = campoY + campoAlto / 2 - jugadorAlto / 2;
    int azulY = azulBaseY;
    int azulAvance = 0;

    int pelotaInicioX, pelotaInicioY;
    int pelotaDestinoX;
    int mitadRecorrido;
    int dosTerciosRecorrido;
    int yaAvanzado = 0;
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
    double amplitudOla = 12;
    double velocidadOla = 0.15;
    int olaActiva = 0;
    int contadorOla = 0;

    Thread hilo;
    int animando = 0;

    Baloncesto() {
        setOpaque(true);
        setBackground(new Color(100, 50, 20));
        setFocusable(true);
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
            
            cambiarPanel("menuBaloncesto");
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

        pelotaX = jugadorIzqX + jugadorAncho;
        pelotaY = rojoBaseY + jugadorAlto / 2;
    }

    void cambiarPanel(String nombre) {
        JFrame marco = (JFrame) SwingUtilities.getWindowAncestor(this);
        JPanel contenedor = (JPanel) marco.getContentPane().getComponent(0);
        CardLayout layout = (CardLayout) contenedor.getLayout();
        layout.show(contenedor, nombre);
    }

    void iniciar() {
        if (animando == 1) return;
        animando = 1;
        fase = 0;
        contadorFase = 0;
        rojoY = rojoBaseY;
        azulX = azulBaseX;
        azulY = azulBaseY;
        azulAvance = 0;
        yaAvanzado = 0;
        pelotaX = jugadorIzqX + jugadorAncho;
        pelotaY = rojoBaseY + jugadorAlto / 2;
        lanzando = 0;
        olaActiva = 0;
        tiempoOla = 0;
        hilo = new Thread(() -> {
            while (animando == 1) {
                actualizar();
                repaint();
                try { Thread.sleep(20); } catch (Exception e) {}
            }
        });
        hilo.start();
    }

    void detener() {
        animando = 0;
    }

    void actualizar() {
        if (fase == 0) {
            contadorFase++;
            if (contadorFase <= 15) {
                rojoY = rojoBaseY - (contadorFase * 2);
            } else {
                fase = 1;
                contadorFase = 0;
                lanzando = 1;

                pelotaX = jugadorIzqX + jugadorAncho;
                pelotaY = rojoY + jugadorAlto / 2;
                pelotaInicioX = pelotaX;
                pelotaInicioY = pelotaY;
                pelotaDestinoX = aroDerX + aroAncho / 2;
                mitadRecorrido = (pelotaDestinoX - pelotaInicioX) / 2;
                dosTerciosRecorrido = (pelotaDestinoX - pelotaInicioX) * 2 / 3;
                dirPelotaX = 4;
                yaAvanzado = 0;
            }
        } else if (fase == 1) {
            contadorFase++;

            if (rojoY < rojoBaseY) {
                rojoY += 2;
                if (rojoY > rojoBaseY) rojoY = rojoBaseY;
            }

            if (lanzando == 1) {
                pelotaX += dirPelotaX;
                int recorrido = pelotaX - pelotaInicioX;
                if (recorrido < mitadRecorrido) {
                    pelotaY -= 2;
                } else {
                    pelotaY += 2;
                }

                if (yaAvanzado == 0 && recorrido >= dosTerciosRecorrido) {
                    yaAvanzado = 1;
                    azulAvance = 0;
                }
                if (yaAvanzado == 1 && azulAvance < 50) {
                    azulAvance += 2;
                    azulX = azulBaseX - azulAvance;
                    if (azulAvance <= 10) {
                        azulY = azulBaseY - azulAvance;
                    } else if (azulAvance <= 20) {
                        azulY = azulBaseY - (20 - azulAvance);
                    } else {
                        azulY = azulBaseY;
                    }
                }
                int pelotaIzq = pelotaX;
                int pelotaDer = pelotaX + pelotaTam;
                int pelotaSup = pelotaY;
                int pelotaInf = pelotaY + pelotaTam;
                int aroIzq = aroDerX;
                int aroDer = aroDerX + aroAncho;
                int aroSup = aroY;
                int aroInf = aroY + aroAlto;

                if (pelotaDer > aroIzq && pelotaIzq < aroDer && pelotaInf > aroSup && pelotaSup < aroInf) {
                    if (olaActiva == 0) {
                        olaActiva = 1;
                        contadorOla = 0;
                        tiempoOla = 0;
                    }
                    lanzando = 0;
                    fase = 2;
                    contadorFase = 0;
                }
                if (pelotaX > aroDerX + aroAncho + 20) {
                    lanzando = 0;
                    fase = 2;
                    contadorFase = 0;
                }
            }
        } else if (fase == 2) {
            contadorFase++;
            if (contadorFase > 30) {
                fase = 0;
                contadorFase = 0;
                rojoY = rojoBaseY;
                azulX = azulBaseX;
                azulY = azulBaseY;
                azulAvance = 0;
                yaAvanzado = 0;
                pelotaX = jugadorIzqX + jugadorAncho;
                pelotaY = rojoBaseY + jugadorAlto / 2;
                lanzando = 0;
            }
        }
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
                        int desplazamiento = (int)(amplitudOla * Math.sin(tiempoOla + desfase[f][i]));
                        espectadorY[f][i] = espectadorYBase[f][i] + desplazamiento;
                    }
                }
                tiempoOla += velocidadOla;
            }
        }
    }

    void dibujarCancha(Graphics g) {
        g.setColor(new Color(210, 180, 140));
        g.fillRect(campoX, campoY, campoAncho, campoAlto);
        g.setColor(Color.WHITE);
        g.drawRect(campoX, campoY, campoAncho, campoAlto);
        int centroX = campoX + campoAncho / 2;
        g.drawLine(centroX, campoY, centroX, campoY + campoAlto);
        g.drawOval(centroX - 80, campoY + campoAlto / 2 - 80, 160, 160);
        g.drawRect(campoX + 50, campoY + campoAlto - 60, 150, 50);
        g.drawRect(campoX + campoAncho - 200, campoY + campoAlto - 60, 150, 50);
    }

    void dibujarAros(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(aroDerX, aroY, aroAncho, aroAlto);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(aroDerX + aroAncho, aroY - 30, 20, 60);
        g.setColor(Color.ORANGE);
        g.fillRect(campoX + 20, aroY, aroAncho, aroAlto);
        g.fillRect(campoX, aroY - 30, 20, 60);
    }

    void dibujarJugadores(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(jugadorIzqX, rojoY, jugadorAncho, jugadorAlto);
        g.setColor(Color.BLUE);
        g.fillRect(azulX, azulY, jugadorAncho, jugadorAlto);
    }

    void dibujarPelota(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(pelotaX, pelotaY, pelotaTam, pelotaTam);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarCancha(g);
        dibujarAros(g);
        dibujarJugadores(g);
        dibujarPelota(g);
        dibujarGradas(g);
        dibujarPublico(g);
    }
}