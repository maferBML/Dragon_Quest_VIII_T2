public class Heroe extends Personaje {

    public Heroe(String nombre, int vidaHp, int magiaMp, int ataque, int defensa, int velocidad) {
        super(nombre, vidaHp, magiaMp, ataque, defensa, velocidad);
    }

    @Override
    public void atacar(Personaje enemigo) {
        int danio = this.getAtaque() - enemigo.getDefensa();
        if (danio < 0) {
            danio = 0;
        }

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
        System.out.println(this.getNombre() + " se prepara para defenderse del próximo ataque.");
    }

    public void curar(int cantidad) {
        this.setVidaHp(this.getVidaHp() + cantidad);
        System.out.println(this.getNombre() + " se cura " + cantidad + " puntos de vida. Vida actual: " + this.getVidaHp());
    }
}
