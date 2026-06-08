

//Robert Chirino - 21.370.498-2



package dominio;

public class HechizoAgua extends Hechizo {
    private int cantidadHeal;
    private int presionDeAgua;

    public HechizoAgua(String nombre, String tipo, int daño, int cantidadHeal, int presionDeAgua) {
        super(nombre, tipo, daño);
        this.cantidadHeal = cantidadHeal;
        this.presionDeAgua = presionDeAgua;
    }

    public int getCantidadHeal() {
        return cantidadHeal;
    }

    public void setCantidadHeal(int cantidadHeal) {
        this.cantidadHeal = cantidadHeal;
    }

    public int getPresionDeAgua() {
        return presionDeAgua;
    }

    public void setPresionDeAgua(int presionDeAgua) {
        this.presionDeAgua = presionDeAgua;
    }

    @Override
    public int calcularPuntuacion() {
        return (getDaño() + this.cantidadHeal + this.presionDeAgua) * 2;
    }

    @Override
    public String toString() {
        return getNombre() + ";" + getTipo() + ";" + getDaño() + ";" + this.cantidadHeal + "," + this.presionDeAgua;
    }
}