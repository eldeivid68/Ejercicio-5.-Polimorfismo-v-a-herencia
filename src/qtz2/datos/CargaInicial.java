package qtz2.datos;

import java.util.List;
import qtz2.modelo.*;

/** Misión después de cuatro ciclos. Las capacidades son constantes. */
public final class CargaInicial {
    private CargaInicial() { }
    public static Mision crearMision() {
        List<Modulo> modulos = List.of(
            new ModuloEnergia("E01", "Panel Aurora", 100, true, 120, 30, 4),
            new ModuloVuelo("V01", "Cámara Quetzal", 95, true, 250, TipoInstrumento.CAMARA, 15, 8, 4),
            new ModuloTierra("T01", "Antena Central", 100, true, 200, "Campus Central", 10, 5, 40),
            new ModuloEnergia("E02", "Panel Amanecer", 90, true, 90, 15, 4),
            new ModuloVuelo("V02", "Sensor Térmico", 90, true, 180, TipoInstrumento.SENSOR, 10, 6, 4),
            new ModuloTierra("T02", "Antena Altiplano", 85, true, 160, "Campus Altiplano", 10, 4, 40),
            new ModuloEnergia("E03", "Panel Reserva", 65, false, 60, 8, 2),
            new ModuloVuelo("V03", "Sensor Radiación", 80, true, 140, TipoInstrumento.SENSOR, 5, 3, 4),
            new ModuloTierra("T03", "Antena Sur", 70, false, 100, "Campus Sur", 5, 2, 10),
            new ModuloVuelo("V04", "Cámara Reserva", 75, false, 130, TipoInstrumento.CAMARA, 6, 2, 2)
        );
        // 132 MB recolectados = 42 en órbita + 90 descargados.
        // 196 u generadas - 72 consumidas por vuelo - 40 por tierra = 84 u.
        return new Mision(modulos, new RecursosMision(84, 42, 90), 4);
    }
}
