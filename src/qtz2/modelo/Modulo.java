package qtz2.modelo;

import java.util.Locale;
import java.util.Objects;

/** Identidad y estado comunes. La operación concreta se decide por despacho dinámico. */
public abstract class Modulo {
    private final String id;
    private final String nombre;
    private int salud;
    private boolean activo;
    private final long costoConstruccion;

    protected Modulo(String id, String nombre, int salud, boolean activo, long costo) {
        this.id = textoValido(id).toUpperCase(Locale.ROOT);
        this.nombre = textoValido(nombre);
        if (costo < 0) throw new IllegalArgumentException("Costo negativo.");
        this.costoConstruccion = costo;
        setSalud(salud);
        setActivo(activo);
    }

    protected static String textoValido(String texto) {
        if (texto == null || texto.trim().isEmpty())
            throw new IllegalArgumentException("El texto no puede estar vacío.");
        return texto.trim();
    }

    protected static long positivo(long valor) {
        if (valor <= 0) throw new IllegalArgumentException("La capacidad o consumo debe ser positivo.");
        return valor;
    }

    protected static long noNegativo(long valor) {
        if (valor < 0) throw new IllegalArgumentException("El acumulado no puede ser negativo.");
        return valor;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public int getSalud() { return salud; }
    public boolean isActivo() { return activo; }
    public long getCostoConstruccion() { return costoConstruccion; }

    public void setSalud(int salud) {
        if (salud < 0 || salud > 100) throw new IllegalArgumentException("Salud fuera de 0..100.");
        this.salud = salud;
        if (salud == 0) activo = false;
    }

    public void setActivo(boolean activo) {
        if (activo && salud == 0) throw new IllegalArgumentException("Un módulo sin salud no puede activarse.");
        this.activo = activo;
    }

    public abstract ResultadoOperacion procesarCiclo(RecursosMision recursos);

    @Override
    public String toString() {
        return String.format("%s | %s | salud=%d/100 | activo=%s | costo=%d créditos",
                id, nombre, salud, activo ? "sí" : "no", costoConstruccion);
    }

    /** El ID es único en toda la misión e inmutable. */
    @Override
    public final boolean equals(Object otro) {
        return this == otro || (otro instanceof Modulo && id.equals(((Modulo) otro).id));
    }

    @Override
    public final int hashCode() { return Objects.hash(id); }
}
