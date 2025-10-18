import java.util.*;

public class App {
    public static void main(String[] args) {
        Heroe heroe = new Heroe("Héroe", 100, 30, 25, 10, 15);
        Enemigo slime = new Enemigo("Slime", 60, 0, 15, 5, 8, "agresivo");

        List<Heroe> heroes = new ArrayList<>();
        heroes.add(heroe);

        List<Enemigo> enemigos = new ArrayList<>();
        enemigos.add(slime);

        Combate combate = new Combate(heroes, enemigos);
        combate.iniciar(); 
    }
}

