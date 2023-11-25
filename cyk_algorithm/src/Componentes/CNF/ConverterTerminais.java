package Componentes.CNF;

import java.util.*;

import Componentes.Gramatica;

/**
 * Classe para converter os terminais da gramática em novos não terminais
 * e adicionar uma regra para isso. Exemplo:
 * S -> a
 * Gera uma letra aleatória para "a".
 * S -> M.
 * M agora deve gerar "a".
 * S -> M
 * M -> a
 */
public class ConverterTerminais {

  private static char gerarLetraMaiusculaAleatoria(List<String> naoTerminais) {
    Random random = new Random();
    char letra;

    do {
        // Gera uma letra aleatória maiúscula
        letra = (char) (random.nextInt(26) + 'A');
    } while (naoTerminais.contains(String.valueOf(letra)));

    return letra;
}

  public static Map<String, List<String>> converterTerminais(Map<String, List<String>> glc) {
    Map<String, List<String>> glcopy = Gramatica.clonarGramatica(glc);
    List<String> terminais = Gramatica.pegarTerminais(glcopy);
    List<String> naoTerminais = Gramatica.pegarNaoTerminais(glcopy);
    List<String> regrasAdd = new ArrayList<>();

    for (String terminal : terminais) {
      char letraRandom = gerarLetraMaiusculaAleatoria(naoTerminais);
      for (Map.Entry<String, List<String>> each : glc.entrySet()) {
        String naoTerminal = each.getKey();
        List<String> regras = each.getValue();

        List<String> regrasCopy = new ArrayList<>(regras);

        for (String regra : regrasCopy) {
          for (int i = 0; i < regra.length(); i++) {
            char letraAtual = regra.charAt(i);
            if (String.valueOf(letraAtual).equals(terminal) && regra.length() >= 2) {
              String newRule = regra.replace(letraAtual, letraRandom);
              glcopy.get(naoTerminal).add(regras.indexOf(regra), newRule);
              glcopy.get(naoTerminal).remove(regra);
              i = regra.length();
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
}