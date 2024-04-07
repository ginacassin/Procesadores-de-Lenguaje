package c_ast_descendente;

import java.io.Reader;

public class ConstructorASTsTinyDJ extends ConstructorASTsTiny {
  public ConstructorASTsTinyDJ(Reader r) {
    super(r);
    disable_tracing();
  }
  final protected void trace_token(Token t, String where) {
    switch(t.kind) {
      case 0:
        System.out.println("<EOF>");
        break;
      case 8:
        System.out.println("<and>");
        break;
      case 9:
        System.out.println("<or>");
        break;
      case 10:
        System.out.println("<not>");
        break;
      case 11:
        System.out.println("<true>");
        break;
      case 12:
        System.out.println("<false>");
        break;
      case 13:
        System.out.println("<int>");
        break;
      case 14:
        System.out.println("<real>");
        break;
      case 15:
        System.out.println("<bool>");
        break;
      case 16:
        System.out.println("<string>");
        break;
      case 17:
        System.out.println("<null>");
        break;
      case 18:
        System.out.println("<proc>");
        break;
      case 19:
        System.out.println("<if>");
        break;
      case 20:
        System.out.println("<else>");
        break;
      case 21:
        System.out.println("<while>");
        break;
      case 22:
        System.out.println("<struct>");
        break;
      case 23:
        System.out.println("<new>");
        break;
      case 24:
        System.out.println("<delete>");
        break;
      case 25:
        System.out.println("<read>");
        break;
      case 26:
        System.out.println("<write>");
        break;
      case 27:
        System.out.println("<nl>");
        break;
      case 28:
        System.out.println("<type>");
        break;
      case 29:
        System.out.println("<call>");
        break;
      default:
        System.out.println(t.image);
    }
  }
}