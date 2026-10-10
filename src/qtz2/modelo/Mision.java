package qtz2.modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Administra una sola lista maestra y mantiene su orden de participación. */
public final class Mision {
    private final List<Modulo> modulos;
    private final RecursosMision recursos;
    private long ciclosTranscurridos;

    public Mision(List<Modulo> modulos, RecursosMision recursos, long ciclos) {
        this.modulos = new ArrayList<>(Objects.requireNonNull(modulos));
        this.recursos = Objects.requireNonNull(recursos);
        if (ciclos < 0) throw new IllegalArgumentException("Ciclo negativo.");
        ciclosTranscurridos = ciclos;
        Set<String> ids = new HashSet<>();
        long recolectado = 0;
        long descargado = 0;
        for (Modulo modulo : this.modulos) {
            Objects.requireNonNull(modulo);
            if (!ids.add(modulo.getId())) throw new IllegalArgumentException("ID duplicado: " + modulo.getId());
            if (modulo instanceof ModuloVuelo) {
                ModuloVuelo vuelo = (ModuloVuelo) modulo;
                if (vuelo.getCiclosAcumulados() > ciclos) throw new IllegalArgumentException("Historial de vuelo inválido.");
                recolectado = Math.addExact(recolectado, vuelo.getTotalRecolectado());
            }
            if (modulo instanceof ModuloEnergia
                    && ((ModuloEnergia) modulo).getCiclosAcumulados() > ciclos)
                throw new IllegalArgumentException("Historial de energía inválido.");
            if (modulo instanceof ModuloTierra)
                descargado = Math.addExact(descargado, ((ModuloTierra) modulo).getTotalDescargado());
        }
        if (descargado != recursos.getDatosDescargados()
                || recolectado != Math.addExact(recursos.getDatosEnOrbita(), descargado))
            throw new IllegalArgumentException("Los históricos no coinciden con los recursos de la misión.");
    }
    public List<Modulo> getModulos() { return List.copyOf(modulos); }
    public RecursosMision getRecursos() { return recursos; }
    public long getCiclosTranscurridos() { return ciclosTranscurridos; }

    public List<Modulo> buscar(String consulta) {
        if (consulta == null || consulta.trim().isEmpty()) return List.of();
        String valor = consulta.trim();
        List<Modulo> coincidencias = new ArrayList<>();
        for (Modulo modulo : modulos)
            if (modulo.getId().equalsIgnoreCase(valor) || modulo.getNombre().equalsIgnoreCase(valor))
                coincidencias.add(modulo);
        return List.copyOf(coincidencias);
    }

    public List<Modulo> catalogoPorCosto() {
        List<Modulo> catalogo = new ArrayList<>(modulos);
        catalogo.sort(Comparator.comparingLong(Modulo::getCostoConstruccion).thenComparing(Modulo::getId));
        return List.copyOf(catalogo);
    }

    public List<ResultadoOperacion> avanzarCiclo() {
        long siguiente = Math.addExact(ciclosTranscurridos, 1);
        List<ResultadoOperacion> resultados = new ArrayList<>();
        // No hay selección por tipo: todos reciben exactamente el mismo mensaje.
        for (Modulo modulo : modulos) resultados.add(modulo.procesarCiclo(recursos));
        ciclosTranscurridos = siguiente;
        return List.copyOf(resultados);
    }

    public ResumenComunicaciones resumenComunicaciones() {
        int total = 0, activos = 0;
        long capacidad = 0, descargado = 0, maximo = -1;
        List<ModuloTierra> lideres = new ArrayList<>();
        for (Modulo modulo : modulos) {
            // Este informe trata solo de estaciones terrestres, no decide su operación.
            if (!(modulo instanceof ModuloTierra)) continue;
            ModuloTierra tierra = (ModuloTierra) modulo;
            total++;
            if (tierra.isActivo()) {
                activos++;
                capacidad = Math.addExact(capacidad, tierra.getCapacidadDescarga());
            }
            long historico = tierra.getTotalDescargado();
            descargado = Math.addExact(descargado, historico);
            if (historico > maximo) {
                maximo = historico;
                lideres.clear();
            }
            if (historico == maximo) lideres.add(tierra);
        }
        return new ResumenComunicaciones(total, activos, capacidad, descargado, lideres);
    }
}
