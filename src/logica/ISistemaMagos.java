package logica;

import dominio.Mago;
import dominio.Hechizo;
import java.util.List;

public interface ISistemaMagos {
    // Carga de Archivos
    void cargarDatos();
    void guardarDatos();

    // CRUD Administrador
    boolean agregarMago(String nombre);
    boolean modificarMago(String nombreViejo, String nombreNuevo);
    boolean eliminarMago(String nombre);
    boolean asignarHechizoAMago(String nombreMago, String nombreHechizo);
    
    boolean agregarHechizo(Hechizo hechizo);
    boolean modificarHechizo(String nombreHechizo, Hechizo nuevoHechizo);
    boolean eliminarHechizo(String nombreHechizo);

    // Reportes Analista
    List<Hechizo> obtenerTop10Hechizos();
    List<Mago> obtenerTop3Magos();
    void mostrarTodosLosHechizos();
    void mostrarTodosLosMagos();
    void mostrarHechizosJuntoAMagos();
    void mostrarMagosJuntoAHechizos();
}