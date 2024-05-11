package visitantes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorReporter {

    static class Position {
        int fila;
        int columna;

        public Position(int fila, int columna) {
            this.fila = fila;
            this.columna = columna;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return fila == position.fila && columna == position.columna;
        }

        @Override
        public int hashCode() {
            int result = fila;
            result = 31 * result + columna;
            return result;
        }
    }

    private Map<Position, String> errores = new HashMap<>();

    public void reportarError(int fila, int columna, String mensaje) {
        errores.put(new Position(fila, columna), mensaje);
    }

    public String obtenerError(int fila, int columna) {
        return errores.get(new Position(fila, columna));
    }

    public void imprimirErroresOrdenados() {
        // Paso 1: Extraer las entradas del mapa y almacenarlas en una lista
        List<Map.Entry<Position, String>> listaErrores = new ArrayList<>(errores.entrySet());

        // Paso 2: Ordenar la lista de errores utilizando un comparador personalizado
        Collections.sort(listaErrores, new Comparator<Map.Entry<Position, String>>() {
            @Override
            public int compare(Map.Entry<Position, String> o1, Map.Entry<Position, String> o2) {
                // Primero por fila, luego por columna
                if (o1.getKey().fila != o2.getKey().fila) {
                    return Integer.compare(o1.getKey().fila, o2.getKey().fila);
                } else {
                    return Integer.compare(o1.getKey().columna, o2.getKey().columna);
                }
            }
        });

        // Paso 3: Iterar sobre la lista ordenada e imprimir cada mensaje de error
        for (Map.Entry<Position, String> entry : listaErrores) {
            String mensaje = entry.getValue();
            System.out.println(mensaje);
        }
    }
}
