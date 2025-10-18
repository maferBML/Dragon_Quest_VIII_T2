public class Estado {
    private String nombre;
    private int duracion;

    public Estado(String nombre, int duracion) {
        this.nombre = nombre;
        this.duracion = duracion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    public void reducirDuracion() {
        if (duracion > 0) {
            duracion--;
        }
    }

    public boolean terminado() {
        return duracion <= 0;
    }
}
