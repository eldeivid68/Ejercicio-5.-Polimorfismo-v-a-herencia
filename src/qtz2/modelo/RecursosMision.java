package qtz2.modelo;

/** Recursos compartidos; las operaciones validan antes de modificar el estado. */
public final class RecursosMision {
    private long energiaDisponible;
    private long datosEnOrbita;
    private long datosDescargados;

    public RecursosMision(long energia, long orbita, long descargados) {
        if (energia < 0 || orbita < 0 || descargados < 0)
            throw new IllegalArgumentException("Los recursos no pueden ser negativos.");
        energiaDisponible = energia;
        datosEnOrbita = orbita;
        datosDescargados = descargados;
    }
    public long getEnergiaDisponible() { return energiaDisponible; }
    public long getDatosEnOrbita() { return datosEnOrbita; }
    public long getDatosDescargados() { return datosDescargados; }
    public boolean hayEnergia(long cantidad) { return cantidad >= 0 && energiaDisponible >= cantidad; }

    // Visibilidad de paquete: solo las operaciones del modelo alteran los recursos.
    void generar(long cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Generación no positiva.");
        energiaDisponible = Math.addExact(energiaDisponible, cantidad);
    }
    void recolectar(long consumo, long cantidad) {
        if (consumo <= 0 || cantidad <= 0 || !hayEnergia(consumo))
            throw new IllegalArgumentException("Recolección inválida.");
        long nuevoTotal = Math.addExact(datosEnOrbita, cantidad);
        energiaDisponible -= consumo;
        datosEnOrbita = nuevoTotal;
    }
    void descargar(long consumo, long cantidad) {
        if (consumo <= 0 || cantidad <= 0 || !hayEnergia(consumo) || cantidad > datosEnOrbita)
            throw new IllegalArgumentException("Descarga inválida.");
        long nuevoTotal = Math.addExact(datosDescargados, cantidad);
        energiaDisponible -= consumo;
        datosEnOrbita -= cantidad;
        datosDescargados = nuevoTotal;
    }
    @Override
    public String toString() {
        return "Energía=" + energiaDisponible + " u | En órbita=" + datosEnOrbita
                + " MB | Descargados=" + datosDescargados + " MB";
    }
}
