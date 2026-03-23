import java.awt.*;
import javax.swing.*;

public class Futbol extends Campo {
    int fase = 0;
    Thread hilo;
    int animando = 0;
    int gol = 0;

    Futbol() {
        jugadorX = 150;
        jugadorY = 388;
        pelotaX = 700;
        pelotaY = 388;
    }

    void iniciar() {
        if (animando == 1) return;
        animando = 1;
        hilo = new Thread() {
            public void run() {
                while (animando == 1) {
                    mover();
                    if (gol == 1) {
                        actualizarOla();
                    }
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
            } else {
                if (gol == 0) {
                    gol = 1;
                    activarOla();
                }
            }
        }
    }
}