package Componentes.CNF;

import java.util.*;

import Componentes.Gramatica;

public class FromaNormalChomsky {

  /**
   * Método para verificar a quantidade de letras maiusculas em determinada regra.
   * 
   * @param texto - regra analisada
   * @return - true se é diferente de 2 letras maiusculas.
   */
  private static boolean verificarQuantidadeMaiusculas(String texto) {
    int contadorMaiusculas = 0;

    for (int i = 0; i < texto.length(); i++) {
      char caractere = texto.charAt(i);

      if (Character.isUpperCase(caractere)) {
        contadorMaiusculas++;
      } else {
        return false;
      }
    }

    return contadorMaiusculas != 2;
  }

  /**
   * Método para verificar se determinada regra obedece as específicações da forma
   * de chomsky, ou seja,
   * S -> AB
   * ou
   * S -> a
   * 
   * @param regra - regra analisada
   * @return - true caso seja valida
   */
  private static boolean isValidChomsky(String regra) {
    List<String> naoTerminais = caracteresMaiusculos(regra);
    int quantidadeNaoTerminais = naoTerminais.size();

    Set<String> terminais = caracteresMinusculos(regra);
    int quantidadeTerminais = terminais.size();

    if ((quantidadeTerminais == 1 && quantidadeNaoTerminais == 0 && regra.length() == 1) ||
        (quantidadeNaoTerminais == 2 && quantidadeTerminais == 0)) {
      return true;
    }

    return false;
  }

  /**
   * Método para tratar regras inválidas para a forma de chomsky.
   * 
   * @param glc        - gramática original.
   * @param regras     - conjunto de regras de determinado não terminal.
   * @param regra      - regra a ser analisada.
   * @param key        - não terminal.
   * @param newRuleKey - possível novo não terminal.
   */
  // private static void tratarCasoNaoValido(Map<String, List<String>> glc,
  // List<String> regras, String regra,
  // String key, String newRuleKey) {

  // // Tratando regras do formato aB (terminal - nãoTerminal).
  // if (regra.matches(".*[a-z].*") && regra.matches(".*[A-Z].*")) {

  // // Gera novo nao terminal, sempre iniciando com o primeiro caractere
  // maiusculo
  // // da regra e um número.

  // // Tratando quando precisar criar um novo não terminal.
  // if (!glc.containsKey(newRuleKey)) {
  // List<String> newRuleListValue = new ArrayList<>();
  // newRuleListValue.add(regra.replaceAll("[A-Z]", ""));
  // regras.remove(regra);
  // regras.add(newRuleKey + regra.replaceAll("[a-z]", ""));
  // glc.put(key, regras);
  // glc.put(newRuleKey, newRuleListValue);

  // // O else quando o estado ja está criando.
  // } else {
  // regras.remove(regra);
  // regras.add(newRuleKey + regra.replaceAll("[a-z]", ""));
  // glc.put(key, regras);
  // }
  // }

  // // Tratando 3 maiusculas.
  // if (verificarQuantidadeMaiusculas(regra)) {

  // // Quando não existe ainda o nao terminal.
  // if (!glc.containsKey(newRuleKey)) {
  // String duasPrimeirasLetras = regra.substring(0, 2);
  // String terceiraLetra = String.valueOf(regra.charAt(2));
  // List<String> newRuleListValue = new ArrayList<>();
  // newRuleListValue.add(duasPrimeirasLetras);
  // regras.remove(regra);
  // regras.add(newRuleKey + terceiraLetra);
  // glc.put(newRuleKey, newRuleListValue);
  // glc.put(key, regras);

  // // Quado ja existe o nao terminal na glc, entao faz o else.
  // } else {
  // String terceiraLetra = String.valueOf(regra.charAt(2));
  // regras.remove(regra);
  // regras.add(newRuleKey + terceiraLetra);
  // glc.put(key, regras);
  // }
  // }
  // }

  public static Set<String> caracteresMinusculos(String input) {
    Set<String> caracteresUnicos = new HashSet<>();

    for (int i = 0; i < input.length(); i++) {
      char caractere = input.charAt(i);
      if (Character.isLowerCase(caractere)) {
        caracteresUnicos.add(String.valueOf(caractere));
      }
    }

    return caracteresUnicos;
  }

  public static List<String> caracteresMaiusculos(String input) {
    List<String> caracteresUnicos = new ArrayList<>();

    for (int i = 0; i < input.length(); i++) {
      char caractere = input.charAt(i);
      if (Character.isUpperCase(caractere)) {
        caracteresUnicos.add(String.valueOf(caractere));
      }
    }

    return caracteresUnicos;
  }

  private static String tratarCasoNaoValido(Map<String, List<String>> glc, String naoTerminal, String regra) {
    int contador = 1;

    String regraCopy = new String(regra);
    List<String> newRules = new ArrayList<>();
    // Tratando Regras do formato: terminal não terminal - aA, bB, bbB, cB, Cc, abB,
    // aB.
    // etc.
    // A1 -> a
    // B1 -> b
    // A1B1B
    // A2 -> A1B1
    // A2B
    if (regra.matches(".*[a-z].*") && regra.matches(".*[A-Z].*")) {
      // Gera um novo não terminal para o terminal.
      Set<String> terminais_da_regra = caracteresMinusculos(regra);
      for (String terminal : terminais_da_regra) {
        String newNaoTerminal = terminal.toUpperCase() + String.valueOf(contador);
        "".toString();
        if (!glc.containsKey(newNaoTerminal)) {
          regraCopy = regraCopy.replaceAll(terminal, newNaoTerminal);
          newRules.add(regraCopy);
          glc.get(naoTerminal).remove(regra);
          glc.get(naoTerminal).add(regraCopy);

          // Adicionando novo não terminal com sua regra do tipo A -> a.
          List<String> temp = new ArrayList<>();
          temp.add(terminal);
          glc.put(newNaoTerminal, temp);
          "".toString();
        }
      }
    }

    return regraCopy;
  }

  private static char gerarLetraMaiusculaAleatoria() {
    Random random = new Random();
    // A tabela ASCII para letras maiúsculas está no intervalo de 65 ('A') a 90
    // ('Z')
    int indice = random.nextInt(26) + 65;
    return (char) indice;
  }

  public static Map<String, List<String>> converterTerminais(Map<String, List<String>> glc) {
    Map<String, List<String>> glcopy = Gramatica.clonarGramatica(glc);
    List<String> terminais = Gramatica.pegarTerminais(glcopy);
    List<String> regrasAdd = new ArrayList<>();

    for (String terminal : terminais) {
      char letraRandom = gerarLetraMaiusculaAleatoria();
      for (Map.Entry<String, List<String>> each : glc.entrySet()) {
        String naoTerminal = each.getKey();
        List<String> regras = each.getValue();

        List<String> regrasCopy = new ArrayList<>(regras);

        for (String regra : regrasCopy) {
          for (int i = 0; i < regra.length(); i++) {
            char letraAtual = regra.charAt(i);
            if (String.valueOf(letraAtual).equals(terminal) && regra.length() >= 2) {
              String newRule = "";
              if (!glcopy.containsKey(String.valueOf(letraRandom))) {
                newRule = regra.replace(letraAtual, letraRandom);
                i = regra.length();
              } else {
                String number = Character.toUpperCase(letraAtual) + "1";
                newRule = regra.replace(String.valueOf(letraAtual), number);
                i = regra.length();
              }
              glcopy.get(naoTerminal).add(regras.indexOf(regra), newRule);
              glcopy.get(naoTerminal).remove(regra);
            }
          }
        }
      }
      if (!regrasAdd.contains(terminal))
        regrasAdd.add(terminal);
      glcopy.put(String.valueOf(letraRandom), new ArrayList<>(regrasAdd));
      regrasAdd.clear();
    }

    return glcopy;
  }

  public static Map<String, List<String>> convertToCNF(Map<String, List<String>> glc) {
    Map<String, List<String>> glcopy = Gramatica.clonarGramatica(glc);

    for (Map.Entry<String, List<String>> each : glcopy.entrySet()) {
      String naoTerminal = each.getKey();
      List<String> regras = each.getValue();

      List<String> regras_copy = new ArrayList<>(regras);

      for (String each_regra : regras_copy) {
        while (!isValidChomsky(each_regra)) {
          each_regra = tratarCasoNaoValido(glcopy, naoTerminal, each_regra);
        }
      }
    }

    return glcopy;
  }

  /**
   * Método para realizar a conversão de uma gramática que já passou pelos passos
   * anteriores para a forma de chomsky.
   * 
   * @param glc - gramática advinda dos passos anteriores.
   * @return - nova gramática na forma de chomsky.
   */
  // public static Map<String, List<String>> convertToCNF(Map<String,
  // List<String>> glc) {
  // Map<String, List<String>> glcloop = Gramatica.clonarGramatica(glc);

  // // Faz iteracao pegando cada regra do clone.
  // int contador = 1;
  // Map<String, String> dict_regras = new LinkedHashMap<>();
  // for (Map.Entry<String, List<String>> each : glcloop.entrySet()) {
  // String key = each.getKey();
  // List<String> regras = each.getValue();

  // List<String> regras_copy = new ArrayList<>(regras);

  // for (String each_regra : regras_copy) {
  // // Valida se a regra precisa ser tratada ou nao.
  // if (!isValidChomsky(each_regra)) {
  // // Manda a regra pra ser tratada de acordo com o seu caso, que é diferente de
  // // maiusculas e 1 minuscula.
  // String ret = dict_regras.get(each_regra);
  // String filterMaisc = each_regra.replaceAll("[a-z]", "");
  // String newRule = String.valueOf(filterMaisc.charAt(0));
  // if (ret == null) {
  // dict_regras.put(each_regra, newRule + contador);
  // newRule = newRule + contador;
  // } else {
  // newRule = ret;
  // }

  // tratarCasoNaoValido(glc, regras, each_regra, key, newRule);
  // contador++;
  // }
  // }
  // }
  // return glc;
  // }
}
