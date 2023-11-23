package Componentes.CNF;

import java.util.*;

import Componentes.Gramatica;

/**
 * Classe para o segundo passo da gramática de chomsky:
 * Remover Transições Unitárias. Exemplo:
 * 
 * Suponha que uma gramática recebida do primeiro passo (Remoção de Transições
 * Vazias), esteja da seguinte forma:
 * 
 * S -> AB | B | SCB | SB
 * A -> aA | a | C
 * B -> bB | b
 * C -> cC | c
 * 
 * As transições unitárias (S -> B, A -> C, etc) devem ser transformadas.
 * Fecho de variáveis unitárias:
 * S = {B}
 * A = {C}
 * B = {}
 * C = {}
 * 
 * Resultando em:
 * 
 * S -> AB | bB | b | SCB | SB
 * A -> aA | a | cC | c
 * B -> bB | b
 * C -> cC | c
 */
public class RemoverTransicoesUnitarias {
  public static Map<String, List<String>> removerUnitarios(Map<String, List<String>> glc) {
    // Map<String, List<String>> glcCopy = removerRegrasInuteis(glc);
    Map<String, List<String>> glcCopy = Gramatica.clonarGramatica(glc);
    List<String> transicoesUnitarias = pegarTransicoesUnitarias(glcCopy);
    while (!transicoesUnitarias.isEmpty()) {
      glcCopy = removendoTransicoesUnitarias(transicoesUnitarias, glcCopy);
      transicoesUnitarias = pegarTransicoesUnitarias(glcCopy);
    }
    System.out.println("\n\nRemoverUnitarios");
    Gramatica.imprimirGramatica(glcCopy);
    glcCopy = removerRegraQueGeramElasMesmas(glcCopy);
    System.out.println("\n\nremoverRegraQueGeramElasMesmas");
    Gramatica.imprimirGramatica(glcCopy);
    glcCopy = removerRegrasSoltas(glcCopy);
    System.out.println("\n\nremoverRegrasSoltas");
    Gramatica.imprimirGramatica(glcCopy);
    glcCopy = removerRegraRepetida(glcCopy);
    System.out.println("\n\nremoverRegraRepetida");
    Gramatica.imprimirGramatica(glcCopy);
    glcCopy = removerLoop(glcCopy);
    System.out.println("\n\nremoverLoop");

    return glcCopy;
  }

  public static Map<String, List<String>> trocarElementosGLC(Map<String, List<String>> glc, String remover,
      String trocar) {

    for (Map.Entry<String, List<String>> each : glc.entrySet()) {
      List<String> regras = each.getValue();

      for (String each_regra : regras) {

        if (each_regra.contains(remover)) {
          String replaca = each_regra.replace(remover, trocar);
          int indice = regras.indexOf(each_regra);
          regras.set(indice, replaca);
          "".toString();
        }
      }

    }

    return glc;

  }

  // /**
  // * Remoção possíveis regras inúteis da gramática, geradas na derivação.
  // Exemplo:
  // * S -> A | S
  // * (S gerando ele mesmo).
  // *
  // * @param glc - gramática para remover as regras.
  // * @return - nova gramática com regras inúteis removidas.
  // */
  // public static Map<String, List<String>> removerRegrasInuteis(Map<String,
  // List<String>> glc) {
  // Map<String, List<String>> glcCopy = Gramatica.clonarGramatica(glc);

  // return glcCopy;

  // }

  public static Map<String, List<String>> removerRegrasSoltas(Map<String, List<String>> glc) {
    Map<String, List<String>> glcCopy = Gramatica.clonarGramatica(glc);

    List<String> nao_terminais = new ArrayList<>();
    nao_terminais.add("S");

    for (Map.Entry<String, List<String>> each : glc.entrySet()) {
      // String naoTerminal = each.getKey();
      List<String> regras = each.getValue();

      for (String each_regra : regras) {

        String regra_only_maisc = each_regra.replaceAll("[a-z]", "");

        for (int i = 0; i < regra_only_maisc.length(); i++) {

          String letra = String.valueOf(regra_only_maisc.charAt(i));

          if (!nao_terminais.contains(letra)) {
            nao_terminais.add(letra);
          }

        }
      }
    }

    for (Map.Entry<String, List<String>> each : glc.entrySet()) {
      String naoTerminal = each.getKey();

      if (!nao_terminais.contains(naoTerminal)) {
        glcCopy.remove(naoTerminal);
      }

    }

    return glcCopy;
  }

  public static Map<String, List<String>> removerRegraQueGeramElasMesmas(Map<String, List<String>> glc) {

    Map<String, List<String>> glcCopy = Gramatica.clonarGramatica(glc);

    // Removendo Regras que geram elas mesmas.
    for (Map.Entry<String, List<String>> each : glc.entrySet()) {
      String naoTerminal = each.getKey();
      List<String> regras = each.getValue();

      if (regras.contains(naoTerminal))
        glcCopy.get(naoTerminal).remove(naoTerminal);
    }

    return glcCopy;
  }

  public static Map<String, List<String>> removerRegraRepetida(Map<String, List<String>> glcCopy) {
    Iterator<Map.Entry<String, List<String>>> iterator = glcCopy.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, List<String>> entry = iterator.next();
      String naoTerminal = entry.getKey();
      List<String> regras = entry.getValue();

      Iterator<Map.Entry<String, List<String>>> iterator2 = glcCopy.entrySet().iterator();
      while (iterator2.hasNext()) {
        Map.Entry<String, List<String>> entry2 = iterator2.next();
        List<String> regras2 = entry2.getValue();
        String naoTerminal_dois = entry2.getKey();

        if (regras.equals(regras2) && !naoTerminal.equals(naoTerminal_dois)) {
          iterator.remove();
          glcCopy = trocarElementosGLC(glcCopy, naoTerminal, naoTerminal_dois);
        }
      }
    }

    return glcCopy;
  }

  public static Map<String, List<String>> removerLoop(Map<String, List<String>> glcCopy) {
    Iterator<Map.Entry<String, List<String>>> it = glcCopy.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, List<String>> entry = it.next();
      String naoTerminal = entry.getKey();
      List<String> regras = entry.getValue();

      boolean isValidToExclu = false;

      for (String kd_regra : regras) {

        if (kd_regra.contains(naoTerminal)) {
          isValidToExclu = true;
        } else {
          isValidToExclu = false;
          break;
        }
      }

      if (isValidToExclu) {
        glcCopy.remove(naoTerminal);

        Iterator<Map.Entry<String, List<String>>> it3 = glcCopy.entrySet().iterator();
        while (it3.hasNext()) {
          Map.Entry<String, List<String>> entry3 = it3.next();
          List<String> regras3 = entry3.getValue();

          List<String> cloneList = new ArrayList<>(regras3);
          for (String each_regra : cloneList) {
            if (each_regra.contains(naoTerminal)) {
              regras3.remove(each_regra);
            }
          }

        }
      }

    }

    return glcCopy;
  }

  /**
   * Método para percorrer a gramática e pegar quais não terminais possuem
   * transições vazias.
   * 
   * @param gramatica - gramática.
   * @return - lista de string com variáveis que possuem transição vazia.
   */
  public static List<String> pegarTransicoesUnitarias(Map<String, List<String>> gramatica) {
    List<String> transicoesUnitarias = new ArrayList<>();

    for (Map.Entry<String, List<String>> each : gramatica.entrySet()) {
      // String naoTerminal = each.getKey();
      List<String> regras = each.getValue();

      for (String regra : regras) {
        if (regra.length() == 1 && Character.isUpperCase(regra.charAt(0))) {
          transicoesUnitarias.add(regra);
        }
      }
    }

    return transicoesUnitarias;
  }

  /**
   * Método para pegar as regras de um não terminal que possui transição unitária
   * em algum lugar da gramática.
   * Exemplo:
   * S -> A
   * A -> aA | A
   * return será as regras de A, ou seja, [aA, A].
   * 
   * @param naoTerminal - não terminal para recolher as regras.
   * @param gramatica   - gramática.
   */
  public static List<String> pegarRegras(String naoTerminal, Map<String, List<String>> gramatica) {
    List<String> regrasCopy = new ArrayList<>();

    regrasCopy = gramatica.get(naoTerminal);

    return regrasCopy;
  }

  /**
   * Método para efetivamente remover as transições unitárias gramática.
   * 
   * @param transicoesUnitarias - Lista com não terminais que possuem transição
   *                            unitária.
   * @param gramatica           - gramática.
   * @return - nova gramática sem transições unitárias.
   */
  public static Map<String, List<String>> removendoTransicoesUnitarias(List<String> transicoesUnitarias,
      Map<String, List<String>> gramatica) {
    Map<String, List<String>> novaGramatica = Gramatica.clonarGramatica(gramatica);

    for (Map.Entry<String, List<String>> each : gramatica.entrySet()) {
      String naoTerminal = each.getKey();
      List<String> regras = each.getValue();

      for (String regra : regras) {
        if (transicoesUnitarias.contains(regra)) {
          List<String> copyRegras = pegarRegras(regra, gramatica);
          novaGramatica.get(naoTerminal).remove(regra);
          novaGramatica.get(naoTerminal).addAll(copyRegras);
        }
      }
    }

    return novaGramatica;
  }
}