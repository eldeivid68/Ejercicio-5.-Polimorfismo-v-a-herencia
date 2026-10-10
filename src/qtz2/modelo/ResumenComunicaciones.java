package qtz2.modelo;

import java.util.List;

/** Datos del informe; las listas entregadas al cliente no se pueden modificar. */
public final class ResumenComunicaciones {
    private final int totalModulos;
    private final int activos;
    private final long capacidadActiva;
    private final long totalDescargado;
    private final List<ModuloTierra> lideres;

    public ResumenComunicaciones(int total, int activos, long capacidad, long descargado,
            List<ModuloTierra> lideres) {
        totalModulos = total;
        this.activos = activos;
        capacidadActiva = capacidad;
        totalDescargado = descargado;
        this.lideres = List.copyOf(lideres);
    }
    public int getTotalModulos() { return totalModulos; }
    public int getActivos() { return activos; }
    public long getCapacidadActiva() { return capacidadActiva; }
    public long getTotalDescargado() { return totalDescargado; }
    public List<ModuloTierra> getLideres() { return lideres; }
}
