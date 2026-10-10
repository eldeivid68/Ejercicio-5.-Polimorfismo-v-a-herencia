package qtz2.modelo;

/** Resultado inmutable; el modelo no escribe en la consola. */
public final class ResultadoOperacion {
    private final String idModulo;
    private final boolean realizada;
    private final String detalle;

    public ResultadoOperacion(String idModulo, boolean realizada, String detalle) {
        this.idModulo = idModulo;
        this.realizada = realizada;
        this.detalle = detalle;
    }
    public String getIdModulo() { return idModulo; }
    public boolean isRealizada() { return realizada; }
    public String getDetalle() { return detalle; }
    @Override
    public String toString() {
        return idModulo + (realizada ? " [OK] " : " [SIN OPERAR] ") + detalle;
    }
}
