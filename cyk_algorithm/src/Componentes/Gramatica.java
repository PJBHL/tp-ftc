package Componentes;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class Gramatica {
  public Map<String, List<String>> gramaticaLida;
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
  public Gramatica(String path) throws Exception {

    String pathTratado = this.tratarPath(path);

    gramaticaLida = this.readGrammar(pathTratado);

    // new Cyk(gramaticaLida);


    System.out.println(gramaticaLida);
  }

  public Map<String, List<String>> getGramaticaLida() {
      return gramaticaLida;
  }

/**
 * Metodo para leitura de gramatica de um arquivo txt.
 * @param path - caminho do arquivo de leitura.
 * @return - retorna um dicionario com a gramatica lida.
 * @throws Exception - arquivo não encontrado.
 */
  public Map<String, List<String>> readGrammar(String path) throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(path));
    Map<String, List<String>> gramatica = new LinkedHashMap<>();
    
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

    br.close();
    return gramatica;
  }

  // public void asd(Map<String, List<String>> mapinho) {

  //       mapinho.forEach((key, value)->{
          
  //         System.out.println(key + "=" +  value);
  //       });
    
  // }
}





