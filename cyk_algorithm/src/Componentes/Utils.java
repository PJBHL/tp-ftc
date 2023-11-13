package Componentes;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;

// [0] -> [0][1][2][3]

public class Utils {

  public Utils() {

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
