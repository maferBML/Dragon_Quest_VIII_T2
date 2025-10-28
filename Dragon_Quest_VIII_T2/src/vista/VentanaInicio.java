package vista;

import javax.swing.*;
import java.awt.*;

public class VentanaInicio extends JFrame {

    private Image imagenFondo;
    private JLabel titulo;

    public VentanaInicio() {
    
        setTitle("Reino de Trodain - Dragon Quest RPG");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        imagenFondo = new ImageIcon(getClass().getResource("/foticos/bosque.jpg")).getImage();

        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                // 🖋️ Dibujar borde blanco grueso
                g.setColor(Color.WHITE);
                ((Graphics2D) g).setStroke(new BasicStroke(5));
                g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            }
        };
        panelFondo.setLayout(new BorderLayout());

        // 🌟 Título
        titulo = new JLabel("⚔️ REINO DE TRODAIN ⚔️", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        panelFondo.add(titulo, BorderLayout.NORTH);

        add(panelFondo);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaInicio().setVisible(true);
        });
    }
}
