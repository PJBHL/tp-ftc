import Componentes.*;

public class App {
  public static void main(String[] args) throws Exception {
    Gramatica glc = new Gramatica(args[0]);
    Forma2NF nf = new Forma2NF(glc);
  }
}