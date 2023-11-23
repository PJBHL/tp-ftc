package Componentes.CNF;

import java.util.*;

import Componentes.Gramatica;

public class ConverterTerminais {
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
}