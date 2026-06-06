package dominio;

public class HechizoTierra extends Hechizo {
    private int mejoraDefensa;

    public HechizoTierra(String nombre, String tipo, int daño, int mejoraDefensa) {
        super(nombre, tipo, daño);
        this.mejoraDefensa = mejoraDefensa;
    }

    public int getMejoraDefensa() {
        return mejoraDefensa;
    }

    public void setMejoraDefensa(int mejoraDefensa) {
        this.mejoraDefensa = mejoraDefensa;
    }

    @Override
    public int calcularPuntuacion() {
        return (getDaño() * this.mejoraDefensa) / 2;
    }

    @Override
    public String toTxtFormat() {
        return getNombre() + ";" + getTipo() + ";" + getDaño() + ";" + this.mejoraDefensa;
    }
}