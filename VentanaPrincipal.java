import java.awt.*;
import javax.swing.*;

public class VentanaPrincipal {
    JFrame marco;
    CardLayout layout;
    JPanel contenedor;
    Image fondo;

    VentanaPrincipal() {
        marco = new JFrame("DUPO");
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        marco.setSize(1080, 720);
        marco.setLocation(100, 100);

        fondo = new ImageIcon(getClass().getResource("/Imagenes/fondomainmenu.jpg")).getImage();
        
        layout = new CardLayout();
        contenedor = new JPanel(layout) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (fondo != null) {
                    g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
                }
                Graphics2D g2d = (Graphics2D) g;
                //g2d.setColor(new Color(0, 0, 0, 120));
                //g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contenedor.setOpaque(false);
        marco.add(contenedor);

        // Crear todos los paneles
        MenuDeportes menuDeportes = new MenuDeportes();
        MenuFut menuFut = new MenuFut();
        Futbol futbol = new Futbol();
        MenuTenis menuTenis = new MenuTenis();
        Tenis tenis = new Tenis();
        MenuBaloncesto menuBaloncesto = new MenuBaloncesto();
        Baloncesto baloncesto = new Baloncesto();
        MenuVoleibol menuVoleibol = new MenuVoleibol();
        Voleibol voleibol = new Voleibol();
        MenuBoxeo menuBoxeo = new MenuBoxeo();
        Boxeo boxeo = new Boxeo();
        MenuFutAmericano menuFutAmericano = new MenuFutAmericano();
        FutbolAmericano futAmericano = new FutbolAmericano();
        Reglas reglas = new Reglas();

        // Añadir al contenedor con CardLayout
        contenedor.add(menuDeportes, "deportes");
        contenedor.add(menuFut, "menuFut");
        contenedor.add(futbol, "futbol");
        contenedor.add(menuTenis, "menuTenis");
        contenedor.add(tenis, "tenis");
        contenedor.add(menuBaloncesto, "menuBaloncesto");
        contenedor.add(baloncesto, "baloncesto");
        contenedor.add(menuVoleibol, "menuVoleibol");
        contenedor.add(voleibol, "voleibol");
        contenedor.add(menuBoxeo, "menuBoxeo");
        contenedor.add(boxeo, "boxeo");
        contenedor.add(menuFutAmericano, "menuFutAmericano");
        contenedor.add(futAmericano, "futAmericano");
        contenedor.add(reglas, "reglas");
    }

    void mostrar() {
        layout.show(contenedor, "deportes");
        marco.setVisible(true);
    }

    public static void main(String[] args) {
        new VentanaPrincipal().mostrar();
    }
}