package Componentes;

import java.io.*;
import java.util.*;

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

    System.out.println(gramaticaLida);
  }

  public Map<String, List<String>> getGramaticaLida() {
    return gramaticaLida;
  }

  /**
   * Metodo para leitura de gramatica de um arquivo txt.
   * 
   * @param path - caminho do arquivo de leitura.
   * @return - retorna um dicionario com a gramatica lida.
   * @throws Exception - arquivo não encontrado.
   */
  public Map<String, List<String>> readGrammar(String path) throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(path));
    Map<String, List<String>> gramatica = new LinkedHashMap<>();

    String line;

    while ((line = br.readLine()) != null) {
      String[] delimitador = line.split(" -> ");
      String naoTerminal = delimitador[0].trim();
      String[] regras = delimitador[1].trim().split("\\|");

      // verificar se ja existe o index
      if (gramatica.containsKey(naoTerminal)) {
        List<String> regrasList = gramatica.get(naoTerminal);
        for (String regra : regras)
          regrasList.add(regra.trim());
        gramatica.put(naoTerminal, regrasList);
        // testar sem o index
      } else {
        List<String> regrasList = new ArrayList<>();
        for (String regra : regras)
          regrasList.add(regra.trim());

        gramatica.put(naoTerminal, regrasList);

      }

    }

    br.close();
    return gramatica;
  }

  public static void imprimirGramatica(Map<String, List<String>> gramatica) {
    Set<String> naoTerminaisImprimidos = new HashSet<>();

    for (Map.Entry<String, List<String>> entry : gramatica.entrySet()) {
      String variavel = entry.getKey();
      List<String> regras = entry.getValue();

      if (!naoTerminaisImprimidos.contains(variavel)) {
        System.out.print(variavel + " -> ");
        imprimirConjuntoDeRegras(regras);
        naoTerminaisImprimidos.add(variavel);
      }
    }
  }

  private static void imprimirConjuntoDeRegras(List<String> regras) {
    System.out.print("{");
    for (int i = 0; i < regras.size(); i++) {
      System.out.print(regras.get(i));
      if (i < regras.size() - 1) {
        System.out.print(" | ");
      }
    }
    System.out.println("}");
  }

  public static Map<String, List<String>> clonarGramatica(Map<String, List<String>> target) {
    Map<String, List<String>> clone = new LinkedHashMap<>();

    for (Map.Entry<String, List<String>> entry : target.entrySet()) {
      List<String> originalLista = entry.getValue();
      List<String> copiaLista = new ArrayList<>(originalLista);
      clone.put(entry.getKey(), copiaLista);
    }

    return clone;
  }

  public static List<String> pegarNaoTerminais(Map<String, List<String>> glc) {
    List<String> naoTerminais = new ArrayList<>();
    for (Map.Entry<String, List<String>> entry : glc.entrySet()) {
      naoTerminais.add(entry.getKey());
    }

    return naoTerminais;
  }

  public static List<String> pegarTerminais(Map<String, List<String>> glc) {
    List<String> terminais = new ArrayList<>();
    for (Map.Entry<String, List<String>> entry : glc.entrySet()) {
      List<String> regras = entry.getValue();
      List<String> regrasCopy = new ArrayList<>(regras);

      for (String regra : regrasCopy) {
        regra = regra.replaceAll("[A-Z]", "");
        for(int i = 0; i < regra.length(); i++) {
          char caractere = regra.charAt(i);

          if(!terminais.contains(String.valueOf(caractere))) {
            terminais.add(String.valueOf(caractere));
          }
        }
      }
    }
    
    return terminais;
  }
}
