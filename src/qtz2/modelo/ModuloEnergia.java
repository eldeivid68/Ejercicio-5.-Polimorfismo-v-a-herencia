package qtz2.modelo;

public final class ModuloEnergia extends Modulo {
    private final long energiaPorCiclo;
    private long ciclosAcumulados;

    public ModuloEnergia(String id, String nombre, int salud, boolean activo, long costo,
            long energia, long ciclos) {
        super(id, nombre, salud, activo, costo);
        energiaPorCiclo = positivo(energia);
        ciclosAcumulados = noNegativo(ciclos);
        Math.multiplyExact(energiaPorCiclo, ciclosAcumulados);
    }
    public long getEnergiaPorCiclo() { return energiaPorCiclo; }
    public long getCiclosAcumulados() { return ciclosAcumulados; }
    public long getTotalGenerado() { return Math.multiplyExact(energiaPorCiclo, ciclosAcumulados); }

    @Override
    public ResultadoOperacion procesarCiclo(RecursosMision recursos) {
        if (!isActivo()) return new ResultadoOperacion(getId(), false, "Módulo inactivo.");
        long siguientes = Math.addExact(ciclosAcumulados, 1);
        Math.multiplyExact(energiaPorCiclo, siguientes);
        recursos.generar(energiaPorCiclo);
        ciclosAcumulados = siguientes;
        return new ResultadoOperacion(getId(), true, "Generó " + energiaPorCiclo + " u de energía.");
    }
    @Override
    public String toString() {
        return super.toString() + " | ENERGÍA | generación/ciclo=" + energiaPorCiclo
                + " u | ciclos operados=" + ciclosAcumulados + " | generado histórico=" + getTotalGenerado() + " u";
    }
}
