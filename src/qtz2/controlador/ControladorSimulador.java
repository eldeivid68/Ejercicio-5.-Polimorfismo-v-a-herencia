package qtz2.controlador;

import java.util.List;
import qtz2.modelo.*;
import qtz2.vista.VistaConsola;

/** Coordina opciones; los cálculos y reglas permanecen en el modelo. */
public final class ControladorSimulador {
    private final Mision mision;
    private final VistaConsola vista;

    public ControladorSimulador(Mision mision, VistaConsola vista) {
        this.mision = mision;
        this.vista = vista;
    }
    public void ejecutar() {
        vista.mostrarMensaje("Misión en progreso. Cada inicio restaura la carga inicial.");
        vista.mostrarEstado(mision.getCiclosTranscurridos(), mision.getRecursos());
        while (true) {
            String opcion = vista.leerOpcion();
            if (opcion == null || opcion.equals("0")) {
                vista.mostrarMensaje("Simulación finalizada.");
                return;
            }
            switch (opcion) {
                case "1": vista.mostrarModulos(mision.getModulos()); break;
                case "2":
                    String consulta = vista.leerTexto("ID o nombre completo (ignora mayúsculas): ");
                    if (consulta == null) return;
                    vista.mostrarModulos(mision.buscar(consulta));
                    break;
                case "3": vista.mostrarModulos(mision.catalogoPorCosto()); break;
                case "4":
                    List<ResultadoOperacion> resultados = mision.avanzarCiclo();
                    vista.mostrarCiclo(mision.getCiclosTranscurridos(), resultados, mision.getRecursos());
                    break;
                case "5": vista.mostrarResumen(mision.resumenComunicaciones()); break;
                default: vista.mostrarMensaje("Opción inválida. Usa 0, 1, 2, 3, 4 o 5.");
            }
        }
    }
}
