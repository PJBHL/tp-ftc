package Componentes.CNF;

import java.util.*;

public class teste {

  private static String removerLetra(String str, int posicao) {
    if (posicao < 0 || posicao >= str.length()) {
      return str; // Retorna a string original se a posição for inválida
    }

    return str.substring(0, posicao) + str.substring(posicao + 1);
  }

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

  private static boolean contemDuasOuMaisLetrasMaiusculasConsecutivas(String input) {

    boolean resp = false;

    if (input.length() == 1) {
      return false;
    }

    for (int i = 0; i < input.length() - 1; i++) {
      char charAtual = input.charAt(i);
      char charSeguinte = input.charAt(i + 1);

      if (Character.isUpperCase(charAtual) && Character.isUpperCase(charSeguinte) && charAtual == charSeguinte) {
        resp = true;
      } else {
        // Reinicia a contagem se a condição não for satisfeita
        return false;
      }
    }

    return resp; // Não foram encontradas duas ou mais letras maiúsculas consecutivas iguais
  }

  public static List<String> derivacaoPalavra(String input, char target) {
    List<String> derivacoes = new ArrayList<>();
    List<Integer> posicoesMaiusculas = obterPosicoesLetras(input, target);
    int qntExcluir = posicoesMaiusculas.size() - 1;

    for (Integer posicao : posicoesMaiusculas) {
      for (int i = 0; i < qntExcluir; i++) {
        "".toString();
        List<Integer> copyPosicoes = new ArrayList<>(posicoesMaiusculas);
        copyPosicoes.remove(posicao);
        List<List<Integer>> combinacoes = Combinacoes.gerarCombinacoes(copyPosicoes, i + 1);
        for (List<Integer> integer : combinacoes) {
          String copyInput = new String(input);
          copyInput = excluirLetrasPorPosicao(input, integer);
          if(!derivacoes.contains(copyInput))
            derivacoes.add(copyInput);
        }
      }
    }

    return derivacoes;
  }

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
}
