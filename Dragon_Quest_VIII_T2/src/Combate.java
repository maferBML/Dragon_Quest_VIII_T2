import java.util.*;

public class Combate {
    private List<Heroe> heroes;
    private List<Enemigo> enemigos;
    private Random random = new Random();

    public Combate(List<Heroe> heroes, List<Enemigo> enemigos) {
        this.heroes = heroes;
        this.enemigos = enemigos;
    }

    public void iniciar(){
        System.out.println("\n=== ¡Comienza el combate! ===\n");
        int turno = 1;

        while (hayVivos(heroes) && hayVivos(enemigos)) {
            System.out.println("---- Turno " + turno + " ----");
            mostrarEstado();

            List<Personaje> participantes = new ArrayList<>();
            participantes.addAll(heroes);
            participantes.addAll(enemigos);

            participantes.sort((a, b) -> b.getVelocidad() - a.getVelocidad());

            for (Personaje p : participantes) {
                if (!p.estaVivo()) continue;

                if (p instanceof Heroe){
                    Enemigo objetivo = elegirEnemigo();
                    if (objetivo != null){
                        System.out.println(p.getNombre() + " ataca a " + objetivo.getNombre());
                        ((Heroe) p).atacar(objetivo);
                }

                } else if (p instanceof Enemigo){
                    Heroe objetivo = elegirHeroe();
                    if (objetivo != null){
                        ((Enemigo) p).accionAutomatica(objetivo);
                    }
                }
            

                if  (!hayVivos(heroes) || !hayVivos(enemigos)) break;
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
            System.out.println("  " + h.getNombre() + " - HP: " + h.getVidaHp() + " MP: " + h.getMagiaMp());
        }

        System.out.println("Enemigos:");
        for (Enemigo e : enemigos) {
            System.out.println("  " + e.getNombre() + " - HP: " + e.getVidaHp());
        }
    }

    private Enemigo elegirEnemigo() {
        List<Enemigo> vivos = new ArrayList<>();
        for (Enemigo e : enemigos) if (e.estaVivo()) vivos.add(e);
        if (vivos.isEmpty()) return null;
        return vivos.get(random.nextInt(vivos.size()));
    }

    private Heroe elegirHeroe() {
        List<Heroe> vivos = new ArrayList<>();
        for (Heroe h : heroes) if (h.estaVivo()) vivos.add(h);
        if (vivos.isEmpty()) return null;
        return vivos.get(random.nextInt(vivos.size()));
    }

}
