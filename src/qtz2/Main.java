package qtz2;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import qtz2.controlador.ControladorSimulador;
import qtz2.datos.CargaInicial;
import qtz2.vista.VistaConsola;

/** Punto de entrada y ensamblaje de MVC. Requiere JDK 17 o superior. */
public final class Main {
    private Main() { }
    public static void main(String[] args) {
        try (Scanner entrada = new Scanner(System.in, StandardCharsets.UTF_8)) {
            VistaConsola vista = new VistaConsola(entrada, System.out);
            new ControladorSimulador(CargaInicial.crearMision(), vista).ejecutar();
        }
    }
}
