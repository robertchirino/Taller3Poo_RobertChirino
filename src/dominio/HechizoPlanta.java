package dominio;

public class HechizoPlanta extends Hechizo {
    private int duracionStun;
    private int cantPlantas;

    public HechizoPlanta(String nombre, String tipo, int daño, int duracionStun, int cantPlantas) {
        super(nombre, tipo, daño);
        this.duracionStun = duracionStun;
        this.cantPlantas = cantPlantas;
    }

    public int getDuracionStun() {
        return duracionStun;
    }

    public void setDuracionStun(int duracionStun) {
        this.duracionStun = duracionStun;
    }

    public int getCantPlantas() {
        return cantPlantas;
    }

    public void setCantPlantas(int cantPlantas) {
        this.cantPlantas = cantPlantas;
    }

    @Override
    public int calcularPuntuacion() {
        return getDaño() + (this.duracionStun * this.cantPlantas);
    }

    @Override
    public String toTxtFormat() {
        return getNombre() + ";" + getTipo() + ";" + getDaño() + ";" + this.duracionStun + "," + this.cantPlantas;
    }
}