package dominio;

public class HechizoFuego extends Hechizo {
    private int duracionQuemadura;

    public HechizoFuego(String nombre, String tipo, int daño, int duracionQuemadura) {
        super(nombre, tipo, daño);
        this.duracionQuemadura = duracionQuemadura;
    }

    public int getDuracionQuemadura() { return duracionQuemadura; }
    public void setDuracionQuemadura(int dq) { this.duracionQuemadura = dq; }

    @Override
    public int calcularPuntuacion() {
        return getDaño() * this.duracionQuemadura;
    }

    @Override
    public String toTxtFormat() {
        return getNombre() + ";" + getTipo() + ";" + getDaño() + ";" + this.duracionQuemadura;
    }
}