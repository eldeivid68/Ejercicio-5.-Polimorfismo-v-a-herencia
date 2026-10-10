package qtz2.modelo;

public final class ModuloTierra extends Modulo {
    private final String estacion;
    private final long capacidadDescarga;
    private final long energiaDescarga;
    private long totalDescargado;

    public ModuloTierra(String id, String nombre, int salud, boolean activo, long costo,
            String estacion, long capacidad, long energia, long total) {
        super(id, nombre, salud, activo, costo);
        this.estacion = textoValido(estacion);
        capacidadDescarga = positivo(capacidad);
        energiaDescarga = positivo(energia);
        totalDescargado = noNegativo(total);
    }
    public String getEstacion() { return estacion; }
    public long getCapacidadDescarga() { return capacidadDescarga; }
    public long getEnergiaDescarga() { return energiaDescarga; }
    public long getTotalDescargado() { return totalDescargado; }

    @Override
    public ResultadoOperacion procesarCiclo(RecursosMision recursos) {
        if (!isActivo()) return new ResultadoOperacion(getId(), false, "Módulo inactivo.");
        if (!recursos.hayEnergia(energiaDescarga))
            return new ResultadoOperacion(getId(), false, "Energía insuficiente: requiere " + energiaDescarga + " u.");
        if (recursos.getDatosEnOrbita() == 0)
            return new ResultadoOperacion(getId(), false, "No hay datos pendientes en órbita.");
        long cantidad = Math.min(capacidadDescarga, recursos.getDatosEnOrbita());
        long nuevoTotal = Math.addExact(totalDescargado, cantidad);
        // Incluso una descarga parcial paga el consumo completo.
        recursos.descargar(energiaDescarga, cantidad);
        totalDescargado = nuevoTotal;
        return new ResultadoOperacion(getId(), true, "Descargó " + cantidad
                + " MB y consumió " + energiaDescarga + " u.");
    }
    @Override
    public String toString() {
        return super.toString() + " | TIERRA | estación=" + estacion
                + " | capacidad/ciclo=" + capacidadDescarga + " MB | energía/descarga="
                + energiaDescarga + " u | descargado histórico=" + totalDescargado + " MB";
    }
}
