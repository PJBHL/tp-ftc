package Componentes;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class Gramatica {
  private Map<String, List<String>> leituraGramatica;

  public Gramatica(String path) throws Exception {
    // File arq = new File(path);
    // String caminhoTotal = arq.getAbsolutePath();  

    // // String[] splitado = caminhoTotal.split("/");
    // // List<String> listona = new ArrayList<>(Arrays.asList(splitado));
    

    // // LOBATO: C:\Users\joaopc\Documents\tp-ftc\cyk_algorithm\src\Gramatica\g1.txt

    leituraGramatica = readGrammar(path);
    // System.out.println("LOBATO: " + caminhoTotal);

    

  }


  // Exemplo de leitura gramatica
  // [0] -> [0][1][2][3]
  // [1] -> [0][1][2][3]




  // public List<List<String>> readGrammar(String path) throws Exception {
  //   List<List<String>> gramatica = new ArrayList<>();
  //   BufferedReader br = new BufferedReader(new FileReader(path));

  //   String line;

  //   while((line = br.readLine()) != null) {
  //     line = line.trim();

  //     if(!line.isEmpty()) {
  //       String[] delimitador = line.split("->");
  //       String naoTerminal = delimitador[0].trim();
  //       String[] regras = delimitador[1].trim().split("\\|");

  //       List<String> regrasList = new ArrayList<>();
  //       regrasList.add(naoTerminal);
  //       for(String regra : regras)
  //         regrasList.add(regra.trim());

  //       gramatica.add(regrasList);
  //     }
  //   }

  //   printGramatica(gramatica);
  //   br.close();
  //   return gramatica;
  // }

  // public void printGramatica(List<List<String>> gramatica) {
  //   for (int i = 0; i < gramatica.size(); i++) {
  //       System.out.print("[" + i + "] " + gramatica.get(i).get(0) + ": ");
  //       for (int j = 1; j < gramatica.get(i).size(); j++) {
  //           System.out.print(gramatica.get(i).get(j));
  //           if (j < gramatica.get(i).size() - 1) {
  //               System.out.print(", ");
  //           }
  //       }
  //       System.out.println();
  //   }
  // }


  public Map<String, List<String>> readGrammar(String path) throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(path));
    Map<String, List<String>> gramatica = new HashMap<>();
    
    String line;

    while((line = br.readLine()) != null) {
      String[] delimitador = line.split(" -> ");
      String naoTerminal = delimitador[0].trim();
      String[] regras = delimitador[1].trim().split("\\|");

      List<String> regrasList = new ArrayList<>();
      for(String regra : regras)
        regrasList.add(regra.trim());

      gramatica.put(naoTerminal, regrasList);
      

    }

    System.out.println(gramatica);

    br.close();
    return gramatica;
  }
}

