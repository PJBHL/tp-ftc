import java.util.*;
import Componentes.*;

public class App {
  public static void main(String[] args) throws Exception {

    // Iniciando a gramatica com o path
    Gramatica ut = new Gramatica(args[0]);
    Cyk cyk = new Cyk();
    // if(cyk.cykAlgorithm(ut.getGramaticaLida(), "(a"))
    // System.out.println("A sentença ()() pertence a gramatica!");
    // else
    // System.out.println("A sentença ()() não pertence a gramatica");
    if (cyk.isInAlphabet(ut.getGramaticaLida(), "ab a b a b a b c a"))
      System.out.println("A sentença pertence ao alfabeto");
    else
      System.out.println("A sentença nao pertence ao alfabeto");
  }
}
