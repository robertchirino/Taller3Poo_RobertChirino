

//Robert Chirino - 21.370.498-2



package logica;

import dominio.*;
import java.io.*;
import java.util.*;

public class SistemaMagosImpl implements ISistemaMagos {

    private List<Mago> listaMagos;
    private List<Hechizo> listaHechizos;

    public SistemaMagosImpl() {
        this.listaMagos = new ArrayList<>();
        this.listaHechizos = new ArrayList<>();
    }

    @Override
    public void cargarDatos() {
        File archivoHechizos = new File("Hechizos.txt");
        if (archivoHechizos.exists()) {
            try (Scanner sc = new Scanner(archivoHechizos)) {
                while (sc.hasNextLine()) {
                    String linea = sc.nextLine().trim();
                    if (linea.isEmpty()) continue;
                    
                    String[] partes = linea.split(";");
                    String nombre = partes[0];
                    String tipo = partes[1];
                    int daño = Integer.parseInt(partes[2]);

                    Hechizo h = null;
                    if (tipo.equalsIgnoreCase("Fuego")) {
                        int duracionQuemadura = Integer.parseInt(partes[3]);
                        h = new HechizoFuego(nombre, tipo, daño, duracionQuemadura);
                    } else if (tipo.equalsIgnoreCase("Tierra")) {
                        int mejoraDefensa = Integer.parseInt(partes[3]);
                        h = new HechizoTierra(nombre, tipo, daño, mejoraDefensa);
                    } else if (tipo.equalsIgnoreCase("Planta")) {
                        String[] subPartes = partes[3].split(",");
                        int duracionStun = Integer.parseInt(subPartes[0]);
                        int cantPlantas = Integer.parseInt(subPartes[1]);
                        h = new HechizoPlanta(nombre, tipo, daño, duracionStun, cantPlantas);
                    } else if (tipo.equalsIgnoreCase("Agua")) {
                        String[] subPartes = partes[3].split(",");
                        int cantidadHeal = Integer.parseInt(subPartes[0]);
                        int presionDeAgua = Integer.parseInt(subPartes[1]);
                        h = new HechizoAgua(nombre, tipo, daño, cantidadHeal, presionDeAgua);
                    }

                    if (h != null) {
                        this.listaHechizos.add(h);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("No se pudo leer Hechizos.txt: " + e.getMessage());
            }
        }

        File archivoMagos = new File("Magos.txt");
        if (archivoMagos.exists()) {
            try (Scanner sc = new Scanner(archivoMagos)) {
                while (sc.hasNextLine()) {
                    String linea = sc.nextLine().trim();
                    if (linea.isEmpty()) continue;

                    String[] partes = linea.split(";");
                    String nombreMago = partes[0];
                    Mago mago = new Mago(nombreMago);

                    if (partes.length > 1 && !partes[1].isEmpty()) {
                        String[] nombresHechizos = partes[1].split("\\|");
                        for (String nomH : nombresHechizos) {
                            Hechizo hEncontrado = buscarHechizoPorNombre(nomH.trim());
                            if (hEncontrado != null) {
                                mago.getHechizosDominados().add(hEncontrado);
                            }
                        }
                    }
                    this.listaMagos.add(mago);
                }
            } catch (FileNotFoundException e) {
                System.out.println("No se pudo leer Magos.txt: " + e.getMessage());
            }
        }
    }

    @Override
    public void guardarDatos() {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Hechizos.txt"))) {
            for (Hechizo h : listaHechizos) {
                bw.write(h.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al escribir Hechizos.txt: " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Magos.txt"))) {
            for (Mago m : listaMagos) {
                StringBuilder sb = new StringBuilder();
                sb.append(m.getNombre()).append("|");
                
                List<Hechizo> hechizos = m.getHechizosDominados();
                for (int i = 0; i < hechizos.size(); i++) {
                    sb.append(hechizos.get(i).getNombre());
                    if (i < hechizos.size() - 1) {
                        sb.append("|");
                    }
                }
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al escribir Magos.txt: " + e.getMessage());
        }
    }

    @Override
    public boolean agregarMago(String nombre) {
        if (buscarMagoPorNombre(nombre) != null) return false; 
        listaMagos.add(new Mago(nombre));
        guardarDatos(); 
        return true;
    }

    @Override
    public boolean modificarMago(String nombreViejo, String nombreNuevo) {
        Mago mago = buscarMagoPorNombre(nombreViejo);
        if (mago == null || buscarMagoPorNombre(nombreNuevo) != null) return false;
        mago.setNombre(nombreNuevo);
        guardarDatos();
        return true;
    }

    @Override
    public boolean eliminarMago(String nombre) {
        Mago mago = buscarMagoPorNombre(nombre);
        if (mago == null) return false;
        listaMagos.remove(mago);
        guardarDatos();
        return true;
    }
    @Override
    public boolean asignarHechizoAMago(String nombreMago, String nombreHechizo) {
        Mago mago = buscarMagoPorNombre(nombreMago);
        Hechizo hechizo = buscarHechizoPorNombre(nombreHechizo);
    
        if (mago == null || hechizo == null) {
            return false;
        }
    
        if (mago.getHechizosDominados().contains(hechizo)) {
             return false; 
        }
    
        mago.getHechizosDominados().add(hechizo);
        guardarDatos();
        return true;
    }

    @Override
    public boolean agregarHechizo(Hechizo hechizo) {
        if (buscarHechizoPorNombre(hechizo.getNombre()) != null) return false;
        listaHechizos.add(hechizo);
        guardarDatos();
        return true;
    }

    @Override
    public boolean modificarHechizo(String nombreHechizo, Hechizo nuevoHechizo) {
        Hechizo viejo = buscarHechizoPorNombre(nombreHechizo);
        if (viejo == null) return false;
        
        listaHechizos.remove(viejo);
        listaHechizos.add(nuevoHechizo);
        
        for (Mago m : listaMagos) {
            int idx = m.getHechizosDominados().indexOf(viejo);
            if (idx != -1) {
                m.getHechizosDominados().set(idx, nuevoHechizo);
            }
        }
        guardarDatos();
        return true;
    }

    @Override
    public boolean eliminarHechizo(String nombreHechizo) {
        Hechizo h = buscarHechizoPorNombre(nombreHechizo);
        if (h == null) return false;
        
        listaHechizos.remove(h);
        for (Mago m : listaMagos) {
            m.getHechizosDominados().remove(h);
        }
        guardarDatos();
        return true;
    }

    @Override
    public void mostrarTodosLosHechizos() {
        System.out.println("\n--- LISTADO DE HECHIZOS TOTALES ---");
        for (Hechizo h : listaHechizos) {
            System.out.println("- " + h.getNombre() + " (Tipo: " + h.getTipo() + ", Daño: " + h.getDaño() + ")");
        }
    }

    @Override
    public void mostrarTodosLosMagos() {
        System.out.println("\n--- LISTADO DE MAGOS TOTALES ---");
        for (Mago m : listaMagos) {
            System.out.println("- " + m.getNombre() + " (Puntaje Total: " + m.calcularPuntuacionTotal() + ")");
        }
    }

    @Override
    public void mostrarHechizosJuntoAMagos() {
        System.out.println("\n--- HECHIZOS Y QUIENES LOS DOMINAN ---");
        for (Hechizo h : listaHechizos) {
            System.out.print("- " + h.getNombre() + " dominado por: ");
            List<String> dominadores = new ArrayList<>();
            for (Mago m : listaMagos) {
                if (m.getHechizosDominados().contains(h)) {
                    dominadores.add(m.getNombre());
                }
            }
            System.out.println(dominadores.isEmpty() ? "Nadie" : String.join(", ", dominadores));
        }
    }

    @Override
    public void mostrarMagosJuntoAHechizos() {
        System.out.println("\n--- MAGOS Y SUS HECHIZOS ---");
        for (Mago m : listaMagos) {
            System.out.println("Mago: " + m.getNombre());
            if (m.getHechizosDominados().isEmpty()) {
                System.out.println("  [Sin hechizos dominados]");
            } else {
                for (Hechizo h : m.getHechizosDominados()) {
                    System.out.println("  -> " + h.getNombre() + " (" + h.getTipo() + ") Puntos: " + h.calcularPuntuacion());
                }
            }
        }
    }

    @Override
    public List<Hechizo> obtenerTop10Hechizos() {
        List<Hechizo> copia = new ArrayList<>(listaHechizos);
        copia.sort((h1, h2) -> Integer.compare(h2.calcularPuntuacion(), h1.calcularPuntuacion()));
        return copia.subList(0, Math.min(10, copia.size()));
    }

    @Override
    public List<Mago> obtenerTop3Magos() {
        List<Mago> copia = new ArrayList<>(listaMagos);
        copia.sort((m1, m2) -> Integer.compare(m2.calcularPuntuacionTotal(), m1.calcularPuntuacionTotal()));
        return copia.subList(0, Math.min(3, copia.size()));
    }

    private Mago buscarMagoPorNombre(String nombre) {
        for (Mago m : listaMagos) {
            if (m.getNombre().equalsIgnoreCase(nombre.trim())) return m;
        }
        return null;
    }

    private Hechizo buscarHechizoPorNombre(String nombre) {
        for (Hechizo h : listaHechizos) {
            if (h.getNombre().equalsIgnoreCase(nombre.trim())) return h;
        }
        return null;
    }
}