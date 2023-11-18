package Componentes.CNF;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// BaBa 
// Baa
// aBa
// aa

// codigo
// -> BaBaB
// BaaB
// BaBa
// Baa
// aBaB
// aBa
// aaB

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
        for (Integer posi : posicoesMaiusculas) {
          System.out.println("Trava no " + posicoesMaiusculas.get(posicao) + ": excluir = " + (i + 1) + ".");
          String copyInput = new String(input);
          if (posicao != posi) {
            List<Integer> temp = new ArrayList<>(posicoesMaiusculas);
            temp.remove(posicao);
            List<String> tempDerivacoes;
            copyInput = excluirLetrasPorPosicao(copyInput, temp);
            derivacoes.add(copyInput);
            System.out.println("Derivacao adicionada: " + copyInput);
          }
        }
      }
    }

    return derivacoes;
  }

  // public static String excluirLetras(String input, int qnt, int[] posicoes) {
  //   // Inicializa um StringBuilder para construir a nova string
  //   StringBuilder resultado = new StringBuilder();

  //   // Percorre a string original
  //   for (int i = 0; i < input.length(); i++) {
  //     // Verifica se a letra atual é maiúscula e não está na posição travada
  //     if (Character.isUpperCase(input.charAt(i))) {
  //       // Verifica se ainda há letras maiúsculas a serem removidas
  //       if (qnt > 0) {
  //         qnt--;
  //       } else {
  //         // Adiciona a letra ao resultado se não precisar ser removida
  //         resultado.append(input.charAt(i));
  //       }
  //     } else {
  //       // Adiciona a letra ao resultado se não for maiúscula ou estiver na posição
  //       // travada
  //       resultado.append(input.charAt(i));
  //     }
  //   }

  //   // Converte o StringBuilder de volta para uma string
  //   return resultado.toString();
  // }

  // private static boolean isPosicaoTravada(int posicao, int[] posicoesTravadas) {
  //   for(int i : posicoesTravadas) {
  //     if(posicao == i)
  //       return true;
  //   }

  //   return false;
  // }

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

  public static void main(String[] args) {
    // String input = "AaAaAaA";
    // char target = 'A';
    // // System.out.println(excluirLetras(input, 2, 2));

    // List<String> resp = new ArrayList<>();

    // resp = derivacaoPalavra(input, target);

    // System.out.println(resp);

    // "".toString();
  }

}
