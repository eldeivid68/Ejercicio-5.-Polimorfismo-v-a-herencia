package qtz2.vista;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import qtz2.modelo.*;

/** Única clase que lee al usuario y escribe las respuestas. */
public final class VistaConsola {
    private final Scanner entrada;
    private final PrintStream salida;

    public VistaConsola(Scanner entrada, PrintStream salida) {
        this.entrada = entrada;
        this.salida = salida;
    }
    public String leerOpcion() {
        salida.println("\n=== DEFENSA DE QTZ2 ===");
        salida.println("1. Listar módulos\n2. Buscar por ID o nombre\n3. Catálogo por costo");
        salida.println("4. Avanzar un ciclo\n5. Resumen de comunicaciones\n0. Salir");
        return leerTexto("Opción: ");
    }
    public String leerTexto(String indicacion) {
        salida.print(indicacion);
        return entrada.hasNextLine() ? entrada.nextLine().trim() : null;
    }
    public void mostrarMensaje(String mensaje) { salida.println(mensaje); }
    public void mostrarModulos(List<Modulo> modulos) {
        if (modulos.isEmpty()) salida.println("No se encontraron módulos.");
        for (Modulo modulo : modulos) salida.println(modulo);
    }
    public void mostrarEstado(long ciclo, RecursosMision recursos) {
        salida.println("Ciclos transcurridos: " + ciclo + " | " + recursos);
    }
    public void mostrarCiclo(long ciclo, List<ResultadoOperacion> resultados, RecursosMision recursos) {
        salida.println("\nResultados del ciclo " + ciclo + ":");
        for (ResultadoOperacion resultado : resultados) salida.println(resultado);
        mostrarEstado(ciclo, recursos);
    }
    public void mostrarResumen(ResumenComunicaciones resumen) {
        salida.println("\n=== COMUNICACIONES ===");
        salida.println("Módulos de tierra: " + resumen.getTotalModulos());
        salida.println("Activos: " + resumen.getActivos());
        salida.println("Capacidad activa por ciclo: " + resumen.getCapacidadActiva() + " MB");
        salida.println("Total histórico descargado: " + resumen.getTotalDescargado() + " MB");
        if (resumen.getLideres().isEmpty()) salida.println("No hay módulos de tierra.");
        else {
            salida.println("Mayor descarga histórica (incluye todos los empates):");
            for (ModuloTierra tierra : resumen.getLideres())
                salida.println(tierra.getId() + " | " + tierra.getNombre() + " | estación="
                        + tierra.getEstacion() + " | " + tierra.getTotalDescargado() + " MB");
        }
    }
}
