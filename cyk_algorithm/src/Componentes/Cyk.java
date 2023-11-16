package Componentes;

import java.util.*;
import java.util.regex.Pattern;

/**
 * G:
 * S -> NP VP
 * VP -> VP PP
 * VP -> V NP
 * VP -> come
 * PP -> P NP
 * NP -> Det N
 * NP -> ela
 * V -> come
 * P -> com
 * N -> peixe
 * N -> garfo
 * Det -> um
 * 
 * w: ela come um peixe com um garfo
 */

/**
 * input: a CFG G = (N, Σ, S, ->) in CNF, a word w = a1 . . . an ∈Σ+
 * CYK(G,w) =
 * for i = 1, . . . , n do
 * Ti,i := {A ∈N |A -> ai}
 * for j = 2, . . . , n do
 * for i = j - 1, . . . , 1 do
 * Ti,j := ∅;
 * for h = i, . . . , j - 1 do
 * for all A -> BC
 * if B ∈Ti,h and C ∈Th+1,j then
 * Ti,j := Ti,j ∪ {A}
 * 
 * if S ∈T1,n then return yes else return no
 */

public class Cyk {

  /**
   * Método para excluir nao terminais da lista antes de ser testado
   * 
   * @param listadevalores
   * @return lista de valores somente com terminais
   */
  private List<String> removerNaoterminal(List<String> listadevalores) {
    Iterator<String> iterator = listadevalores.iterator();
    while (iterator.hasNext()) {
      String palavra = iterator.next();
      if (Pattern.matches("^[A-Z0-9]*$", palavra)) {
        iterator.remove();
      }
    }
    return listadevalores;
  }

  /**
   * Método para verificar se uma sentença está
   * 
   * @param gramatica
   * @param word
   * @return
   */
  // 1 caso
  // S -> NP | VP
  // NP -> batata
  // VP -> vini
  // 2 caso
  // vini batata
  // 3 caso
  // vini batata
  public boolean isInAlphabet(Map<String, List<String>> gramatica, String word) {

    // Criando lista de string char
    List<String> listaDeStrings = new ArrayList<>();
    for (Character character : word.toCharArray()) {

      if (character != ' ') {
        listaDeStrings.add(character.toString());
      }
    }

    // Caso for somente uma palavra
    for (Map.Entry<String, List<String>> each : gramatica.entrySet()) {
      List<String> value = each.getValue();
      value = this.removerNaoterminal(value);
      // Se a lista for somente de terminal vai dar 0, entao ignorar este caso
      if (value.size() != 0) {
        value.forEach(ite -> {
          listaDeStrings.removeIf(elemento -> ite.equals(elemento));
        });
      }

      if (listaDeStrings.size() == 0) {
        return true;
      }
    }

    return false;
  }

  /**
   * Método do algoritmo original de CYK, para verificar se deteminada
   * palavra pertence a uma gramatica.
   * 
   * @param gramatica - Gramatica no formato de Chomsky.
   * @param word      - Palavra ou sentença para verificação.
   * @return - true se word pertence a gramatica e false caso contrário.
   */
  /**
   * Output:
   * A saída do algoritmo é uma tabela T contendo, para cada subpalavra
   * v de w, o set de não terminais que derivão de v, isto é, propriedades
   * sintaticas.
   * Em resumo T, nos diz se w é uma sentença de G ou não.
   */
  @SuppressWarnings("unchecked")
  public boolean cykAlgorithm(Map<String, List<String>> gramatica, String word) {
    int n = word.length();

    // Tabela T com armazenamento de resultados.
    List<String>[][] tabela = new List[n][n];

    // Inicializar tabela.
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        tabela[i][j] = new ArrayList<>();

    // Tabela sendo preenchida com não-terminais que geram terminais.
    for (int i = 0; i < n; i++) {
      char terminal = word.charAt(i);
      for (Map.Entry<String, List<String>> j : gramatica.entrySet()) {
        String naoTerminal = j.getKey();
        List<String> regras = j.getValue();
        for (String regra : regras) {
          if (regra.length() == 1 && regra.charAt(0) == terminal)
            tabela[i][i].add(naoTerminal);
        }
      }
    }

    // Tabela sendo preenchida com regras da gramática.
    for (int j = 2; j <= n; j++) {
      for (int i = 0; i <= n - j; i++) {
        int k = i + j - 1;
        for (int z = i; z < k; z++) {
          for (Map.Entry<String, List<String>> entry : gramatica.entrySet()) {
            String naoTerminal = entry.getKey();
            List<String> regras = entry.getValue();
            for (String regra : regras) {
              if (regra.length() == 2) {
                char simbolo1 = regra.charAt(0);
                char simbolo2 = regra.charAt(1);
                if (tabela[i][z].contains(String.valueOf(simbolo1)) &&
                    tabela[z + 1][k].contains(String.valueOf(simbolo2))) {
                  tabela[i][k].add(naoTerminal);
                }
              }
            }
          }
        }
      }
    }

    return tabela[0][n - 1].contains("S");
  }
}
