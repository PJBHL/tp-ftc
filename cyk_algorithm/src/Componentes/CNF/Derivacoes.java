package Componentes.CNF;

import java.util.*;

/**
 * Classe para fazer as derivações da palavra para utilizar na remoção do lambda.
 */
public class Derivacoes {

  /**
   * Método para obter a posição dos Não Terminais (Letras Maiusculas) na regra.
   * @param str - String para obter posições.
   * @param target - Caractere que iremos obter as posições.
   * @return
   */
  private static List<Integer> obterPosicoesLetras(String str, char target) {
    List<Integer> posicoes = new ArrayList<>();

    for (int i = 0; i < str.length(); i++) {
      char letraAtual = str.charAt(i);

      if (letraAtual == target) {
        posicoes.add(i);
      }
    }

    return posicoes;
  }

  /**
   * Método para fazer a derivação da string a partir de um caractere.
   * @param input - String de entrada.
   * @param target - Carectere de derivação.
   * @return - return de uma lista de derivações.
   */
  public static List<String> derivacaoPalavra(String input, char target) {
    List<String> derivacoes = new ArrayList<>();
    List<Integer> posicoesMaiusculas = obterPosicoesLetras(input, target);
    int qntExcluir = posicoesMaiusculas.size() - 1;

    for (Integer posicao : posicoesMaiusculas) {
      for (int i = 0; i < qntExcluir; i++) {

        List<Integer> copyPosicoes = new ArrayList<>(posicoesMaiusculas);
        copyPosicoes.remove(posicao);

        List<List<Integer>> combinacoes = gerarCombinacoes(copyPosicoes, i + 1);
        
        for (List<Integer> integer : combinacoes) {
          String copyInput = new String(input);
          copyInput = excluirLetrasPorPosicao(input, integer);

          if(!derivacoes.contains(copyInput))
            derivacoes.add(copyInput);
        }
      }
    }

    derivacoes.add(input.replace(String.valueOf(target), ""));

    return derivacoes;
  }

  /**
   * Método para excluir as letras de determinado input dado um vetor de posições.
   * @param input - string para excluir as posições.
   * @param posicoesExcluir - vetor de posições a serem excluídas.
   * @return - string resultante.
   */
  private static String excluirLetrasPorPosicao(String input, List<Integer> posicoesExcluir) {
    StringBuilder resultado = new StringBuilder(input);

    // Ordena as posições em ordem decrescente para evitar problemas com
    // deslocamento após exclusões
    posicoesExcluir.sort((a, b) -> b.compareTo(a));

    for (int posicao : posicoesExcluir) {
      if (posicao >= 0 && posicao < resultado.length()) {
        resultado.deleteCharAt(posicao);
      }
    }

    return resultado.toString();
  }

  public static List<List<Integer>> gerarCombinacoes(List<Integer> lista, int tamanho) {
    List<List<Integer>> combinacoes = new ArrayList<>();

    gerarCombinacoesRecursivas(combinacoes, new ArrayList<>(), lista, tamanho, 0);
    return combinacoes;
  }

  private static void gerarCombinacoesRecursivas(List<List<Integer>> combinacoes, List<Integer> combinacaoAtual,
      List<Integer> lista, int tamanho, int indice) {
    if (combinacaoAtual.size() == tamanho) {
      combinacoes.add(new ArrayList<>(combinacaoAtual));
      return;
    }

    for (int i = indice; i < lista.size(); i++) {
      combinacaoAtual.add(lista.get(i));
      gerarCombinacoesRecursivas(combinacoes, combinacaoAtual, lista, tamanho, i + 1);
      combinacaoAtual.remove(combinacaoAtual.size() - 1);
    }
  }
}
