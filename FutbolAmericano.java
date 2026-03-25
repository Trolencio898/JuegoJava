import java.awt.*;
import javax.swing.*;

public class FutbolAmericano extends CampoAmericano {
    JButton volver;
    Thread hilo;
    int animando = 0;

    // Fases: 0 = correr hacia endzone, 1 = celebrar, 2 = reiniciar
    int fase = 0;
    int metaX;

    int celebracionFrame = 0;
    int totalCelebracion = 120;
    int saltoCelebracion = 0;

    int esperaFrame = 0;
    int totalEspera = 80;

    String textoTouchdown = "";
    int tiempoTexto = 0;

    FutbolAmericano() {
        jugadorX = canchaX + canchaAncho / 10 + 10;
        jugadorY = canchaY + canchaAlto / 2 - jugadorAlto / 2;
        balonX = jugadorX + jugadorAncho;
        balonY = jugadorY + 10;
        metaX = canchaX + canchaAncho - canchaAncho / 10 - jugadorAncho - 10;

        

        // ===== BOTÓN =====
        volver = new JButton("VOLVER");
        volver.setBounds(20, 20, 120, 40);
        volver.setBackground(Color.RED);
        volver.setForeground(Color.WHITE);

        volver.addActionListener(e -> {
            detener();
            cambiarPanel("menuFutAmericano");
        });

        add(volver);
        volver.setVisible(true);
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

        hilo = new Thread() {
            public void run() {
                while (animando == 1) {

                    if (olaActiva == 1) {
                        actualizarOla();
                    }

                    if (fase == 0) {
                        // Correr hacia la endzone
                        jugadorX += 2;
                        balonX = jugadorX + jugadorAncho;
                        balonY = jugadorY + 10;

                        if (jugadorX >= metaX) {
                            fase = 1;
                            celebracionFrame = 0;
                            textoTouchdown = "TOUCHDOWN!";
                            tiempoTexto = totalCelebracion;
                            activarOla();
                        }

                    } else if (fase == 1) {
                        // Celebracion: jugador salta
                        saltoCelebracion = (int)(Math.sin(celebracionFrame * 0.3) * 18);
                        celebracionFrame++;

                        if (celebracionFrame >= totalCelebracion) {
                            fase = 2;
                            esperaFrame = 0;
                            saltoCelebracion = 0;
                        }

                    } else if (fase == 2) {
                        // Pausa antes de reiniciar
                        esperaFrame++;
                        if (esperaFrame >= totalEspera) {
                            // Reiniciar posicion
                            jugadorX = canchaX + canchaAncho / 10 + 10;
                            jugadorY = canchaY + canchaAlto / 2 - jugadorAlto / 2;
                            balonX = jugadorX + jugadorAncho;
                            balonY = jugadorY + 10;
                            saltoCelebracion = 0;
                            textoTouchdown = "";
                            tiempoTexto = 0;
                            fase = 0;
                        }
                    }

                    if (tiempoTexto > 0) tiempoTexto--;

                    repaint();

                    try {
                        Thread.sleep(5);
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

    @Override
    void dibujarJugador(Graphics g) {
        int yReal = jugadorY - saltoCelebracion;
        // Cuerpo
        g.setColor(new Color(180, 30, 30));
        g.fillRect(jugadorX, yReal, jugadorAncho, jugadorAlto);
        // Cabeza
        g.setColor(new Color(230, 180, 130));
        g.fillOval(jugadorX + 3, yReal - 18, 16, 16);
        // Casco
        g.setColor(new Color(180, 30, 30));
        g.fillArc(jugadorX + 3, yReal - 20, 16, 14, 0, 180);
    }

    @Override
    void dibujarBalon(Graphics g) {
        int yReal = jugadorY - saltoCelebracion + 10;
        g.setColor(new Color(139, 90, 43));
        g.fillOval(balonX, yReal, balonAncho, balonAlto);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Texto touchdown encima de todo
        if (tiempoTexto > 0) {
            g.setColor(new Color(255, 220, 0));
            g.setFont(new Font("Arial", Font.BOLD, 48));
            FontMetrics fm = g.getFontMetrics();
            int tw = fm.stringWidth(textoTouchdown);
            g.drawString(textoTouchdown, (getWidth() - tw) / 2, canchaY + canchaAlto / 2);
        }
    }
}