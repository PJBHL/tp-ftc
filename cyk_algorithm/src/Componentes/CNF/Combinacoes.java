package Componentes.CNF;

import java.util.ArrayList;
import java.util.List;

public class Combinacoes {
  public static void main(String[] args) {
    List<Integer> lista = new ArrayList<>();
    lista.add(2);
    lista.add(4);
    lista.add(6);

    List<List<Integer>> combinacoes = gerarCombinacoes(lista, 2);

    "".toString();

    for (List<Integer> combinacao : combinacoes) {
      System.out.println(combinacao);
    }
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