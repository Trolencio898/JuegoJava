/*import javax.swing.*;
import java.awt.*;
public class Control {
    private JFrame ventana;
    private CardLayout layout;
    private MenuDeportes menuDeportes;
    private MenuFut menuFut;
    private Futbol futbol;
    private Reglas reglas;
    public Control() {
        ventana = new JFrame("DUPO");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(1080, 720);
        ventana.setLocationRelativeTo(null);
        layout = new CardLayout();
        ventana.setLayout(layout);
        menuDeportes = new MenuDeportes(this);
        menuFut = new MenuFut(this);
        futbol = new Futbol(this);
        reglas = new Reglas(this, "menuFut");
        ventana.add(menuDeportes, "deportes");
        ventana.add(menuFut, "menuFut");
        ventana.add(futbol, "futbol");
        ventana.add(reglas, "reglas");
        ventana.setVisible(true);
    }

    public static void iniciar() {
        new Control();
    }

    public void mostrarDeportes() {
        layout.show(ventana.getContentPane(), "deportes");
    }

    public void mostrarMenuFut() {
        layout.show(ventana.getContentPane(), "menuFut");
    }

    public void mostrarFutbol() {
        layout.show(ventana.getContentPane(), "futbol");
    }

    public void mostrarReglas() {
        reglas.definirRegreso("menuFut");
        layout.show(ventana.getContentPane(), "reglas");
    }

    public void salir() {
        System.exit(0);
    }
}*/