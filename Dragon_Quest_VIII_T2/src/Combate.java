import java.util.*;

public class Combate {
    private List<Heroe> heroes;
    private List<Enemigo> enemigos;
    private Random random = new Random();

    public Combate(List<Heroe> heroes, List<Enemigo> enemigos) {
        this.heroes = heroes;
        this.enemigos = enemigos;
    }

    public void iniciar() {
        System.out.println("\n=== ¡Comienza el combate! ===\n");
        int turno = 1;
        Scanner sc = new Scanner(System.in);

        while (hayVivos(heroes) && hayVivos(enemigos)) {
            System.out.println("---- Turno " + turno + " ----");
            mostrarEstado();

            List<Personaje> participantes = new ArrayList<>();
            participantes.addAll(heroes);
            participantes.addAll(enemigos);

            participantes.sort((a, b) -> b.getVelocidad() - a.getVelocidad());

            for (Personaje p : participantes) {
                if (!p.estaVivo()) continue;

                // Aplicar efectos de estado al inicio del turno
                if (p.getEstado() != null) {
                    p.getEstado().aplicarEfecto(p);
                    if (p.getEstado().terminado()) {
                        System.out.println(p.getNombre() + " ya no está " + p.getEstado().getNombre() + ".");
                        p.setEstado(null);
                    }
                }

                if (!p.estaVivo()) continue;

                if (p instanceof Heroe) {
                    Heroe heroe = (Heroe) p;
                    Enemigo objetivo = elegirEnemigo();
                    if (objetivo == null) break;

                    System.out.println("\n==============================");
                    System.out.println("     Turno de " + heroe.getNombre());
                    System.out.println("==============================");
                    System.out.println("1. Atacar");
                    System.out.println("2. Defender");
                    System.out.println("3. Usar Habilidad");
                    System.out.print("Elige una acción: ");
                    int opcion = sc.nextInt();

                    switch (opcion) {
                        case 1 -> heroe.atacar(objetivo);
                        case 2 -> heroe.defender();
                        case 3 -> heroe.usarHabilidad(objetivo);
                        default -> System.out.println("Opción inválida. Pierdes el turno 😅");
                    }

                } else if (p instanceof Enemigo) {
                    Heroe objetivo = elegirHeroe();
                    if (objetivo != null) ((Enemigo) p).accionAutomatica(objetivo);
                }

                if (!hayVivos(heroes) || !hayVivos(enemigos)) break;
            }
            turno++;
        }

        if (hayVivos(heroes))
            System.out.println("¡Los héroes han ganado!");
        else
            System.out.println("¡Los enemigos han ganado!");
    }

    private boolean hayVivos(List<? extends Personaje> lista) {
        for (Personaje p : lista) if (p.estaVivo()) return true;
        return false;
    }

    private void mostrarEstado() {
        System.out.println("Héroes:");
        for (Heroe h : heroes) {
            System.out.println("  " + h.getNombre() + " - HP: " + h.getVidaHp() + " MP: " + h.getMagiaMp() + estadoString(h.getEstado()));
        }

        System.out.println("Enemigos:");
        for (Enemigo e : enemigos) {
            System.out.println("  " + e.getNombre() + " - HP: " + e.getVidaHp() + estadoString(e.getEstado()));
        }
    }

    private String estadoString(Estado est) {
        if (est == null) return "";
        return " [" + est.getNombre() + " (" + est.getDuracion() + ")]";
    }

    private Enemigo elegirEnemigo() {
        List<Enemigo> vivos = new ArrayList<>();
        for (Enemigo e : enemigos) if (e.estaVivo()) vivos.add(e);
        return vivos.isEmpty() ? null : vivos.get(random.nextInt(vivos.size()));
    }

    private Heroe elegirHeroe() {
        List<Heroe> vivos = new ArrayList<>();
        for (Heroe h : heroes) if (h.estaVivo()) vivos.add(h);
        return vivos.isEmpty() ? null : vivos.get(random.nextInt(vivos.size()));
    }
}
