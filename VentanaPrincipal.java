import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal {
    JFrame marco;
    CardLayout layout;
    JPanel contenedor;

    VentanaPrincipal() {
        marco = new JFrame("DUPO");
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        marco.setSize(1080, 720);
        marco.setLocation(100, 100);

        layout = new CardLayout();
        contenedor = new JPanel(layout);
        marco.add(contenedor);

        MenuDeportes menuDeportes = new MenuDeportes();
        MenuFut menuFut = new MenuFut();
        Futbol futbol = new Futbol();
        Reglas reglas = new Reglas();

        contenedor.add(menuDeportes, "deportes");
        contenedor.add(menuFut, "menuFut");
        contenedor.add(futbol, "futbol");
        contenedor.add(reglas, "reglas");
    }

    void mostrar() {
        layout.show(contenedor, "deportes");
        marco.setVisible(true);
    }
}