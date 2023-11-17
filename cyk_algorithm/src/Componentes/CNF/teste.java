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

  public static List<String> iniciandoDerivacaoPalavra(String input, char target) {

    List<Integer> opa = obterPosicoesLetras(input, target);

    // for (int i : opa) {
    // System.out.println(i);
    // }

    // BaBaB

    List<String> possibilidades = new ArrayList<>();

    for (Integer posicaoTrava : opa) {

      String copyInput = new String(input);

      for (int index = 0; index < copyInput.length(); index++) {

        if (index != posicaoTrava) {
          char letraAtual = copyInput.charAt(index);

          if (Character.isUpperCase(letraAtual)) {

            String copyInputDerivacao = new String(copyInput);
            copyInputDerivacao = removerLetra(copyInputDerivacao, index);

            if (!possibilidades.contains(copyInputDerivacao) &&
                !contemDuasOuMaisLetrasMaiusculasConsecutivas(copyInputDerivacao) && copyInputDerivacao.length() > 1) {

              possibilidades.add(copyInputDerivacao);
            }

          }

        }

      }

      // Deletar os tudo e atualizar lista de trava
      String copyInputDerivacao = new String(copyInput);
      for (int indexo = 0; indexo < copyInputDerivacao.length(); indexo++) {

        if (indexo != posicaoTrava) {

          char letraAtual = copyInputDerivacao.charAt(indexo);

          if (Character.isUpperCase(letraAtual)) {

            copyInputDerivacao = removerLetra(copyInputDerivacao, indexo);
            "".toString();

            if (indexo < posicaoTrava) {
              posicaoTrava--;
              indexo--;
              "".toString();
            }

          }

        }
      }

      if (!possibilidades.contains(copyInputDerivacao) &&
          !contemDuasOuMaisLetrasMaiusculasConsecutivas(copyInputDerivacao) && copyInputDerivacao.length() > 1) {
        possibilidades.add(copyInputDerivacao);
      }

      // Atualizar trava
      "".toString();

    }

    return possibilidades;

  }

  // public static void main(String[] args) {
  // String input = "AaAaAaA";
  // char target = 'A';

  // List<String> resp = new ArrayList<>();

  // resp = iniciandoDerivacaoPalavra(input, target);

  // "".toString();

  // }

}
