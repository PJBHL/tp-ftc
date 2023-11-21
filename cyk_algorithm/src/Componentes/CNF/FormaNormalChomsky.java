package Componentes.CNF;

import java.util.*;

import Componentes.Gramatica;

public class FormaNormalChomsky {

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

  private static boolean isValidChomsky(String regra) {

    if ((regra.length() == 2 && regra.equals(regra.toUpperCase()))
        || (regra.length() == 1 && regra.equals(regra.toLowerCase()))) {
      return true;
    }
    return false;

  }

  private static void tratarCasoNaoValido(Map<String, List<String>> glc, List<String> regras, String regra,
      String key) {

    // Tratando regra aB
    if (regra.matches(".*[a-z].*") && regra.matches(".*[A-Z].*")) {

      // Gera novo nao terminal, sempre iniciando com X e seu caracter minusculo
      String newRuleKey = "X" + regra.replaceAll("[A-Z]", "");

      // Tratando quando precisar criar o estado novo
      if (!glc.containsKey(newRuleKey)) {
        List<String> newRuleListValue = new ArrayList<>();
        newRuleListValue.add(regra.replaceAll("[A-Z]", ""));
        regras.remove(regra);
        regras.add(newRuleKey + regra.replaceAll("[a-z]", ""));
        glc.put(key, regras);
        glc.put(newRuleKey, newRuleListValue);

        // O else quando o estado ja está criando (PRECISA SER TESTADO MAIS ELSE)
      } else {
        regras.remove(regra);
        regras.add(newRuleKey + regra.replaceAll("[a-z]", ""));
        glc.put(key, regras);

      }
    }
    // Tratando 3 maiusculas
    if (verificarQuantidadeMaiusculas(regra)) {

      // Criando nao terminal
      String newRuleKey = "X" + regra.substring(0, 2);

      // Quando não existe ainda o nao terminal
      if (!glc.containsKey(newRuleKey)) {
        String duasPrimeirasLetras = regra.substring(0, 2);
        String terceiraLetra = String.valueOf(regra.charAt(2));
        List<String> newRuleListValue = new ArrayList<>();
        newRuleListValue.add(duasPrimeirasLetras);
        regras.remove(regra);
        regras.add(newRuleKey + terceiraLetra);
        glc.put(newRuleKey, newRuleListValue);
        glc.put(key, regras);

        // Quado ja existe o nao terminal na glc, entao faz o else (PRECISA SER TESTADO
        // AINDA)
      } else {
        String terceiraLetra = String.valueOf(regra.charAt(2));
        regras.remove(regra);
        regras.add(newRuleKey + terceiraLetra);
        glc.put(key, regras);
      }

    }

  }

  public static Map<String, List<String>> convertToCNF(Map<String, List<String>> glc) {

    Map<String, List<String>> glcloop = Gramatica.clonarGramatica(glc);

    // Faz iteracao pegando cada regra do clone
    for (Map.Entry<String, List<String>> each : glcloop.entrySet()) {
      String key = each.getKey();
      List<String> regras = each.getValue();

      // Faz o clone da lista de regras
      List<String> regras_copy = new ArrayList<>(regras);

      for (String each_regra : regras_copy) {
        // Valida se a regra precisa ser tratada ou nao
        if (!isValidChomsky(each_regra)) {
          // Manda a regra pra ser tratada de acordo com o seu caso, que é diferente de 2
          // maiusculas e 1 minuscula
          tratarCasoNaoValido(glc, regras, each_regra, key);
        }

      }
    }
    return glc;
  }

}
