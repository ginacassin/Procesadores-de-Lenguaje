public class Main {
    /*
    Un programa principal que integre ambos analizadores. Dicho programa recibirá como
    argumento (i) el archivo a analizar; (ii) una opción op que indique el analizador sintáctico a
    aplicar (si op es desc el analizador a aplicar será el descendente; si es asc será el
    ascendente). El programa producirá como salida, bien un mensaje legible del primer error
    (léxico o sintáctico) detectado, bien un mensaje “OK” cuando el programa analizado sea
    sintácticamente correcto.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Uso: java Main <archivo> <desc|asc>");
            System.exit(1);
        }
        String archivo = args[0];
        String op = args[1];
        if (!op.equals("desc") && !op.equals("asc")) {
            System.err.println("Uso: java Main <archivo> <desc|asc>");
            System.exit(1);
        }
        try {
            if (op.equals("desc")) {
                javacc.asint.Main.main(args);
                System.out.println("OK");
            } else {
                cup.asint.Main.main(args);
                System.out.println("OK");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
}
}