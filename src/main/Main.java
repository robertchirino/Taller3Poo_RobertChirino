package main;

import logica.ISistemaMagos;
import logica.SistemaMagosImpl;
import dominio.*;

import java.util.Scanner;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		// Inicializacion del sistema e inicio de la lectura de archivos .txt
		ISistemaMagos sistema = new SistemaMagosImpl();
		sistema.cargarDatos();

		Scanner scanner = new Scanner(System.in);
		int opcionPrincipal = 0;

		System.out.println("========================================");
		System.out.println("   BIENVENIDO AL SISTEMA DE MAGOS   ");
		System.out.println("========================================");

		do {
			System.out.println("\n--- MENU PRINCIPAL ---");
			System.out.println("1. Entrar como Administrador");
			System.out.println("2. Entrar como Analista de Datos");
			System.out.println("3. Salir y Guardar");
			System.out.print("Seleccione un rol: ");

			try {
				opcionPrincipal = Integer.parseInt(scanner.nextLine());

				switch (opcionPrincipal) {
				case 1:
					menuAdministrador(scanner, sistema);
					break;
				case 2:
					menuAnalista(scanner, sistema);
					break;
				case 3:
					System.out.println("\nGuardando todos los cambios en Hechizos.txt y Magos.txt...");
					sistema.guardarDatos();
					System.out.println("Datos guardados exitosamente Programa finalizado.");
					break;
				default:
					System.out.println("Opcion no valida. Por favor, intente nuevamente.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Error de entrada: Debe ingresar un numero entero valido.");
			}
		} while (opcionPrincipal != 3);

		scanner.close();
	}

	/**
	 * Sub-menu para el rol de Administrador (CRUD de Magos y Hechizos)
	 */
	private static void menuAdministrador(Scanner scanner, ISistemaMagos sistema) {
		int opcion = 0;
		do {
			System.out.println("\n--- MENU ADMINISTRADOR ---");
			System.out.println("1. Agregar Mago");
			System.out.println("2. Modificar Mago");
			System.out.println("3. Eliminar Mago");
			System.out.println("4. Agregar Hechizo");
			System.out.println("5. Modificar Hechizo");
			System.out.println("6. Eliminar Hechizo");
			System.out.println("7. Volver al Menu Principal");
			System.out.print("Seleccione una opcion: ");

			try {
				opcion = Integer.parseInt(scanner.nextLine());
				switch (opcion) {
				case 1:
					System.out.print("Ingrese el nombre del nuevo Mago: ");
					String nombreMago = scanner.nextLine();

					if (sistema.agregarMago(nombreMago)) {
						System.out.println("Mago creado exitosamente.");

						boolean asignando = true;
						while (asignando) {
							System.out.println("\nDesea asignarle un hechizo a " + nombreMago + "?");
							System.out.println("1. Asignar un hechizo YA EXISTENTE del catalogo");
							System.out.println("2. Crear un hechizo NUEVO y asignarselo");
							System.out.println("3. No asignar más hechizos (Terminar)");
							System.out.print("Seleccione una opcion: ");

							try {
								int opHechizo = Integer.parseInt(scanner.nextLine());
								if (opHechizo == 1) {
									sistema.mostrarTodosLosHechizos();
									System.out.print("Escriba el NOMBRE EXACTO del hechizo a asignar: ");
									String nombreH = scanner.nextLine();

									if (sistema.asignarHechizoAMago(nombreMago, nombreH)) {
										System.out.println("Hechizo asignado con exito");
									} else {
										System.out.println(
												"Error: El hechizo no existe en el catalogo o el mago ya lo tiene.");
									}

								} else if (opHechizo == 2) {
									System.out.println("--- Creacion del nuevo hechizo ---");
									Hechizo nuevoH = capturarDatosHechizo(scanner);

									if (nuevoH != null) {
										if (sistema.agregarHechizo(nuevoH)) {
											sistema.asignarHechizoAMago(nombreMago, nuevoH.getNombre());
											System.out.println("Hechizo creado y asignado con exito");
										} else {
											System.out.println(
													"Error: Ya existe un hechizo con ese nombre en el catalogo.");
										}
									}

								} else if (opHechizo == 3) {
									asignando = false;
								} else {
									System.out.println("Opcion invalida.");
								}
							} catch (NumberFormatException e) {
								System.out.println("Error: Ingrese un numero valido.");
							}
						}
					} else {
						System.out.println("Error: El mago ya se encuentra registrado.");
					}
					break;

				case 2:
					System.out.print("Ingrese el nombre actual del mago a modificar: ");
					String mViejo = scanner.nextLine();
					System.out.print("Ingrese el nuevo nombre para el mago: ");
					String mNuevo = scanner.nextLine();
					if (sistema.modificarMago(mViejo, mNuevo)) {
						System.out.println("Mago renombrado correctamente.");
					} else {
						System.out.println("Error: No se encontro al mago o el nuevo nombre ya esta ocupado.");
					}
					break;

				case 3:
					System.out.print("Ingrese el nombre del mago a eliminar: ");
					String mEliminar = scanner.nextLine();
					if (sistema.eliminarMago(mEliminar)) {
						System.out.println("Mago eliminado correctamente.");
					} else {
						System.out.println("Error: No se encontro al mago especificado.");
					}
					break;

				case 4:
					Hechizo nuevoHechizo = capturarDatosHechizo(scanner);
					if (nuevoHechizo != null && sistema.agregarHechizo(nuevoHechizo)) {
						System.out.println("Hechizo registrado en el catalogo global.");
					} else {
						System.out.println("Error: El hechizo ya existe o los parametros ingresados no son validos.");
					}
					break;

				case 5:
					System.out.print("Ingrese el nombre del hechizo que desea modificar: ");
					String hViejo = scanner.nextLine();
					System.out.println("--- Ingrese los nuevos datos para el hechizo ---");
					Hechizo modificadoHechizo = capturarDatosHechizo(scanner);

					if (modificadoHechizo != null && sistema.modificarHechizo(hViejo, modificadoHechizo)) {
						System.out.println("Hechizo modificado y actualizado en todos los magos correspondientes.");
					} else {
						System.out.println("Error: No se pudo modificar el hechizo.");
					}
					break;

				case 6:
					System.out.print("Ingrese el nombre del hechizo a eliminar: ");
					String hEliminar = scanner.nextLine();
					if (sistema.eliminarHechizo(hEliminar)) {
						System.out.println("Hechizo removido por completo del sistema.");
					} else {
						System.out.println("Error: No se encontro el hechizo especificado.");
					}
					break;

				case 7:
					System.out.println("Regresando al Menu Principal...");
					break;
				default:
					System.out.println("Opcion invalida.");
				}
			} catch (Exception e) {
				System.out.println("Ocurrio un error al procesar la solicitud: " + e.getMessage());
			}
		} while (opcion != 7);
	}

	/**
	 * Sub-menu para el rol de Analista (Reportes, consultas y rankings)
	 */
	private static void menuAnalista(Scanner scanner, ISistemaMagos sistema) {
		int opcion = 0;
		do {
			System.out.println("\n--- MENU ANALISTA DE DATOS ---");
			System.out.println("1. Top 10 mejores Hechizos");
			System.out.println("2. Top 3 mejores Magos");
			System.out.println("3. Mostrar todos los Hechizos");
			System.out.println("4. Mostrar todos los Magos");
			System.out.println("5. Mostrar todos los Hechizos junto a sus Magos");
			System.out.println("6. Mostrar todos los Magos junto a sus Hechizos");
			System.out.println("7. Volver al Menu Principal");
			System.out.print("Seleccione una opcion: ");

			try {
				opcion = Integer.parseInt(scanner.nextLine());
				switch (opcion) {
				case 1:
					System.out.println("\n--- TOP 10 MEJORES HECHIZOS ---");
					List<Hechizo> topHechizos = sistema.obtenerTop10Hechizos();
					for (int i = 0; i < topHechizos.size(); i++) {
						Hechizo h = topHechizos.get(i);
						System.out.println((i + 1) + ". " + h.getNombre() + " (" + h.getTipo() + ") | Puntos: "
								+ h.calcularPuntuacion());
					}
					break;
				case 2:
					System.out.println("\n--- TOP 3 MEJORES MAGOS ---");
					List<Mago> topMagos = sistema.obtenerTop3Magos();
					for (int i = 0; i < topMagos.size(); i++) {
						Mago m = topMagos.get(i);
						System.out.println(
								(i + 1) + ". " + m.getNombre() + " | Puntaje Total: " + m.calcularPuntuacionTotal());
					}
					break;
				case 3:
					sistema.mostrarTodosLosHechizos();
					break;
				case 4:
					sistema.mostrarTodosLosMagos();
					break;
				case 5:
					sistema.mostrarHechizosJuntoAMagos();
					break;
				case 6:
					sistema.mostrarMagosJuntoAHechizos();
					break;
				case 7:
					System.out.println("Regresando al Menu Principal...");
					break;
				default:
					System.out.println("Opcion no valida.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Error de entrada: Debe ingresar un valor numerico entero.");
			}
		} while (opcion != 7);
	}

	/**
	 * Metodo auxiliar privado para encapsular la lectura por consola de un Hechizo
	 * seun su respectivo tipo de elemento.
	 */
	private static Hechizo capturarDatosHechizo(Scanner scanner) {
		try {
			System.out.print("Nombre del hechizo: ");
			String nombre = scanner.nextLine();
			System.out.print("Tipo (Fuego, Tierra, Planta, Agua): ");
			String tipo = scanner.nextLine();
			System.out.print("Daño base: ");
			int daño = Integer.parseInt(scanner.nextLine());

			if (tipo.equalsIgnoreCase("Fuego")) {
				System.out.print("Duracion de quemadura: ");
				int duracion = Integer.parseInt(scanner.nextLine());
				return new HechizoFuego(nombre, tipo, daño, duracion);

			} else if (tipo.equalsIgnoreCase("Tierra")) {
				System.out.print("Mejora de defensa: ");
				int defensa = Integer.parseInt(scanner.nextLine());
				return new HechizoTierra(nombre, tipo, daño, defensa);

			} else if (tipo.equalsIgnoreCase("Planta")) {
				System.out.print("Duracion de Aturdimiento (Stun): ");
				int stun = Integer.parseInt(scanner.nextLine());
				System.out.print("Cantidad de plantas: ");
				int plantas = Integer.parseInt(scanner.nextLine());
				return new HechizoPlanta(nombre, tipo, daño, stun, plantas);

			} else if (tipo.equalsIgnoreCase("Agua")) {
				System.out.print("Cantidad de curación (Heal): ");
				int heal = Integer.parseInt(scanner.nextLine());
				System.out.print("Presión de agua: ");
				int presion = Integer.parseInt(scanner.nextLine());
				return new HechizoAgua(nombre, tipo, daño, heal, presion);
			} else {
				System.out.println("Error: Tipo de elemento desconocido.");
				return null;
			}
		} catch (NumberFormatException e) {
			System.out.println("Error al capturar datos numéricos del hechizo. Registro cancelado.");
			return null;
		}
	}
}