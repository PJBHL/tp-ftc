import Componentes.*;
import Componentes.CNF.chom;

public class App {
  public static void main(String[] args) throws Exception {
    Gramatica ut = new Gramatica(args[0]);
    Chomsky chomchom = new Chomsky(ut);
  }
}