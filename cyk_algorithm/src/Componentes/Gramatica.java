package Componentes;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

// Exemplo de leitura gramatica
// [0] -> [0][1][2][3]

public class Gramatica {
  private List<String> leituraGramatica = new ArrayList<>();

  public Gramatica(String path) {
    File arq = new File(path);
    String caminhoTotal = arq.getAbsolutePath();  

    // String[] splitado = caminhoTotal.split("/");
    // List<String> listona = new ArrayList<>(Arrays.asList(splitado));
    

    // LOBATO: C:\Users\joaopc\Documents\tp-ftc\cyk_algorithm\src\Gramatica\g1.txt

    leituraGramatica = readGrammar(caminhoTotal);
    System.out.println("LOBATO: " + caminhoTotal);

  }

  public List<String> readGrammar(String path) {

    List<String> grammarLines = new ArrayList<>();
    try {

      BufferedReader br = new BufferedReader(new FileReader(path));
      String line;

      while ((line = br.readLine()) != null) {
        grammarLines.add(line);
      }

      br.close();

    } catch (IOException e) {
      System.out.println("Erro na leitura E: " + e.getMessage());

    }

    return grammarLines;

  }
}
