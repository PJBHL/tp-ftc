import Componentes.*;

public class App {
  public static void main(String[] args) throws Exception {
    Gramatica ut = new Gramatica(args[0]);
    System.out.println("\nGramatica lida: \n");
    Gramatica.imprimirGramatica(ut.getGramaticaLida());
    Chomsky chomchom = new Chomsky(ut);
  }
}