import java.awt.*;
import javax.swing.*;

public class Boxeo extends RingBoxeo {

    Thread hilo;
    int animando = 0;

    // Posiciones base (donde empiezan separados)
    int b1BaseX = 200;
    int b2BaseX = 760;

    // Distancia a la que se dispara el golpe
    int distanciaGolpe = 80;

    // Fases: 0 = acercandose, 1 = golpeando, 2 = retrocediendo
    int fase = 0;
    int velocidadMovimiento = 2;

    Boxeo() {
        b1X = b1BaseX;
        b2X = b2BaseX;
        b1Y = 340;
        b2Y = 340;
        resetPunios();
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
                        // Acercarse
                        b1X += velocidadMovimiento;
                        b2X -= velocidadMovimiento;
                        resetPunios(); // punios siguen al cuerpo

                        if (b2X - (b1X + b1Ancho) <= distanciaGolpe) {
                            fase = 1;
                            iniciarGolpe();
                        }

                    } else if (fase == 1) {
                        // Animacion del golpe
                        actualizarGolpe();

                        if (!golpeando) {
                            fase = 2;
                        }

                    } else if (fase == 2) {
                        // Retroceder a posicion base
                        if (b1X > b1BaseX) b1X -= velocidadMovimiento;
                        if (b2X < b2BaseX) b2X += velocidadMovimiento;
                        resetPunios(); // punios siguen al cuerpo

                        if (b1X <= b1BaseX && b2X >= b2BaseX) {
                            b1X = b1BaseX;
                            b2X = b2BaseX;
                            resetPunios();
                            fase = 0;
                        }
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
}