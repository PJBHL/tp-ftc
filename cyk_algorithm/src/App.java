import java.util.*;
import Componentes.*;

public class App {
  public static void main(String[] args) throws Exception {

    // Iniciando a gramatica com o path
    Gramatica ut = new Gramatica(args[0]);
    Cyk cyk = new Cyk();
    if(cyk.cykAlgorithm(ut.getGramaticaLida(), "(a"))
      System.out.println("A sentença ()() pertence a gramatica!");
    else
      System.out.println("A sentença ()() não pertence a gramatica");
  }
}
