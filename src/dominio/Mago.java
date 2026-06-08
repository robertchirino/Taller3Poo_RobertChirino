

//Robert Chirino - 21.370.498-2



package dominio;

import java.util.ArrayList;
import java.util.List;

public class Mago {
    private String nombre;
    private List<Hechizo> hechizosDominados;

    public Mago(String nombre) {
        this.nombre = nombre;
        this.hechizosDominados = new ArrayList<>(); 
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public List<Hechizo> getHechizosDominados() { return hechizosDominados; }

    public int calcularPuntuacionTotal() {
        int total = 0;
        for (Hechizo h : hechizosDominados) {
            total += h.calcularPuntuacion();
        }
        return total;
    }

}