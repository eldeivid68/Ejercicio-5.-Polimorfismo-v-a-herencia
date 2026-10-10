package qtz2.modelo;

import java.util.Objects;

public final class ModuloVuelo extends Modulo {
    private final TipoInstrumento tipoInstrumento;
    private final long datosPorCiclo;
    private final long consumoEnergia;
    private long ciclosAcumulados;

    public ModuloVuelo(String id, String nombre, int salud, boolean activo, long costo,
            TipoInstrumento tipo, long datos, long consumo, long ciclos) {
        super(id, nombre, salud, activo, costo);
        tipoInstrumento = Objects.requireNonNull(tipo, "Instrumento requerido.");
        datosPorCiclo = positivo(datos);
        consumoEnergia = positivo(consumo);
        ciclosAcumulados = noNegativo(ciclos);
        Math.multiplyExact(datosPorCiclo, ciclosAcumulados);
    }
    public TipoInstrumento getTipoInstrumento() { return tipoInstrumento; }
    public long getDatosPorCiclo() { return datosPorCiclo; }
    public long getConsumoEnergia() { return consumoEnergia; }
    public long getCiclosAcumulados() { return ciclosAcumulados; }
    public long getTotalRecolectado() { return Math.multiplyExact(datosPorCiclo, ciclosAcumulados); }

    @Override
    public ResultadoOperacion procesarCiclo(RecursosMision recursos) {
        if (!isActivo()) return new ResultadoOperacion(getId(), false, "Módulo inactivo.");
        if (!recursos.hayEnergia(consumoEnergia))
            return new ResultadoOperacion(getId(), false, "Energía insuficiente: requiere " + consumoEnergia + " u.");
        long siguientes = Math.addExact(ciclosAcumulados, 1);
        Math.multiplyExact(datosPorCiclo, siguientes);
        recursos.recolectar(consumoEnergia, datosPorCiclo);
        ciclosAcumulados = siguientes;
        return new ResultadoOperacion(getId(), true, "Recolectó " + datosPorCiclo
                + " MB y consumió " + consumoEnergia + " u.");
    }
    @Override
    public String toString() {
        return super.toString() + " | VUELO | instrumento=" + tipoInstrumento
                + " | datos/ciclo=" + datosPorCiclo + " MB | consumo=" + consumoEnergia
                + " u | ciclos operados=" + ciclosAcumulados + " | recolectado histórico=" + getTotalRecolectado() + " MB";
    }
}
