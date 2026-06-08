

//Robert Chirino - 21.370.498-2


package dominio;

public abstract class Hechizo {
    private String nombre;
    private String tipo;
    private int daño;

    public Hechizo(String nombre, String tipo, int daño) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.daño = daño;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTipo() { return tipo; }
    public int getDaño() { return daño; }
    public void setDaño(int daño) { this.daño = daño; }

    public abstract int calcularPuntuacion();

    public abstract String toString();

}