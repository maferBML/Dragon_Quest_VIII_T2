import java.util.ArrayList;
import java.util.Scanner;

public class Heroe extends Personaje {

    private ArrayList<Habilidad> habilidades = new ArrayList<>();

    public Heroe(String nombre, int vidaHp, int magiaMp, int ataque, int defensa, int velocidad) {
        super(nombre, vidaHp, magiaMp, ataque, defensa, velocidad);
    }

    public void agregarHabilidad(Habilidad h) {
        habilidades.add(h);
    }

    @Override
    public void atacar(Personaje enemigo) {
        if (getEstado() != null && getEstado().getNombre().equals("Paralizado")) {
            System.out.println(getNombre() + " está paralizado y no puede atacar.");
            return;
        }

        int danio = this.getAtaque() - enemigo.getDefensa();
        if (danio < 0) danio = 0;

        enemigo.setVidaHp(enemigo.getVidaHp() - danio);
        if (enemigo.getVidaHp() <= 0) {
            enemigo.setVive(false);
            enemigo.setVidaHp(0);
            System.out.println(enemigo.getNombre() + " ha sido derrotado.");
        } else {
            System.out.println(this.getNombre() + " ataca a " + enemigo.getNombre() + " causando " + danio + " puntos de daño.");
        }
    }

    public void defender() {
        System.out.println(this.getNombre() + " se anda preparando para defenderse del próximo ataque. Defensa aumentada temporalmente.");
        setDefensa(getDefensa() + 5);
    }

    public void curar(int cantidad) {
        this.setVidaHp(this.getVidaHp() + cantidad);
        System.out.println(this.getNombre() + " se cura " + cantidad + " puntos de vida. Vida actual: " + this.getVidaHp());
    }

    // versión mejorada para elegir a quién curar o atacar
    public void usarHabilidad(Personaje enemigo, ArrayList<Heroe> heroes) {
        if (habilidades.isEmpty()) {
            System.out.println(getNombre() + " no tiene habilidades.");
            return;
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("\nHabilidades disponibles:");
        for (int i = 0; i < habilidades.size(); i++) {
            Habilidad h = habilidades.get(i);
            System.out.println((i + 1) + ". " + h.getNombre() + " (MP: " + h.getCosteMp() + ")");
        }

        System.out.print("Elige una habilidad: ");
        int opcion = sc.nextInt();

        if (opcion < 1 || opcion > habilidades.size()) {
            System.out.println("Te inventaste esa opcion.");
            return;
        }

        Habilidad h = habilidades.get(opcion - 1);

        if (getMagiaMp() < h.getCosteMp()) {
            System.out.println("No tienes suficiente MP para usar " + h.getNombre() + ".");
            return;
        }

        setMagiaMp(getMagiaMp() - h.getCosteMp());
        System.out.println(getNombre() + " usa " + h.getNombre() + "!");

        switch (h.getTipo().toLowerCase()) {
            case "daño":
                enemigo.setVidaHp(enemigo.getVidaHp() - h.getPoder());
                System.out.println(enemigo.getNombre() + " recibe " + h.getPoder() + " puntos de daño mágico :O.");
                if (enemigo.getVidaHp() <= 0) enemigo.setVive(false);
                break;

            case "curación":
                // 💚 Elegir compañero a curar
                System.out.println("\n¿A qué compañero quieres curar?");
                for (int i = 0; i < heroes.size(); i++) {
                    Heroe aliado = heroes.get(i);
                    if (aliado.estaVivo()) {
                        System.out.println((i + 1) + ". " + aliado.getNombre() + " (HP: " + aliado.getVidaHp() + ")");
                    }
                }
                System.out.print("Elige número: ");
                int eleccion = sc.nextInt();

                if (eleccion < 1 || eleccion > heroes.size() || !heroes.get(eleccion - 1).estaVivo()) {
                    System.out.println("Y esa opcion de donde salio?.");
                    return;
                }

                Heroe aliadoCurado = heroes.get(eleccion - 1);
                aliadoCurado.setVidaHp(aliadoCurado.getVidaHp() + h.getPoder());
                System.out.println(aliadoCurado.getNombre() + " recupera " + h.getPoder() + " puntos de vida.");
                break;

            case "estado":
                enemigo.setEstado(new Estado(h.getEstado(), h.getDuracion()));
                System.out.println(enemigo.getNombre() + " ahora está " + h.getEstado() + ".");
                break;

            default:
                System.out.println("Que es esa habilidad? para proximas actualizaciones te la ponemos.");
                break;
        }
    }
}
