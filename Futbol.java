import java.awt.*;
import javax.swing.*;

public class Futbol extends Campo {
    int fase = 0; // 0 = mover jugador, 1 = mover pelota
    Thread hilo;
    boolean animando = false;

    Futbol() {
        // Se colocan las posiciones iniciales correctas
        jugadorX = 150;
        jugadorY = 388;
        pelotaX = 700;
        pelotaY = 388;
    }

    void iniciar() {
        if (animando) return;
        animando = true;
        hilo = new Thread() {
            public void run() {
                while (animando) {
                    mover();
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
        animando = false;
        // Opcional: esperar a que el hilo termine, pero no necesario.
    }

    void mover() {
        if (fase == 0) {
            if (jugadorX < 679) {
                jugadorX = jugadorX + 1;
            } else {
                fase = 1;
            }
        } else if (fase == 1) {
            if (pelotaX < 900) {
                pelotaX = pelotaX + 1;
            }
        }
    }
}