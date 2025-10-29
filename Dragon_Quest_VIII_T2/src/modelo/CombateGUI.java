package modelo;

import java.util.*;

public class CombateGUI {
    private List<Heroe> heroes;
    private List<Enemigo> enemigos;
    private int turnoHeroe = 0;
    private Random random = new Random();
    private StringBuilder log = new StringBuilder();

    public CombateGUI(List<Heroe> heroes, List<Enemigo> enemigos) {
        this.heroes = heroes;
        this.enemigos = enemigos;
        log.append("💥 ¡Comienza la batalla! 💥\n\n");
        mostrarEstado();
    }

    public String getLog() {
        return log.toString();
    }

    // =================== ESTADO ===================
    private void mostrarEstado() {
        log.append("\n--- 🧾 Estado Actual ---\n");
        log.append("👑 Héroes:\n");
        for (Heroe h : heroes) {
            log.append(" - ").append(h.getNombre())
               .append(" | HP: ").append(h.getVidaHp())
               .append(" | MP: ").append(h.getMagiaMp());
            if (h.getEstado() != null) log.append(" [" + h.getEstado().getNombre() + "]");
            log.append("\n");
        }
        log.append("👹 Enemigos:\n");
        for (Enemigo e : enemigos) {
            log.append(" - ").append(e.getNombre())
               .append(" | HP: ").append(e.getVidaHp());
            if (e.getEstado() != null) log.append(" [" + e.getEstado().getNombre() + "]");
            log.append("\n");
        }
        log.append("-------------------------\n");
    }

    private boolean hayVivos(List<? extends Personaje> lista) {
        for (Personaje p : lista) if (p.estaVivo()) return true;
        return false;
    }

    public boolean hayGanador() {
        return !hayVivos(heroes) || !hayVivos(enemigos);
    }

    public String obtenerGanador() {
        return hayVivos(heroes) ? "🏆 ¡GANASTE!" : "💀 Has sido derrotado...";
    }

    private Heroe elegirHeroeAleatorioVivo() {
        List<Heroe> vivos = new ArrayList<>();
        for (Heroe h : heroes) if (h.estaVivo()) vivos.add(h);
        return vivos.isEmpty() ? null : vivos.get(random.nextInt(vivos.size()));
    }

    // =================== ATAQUE ===================
    public String atacarEnemigo(Enemigo objetivo) {
        Heroe atacante = obtenerHeroeActual();
        if (atacante == null) {
            log.append("No hay héroes disponibles para atacar.\n");
            return log.toString();
        }

        // 1️⃣ Mostrar primero de quién es el turno
        log.append("\n👉 Turno de: ").append(atacante.getNombre()).append("\n");

        // 2️⃣ Luego mostrar la acción de ataque
        if (objetivo == null || !objetivo.estaVivo()) {
            log.append("Ese enemigo ya está derrotado o no existe.\n");
        } else {
            atacante.atacar(objetivo);
            log.append("⚔️ ").append(atacante.getNombre())
               .append(" atacó a ").append(objetivo.getNombre()).append("!\n");
        }

        avanzarTurnoHeroe();

        if (todosHeroesActuaron()) {
            ejecutarTurnoEnemigos();
            mostrarEstado();
        }

        if (hayGanador()) log.append("\n").append(obtenerGanador()).append("\n");

        return log.toString();
    }

    // =================== HABILIDAD ===================
    public String usarHabilidadHeroe() {
        Heroe atacante = obtenerHeroeActual();
        if (atacante == null) {
            log.append("No hay héroes disponibles para usar una habilidad.\n");
            return log.toString();
        }

        // 1️⃣ Mostrar primero el turno del héroe
        log.append("\n👉 Turno de: ").append(atacante.getNombre()).append("\n");

        // 2️⃣ Indicar que está usando habilidad
        log.append("🪄 ").append(atacante.getNombre()).append(" está usando una habilidad...\n");

        atacante.usarHabilidad((ArrayList<Heroe>) heroes, enemigos);

        avanzarTurnoHeroe();

        if (todosHeroesActuaron()) {
            ejecutarTurnoEnemigos();
            mostrarEstado();
        }

        if (hayGanador()) log.append("\n").append(obtenerGanador()).append("\n");

        return log.toString();
    }

    // =================== TURNOS ===================
    private Heroe obtenerHeroeActual() {
        for (int i = 0; i < heroes.size(); i++) {
            int idx = (turnoHeroe + i) % heroes.size();
            if (heroes.get(idx).estaVivo()) {
                turnoHeroe = idx;
                return heroes.get(idx);
            }
        }
        return null;
    }

    private void avanzarTurnoHeroe() {
        for (int i = 1; i <= heroes.size(); i++) {
            int cand = (turnoHeroe + i) % heroes.size();
            if (heroes.get(cand).estaVivo()) {
                turnoHeroe = cand;
                return;
            }
        }
    }

    private boolean todosHeroesActuaron() {
        // La ronda termina cuando el siguiente héroe vivo es el primero de la lista
        Heroe primerVivo = null;
        for (Heroe h : heroes) {
            if (h.estaVivo()) {
                primerVivo = h;
                break;
            }
        }
        if (primerVivo == null) return true;
        return heroes.get(turnoHeroe) == primerVivo;
    }

    // =================== TURNO DE ENEMIGOS ===================
    private void ejecutarTurnoEnemigos() {
        log.append("\n💀 Turno de los enemigos...\n");
        for (Enemigo e : enemigos) {
            if (!e.estaVivo()) continue;
            Heroe objetivo = elegirHeroeAleatorioVivo();
            if (objetivo != null) {
                e.accionAutomatica(objetivo);
                log.append("💥 ").append(e.getNombre())
                   .append(" atacó a ").append(objetivo.getNombre()).append("!\n");
            }
        }
        log.append("☠️ Fin del turno de los enemigos.\n");
    }
}
