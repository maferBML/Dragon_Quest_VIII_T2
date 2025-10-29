package vista;

import modelo.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;

public class VentanaBatalla extends JFrame {
    private Image fondo;
    private JTextArea cuadroTexto;
    private JPanel panelHeroes, panelEnemigos, panelAcciones;
    private ArrayList<Heroe> heroes;
    private ArrayList<Enemigo> enemigos;
    private Enemigo miniJefe;
    private CombateGUI combate;
    private ArrayList<JLabel> labelsHeroes = new ArrayList<>();

    public VentanaBatalla() {
        setTitle("⚔️ Batalla en el Reino de Trodain");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        fondo = new ImageIcon(getClass().getResource("/foticos/bosque.jpg")).getImage();
        inicializarPersonajes();

        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(new BorderLayout());
        panelFondo.setBorder(BorderFactory.createLineBorder(Color.WHITE, 6));

        panelHeroes = new JPanel(new GridLayout(1, heroes.size(), 10, 10));
        panelHeroes.setOpaque(false);
        panelHeroes.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        for (Heroe h : heroes) agregarHeroe(h);

        panelEnemigos = new JPanel(new GridLayout(1, enemigos.size(), 15, 15));
        panelEnemigos.setOpaque(false);
        panelEnemigos.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        for (Enemigo e : enemigos) agregarEnemigo(e);

        cuadroTexto = new JTextArea(8, 20);
        cuadroTexto.setEditable(false);
        cuadroTexto.setWrapStyleWord(true);
        cuadroTexto.setLineWrap(true);
        cuadroTexto.setFont(new Font("Monospaced", Font.PLAIN, 14));
        cuadroTexto.setBackground(new Color(20, 20, 50));
        cuadroTexto.setForeground(Color.WHITE);
        cuadroTexto.setText(mensajeJefe());

        panelAcciones = new JPanel();
        panelAcciones.setBackground(new Color(10, 10, 30));
        JButton btnContinuar = crearBoton("Continuar");
        JButton btnSalir = crearBoton("Salir");

        btnContinuar.addActionListener(e -> mostrarBotonesBatalla());
        btnSalir.addActionListener(e -> System.exit(0));

        panelAcciones.add(btnContinuar);
        panelAcciones.add(btnSalir);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(new Color(10, 10, 30));
        panelInferior.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        panelInferior.add(new JScrollPane(cuadroTexto), BorderLayout.CENTER);
        panelInferior.add(panelAcciones, BorderLayout.SOUTH);

        panelFondo.add(panelHeroes, BorderLayout.NORTH);
        panelFondo.add(panelEnemigos, BorderLayout.CENTER);
        panelFondo.add(panelInferior, BorderLayout.SOUTH);
        add(panelFondo);

        setVisible(true);
    }

    private void inicializarPersonajes() {
        Heroe heroe1 = new Heroe("Héroe", 100, 30, 25, 10, 15);
        Heroe heroe2 = new Heroe("Yangus", 120, 20, 27, 12, 12);
        Heroe heroe3 = new Heroe("Jessica", 90, 50, 20, 8, 18);
        Heroe heroe4 = new Heroe("Angelo", 85, 40, 24, 9, 16);

        heroe3.agregarHabilidad(new Habilidad("Fuego", "daño", 25, 10));
        heroe3.agregarHabilidad(new Habilidad("Curar", "curación", 30, 8));
        heroe3.agregarHabilidad(new Habilidad("Veneno", "estado", 0, 6, 2, "Envenenado"));

        heroe4.agregarHabilidad(new Habilidad("Rayo Divino", "daño", 35, 12));
        heroe4.agregarHabilidad(new Habilidad("Curación Menor", "curación", 20, 6));

        heroes = new ArrayList<>(Arrays.asList(heroe1, heroe2, heroe3, heroe4));

        Enemigo[] enemigosArr = {
            new Enemigo("Goblin", 70, 0, 20, 8, 10, "agresivo"),
            new Enemigo("Slime", 60, 0, 15, 5, 8, "agresivo"),
            new Enemigo("Dragón", 110, 20, 30, 15, 14, "defensivo"),
            new Enemigo("Esqueleto", 80, 0, 18, 9, 13, "agresivo")
        };

        Random random = new Random();
        int indiceMiniJefe = random.nextInt(enemigosArr.length);
        Enemigo elegido = enemigosArr[indiceMiniJefe];
        miniJefe = new Enemigo(
            elegido.getNombre(),
            elegido.getVidaHp(),
            elegido.getMagiaMp(),
            elegido.getAtaque(),
            elegido.getDefensa(),
            elegido.getVelocidad(),
            elegido.getTipo(),
            true
        );
        enemigosArr[indiceMiniJefe] = miniJefe;

        enemigos = new ArrayList<>(Arrays.asList(enemigosArr));
    }

    private void agregarHeroe(Heroe h) {
        JLabel lbl = new JLabel(
            "<html><center><b>" + h.getNombre() + "</b><br>HP: " + h.getVidaHp() + "<br>MP: " + h.getMagiaMp() + "</center></html>",
            JLabel.CENTER
        );
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Serif", Font.BOLD, 16));
        lbl.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        lbl.setOpaque(false);
        panelHeroes.add(lbl);
        labelsHeroes.add(lbl);
    }

    private void agregarEnemigo(Enemigo e) {
        JPanel panelE = new JPanel(new BorderLayout());
        panelE.setOpaque(false);

        JLabel lbl = new JLabel("<html><center>" + e.getNombre() + "</center></html>", JLabel.CENTER);
        lbl.setForeground(e.esMiniJefe() ? Color.ORANGE : Color.RED);
        lbl.setFont(new Font("Serif", Font.BOLD, 16));
        lbl.setBorder(BorderFactory.createLineBorder(e.esMiniJefe() ? Color.ORANGE : Color.RED, 2));
        lbl.setOpaque(false);
        panelE.add(lbl, BorderLayout.CENTER);

        JButton btn = crearBoton("Atacar");
        
        btn.addActionListener(ev -> {
            if (combate != null) {
                cuadroTexto.setText(combate.atacarEnemigo(e));
                actualizarLabelsHeroes();
            }
        });
        panelE.add(btn, BorderLayout.SOUTH);

        panelEnemigos.add(panelE);
    }

    private void actualizarLabelsHeroes() {
        for (int i = 0; i < heroes.size(); i++) {
            Heroe h = heroes.get(i);
            JLabel lbl = labelsHeroes.get(i);
            lbl.setText("<html><center><b>" + h.getNombre() + "</b><br>HP: " + h.getVidaHp() + "<br>MP: " + h.getMagiaMp() + "</center></html>");
        }
    }

    private JButton crearBoton(String texto) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Serif", Font.BOLD, 16));
        b.setBackground(new Color(30, 144, 255));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        return b;
    }

    private String mensajeJefe() {
        return "💀 ¡Un " + miniJefe.getNombre().toUpperCase() + " ha aparecido como JEFE!\n" +
               "HP aumentado: " + miniJefe.getVidaHp() + "\n" +
               "Ataque aumentado: " + miniJefe.getAtaque() + "\n" +
               "Defensa aumentada: " + miniJefe.getDefensa() + "\n";
    }

    private void mostrarBotonesBatalla() {
        combate = new CombateGUI(heroes, enemigos);
        cuadroTexto.setText(combate.getLog());

        panelAcciones.removeAll();

        JButton btnAtacar = crearBoton("Atacar");
        JButton btnHabilidad = crearBoton("Habilidad");
        JButton btnSalir = crearBoton("Salir");

        btnAtacar.addActionListener(e -> {
            cuadroTexto.setText(combate.getLog()); // recordatorio
        });

        btnHabilidad.addActionListener(e -> {
            cuadroTexto.setText( combate.usarHabilidadHeroe() );
            actualizarLabelsHeroes();
        });

        btnSalir.addActionListener(e -> System.exit(0));

        panelAcciones.add(btnAtacar);
        panelAcciones.add(btnHabilidad);
        panelAcciones.add(btnSalir);

        panelAcciones.revalidate();
        panelAcciones.repaint();
    }
}
