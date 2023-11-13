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

  /**
   * Este método trata o path.
   * 
   * @param path Caminho do arquivo de texto.
   * @return Path tratado com o caminho total do arquivo.
   */

  private String tratarPath(String path) {

    File arq = new File(path);
    String caminhoTotal = arq.getAbsolutePath();
    String array[] = caminhoTotal.split("\\\\");
    List<String> list = new ArrayList<>(Arrays.asList(array));
    var arqName = list.get(list.size() - 1);
    var folder1 = list.get(list.size() - 2);
    list.remove(list.size() - 1);
    list.remove(list.size() - 1);
    list.add("src");
    list.add(folder1);
    list.add(arqName);

    String pathFinal = "";

    for (String each : list) {
      pathFinal += each + "/";
    }

    if (pathFinal != "") {
      return pathFinal;
    } else {
      return "Path nao encontrado";
    }

  }

  /**
   * Este método CONSTRUTOR.
   * 
   * @param path Caminho do arquivo de texto.
   */
  public Gramatica(String path) {

    String pathTratado = this.tratarPath(path);

    List<String> gramaticaLida = this.readGrammar(pathTratado);

    System.out.println(gramaticaLida);

  }

  /**
   * Este método faz a leiturado do arquivo texto.
   * 
   * @param path Caminho do arquivo de texto.
   * @return Lista com todas as regras da gramatica.
   */

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
