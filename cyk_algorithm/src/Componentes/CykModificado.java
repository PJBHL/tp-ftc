package Componentes;

import java.util.*;

import Componentes.CNF.RemoverTransicoesVazias;

public class CykModificado {

    public static List<String> pegarTransicoesVazias(List<String> transicoesVazias, Map<String, List<String>> copyGlc) {
        for (Map.Entry<String, List<String>> each : copyGlc.entrySet()) {
          String naoTerminal = each.getKey();
          List<String> regras = each.getValue();
    
          for (String eachTV : transicoesVazias) {
            if (regras.contains(eachTV) && eachTV != naoTerminal) {
              regras.remove(eachTV);
              transicoesVazias.add(naoTerminal);
    
              return pegarTransicoesVazias(transicoesVazias, copyGlc);
            }
          }
        }
    
        transicoesVazias.remove("!");
        return transicoesVazias;
      }


    public static List<String> nullable(Map<String, List<String>> glc) {
        List<String> transicoesVazias = new ArrayList<>();
        Map<String, List<String>> glcCopy = Gramatica.clonarGramatica(glc);
        transicoesVazias.add("!");

        transicoesVazias = pegarTransicoesVazias(transicoesVazias, glcCopy);

        return transicoesVazias;
    }

    public static Map<String, List<String>> pegarUnitarios(Map<String, List<String>> glc, List<String> nullables) {
        Map<String, List<String>> glcUnitarios = new LinkedHashMap<>();

        for (Map.Entry<String, List<String>> entry : glc.entrySet()) {
            String naoTerminal = entry.getKey();
            List<String> regras = entry.getValue();
            List<String> adicionaveis = new ArrayList<>();

            for(String regra : regras) {
                if(regra.length() >= 2) {
                    // Unitários juntos de não terminais que formam lambda.
                    if(nullables.stream().anyMatch(regra::contains)) {
                        String terminal = new String(regra).replaceAll("[A-Z]", "");
                        adicionaveis.add(terminal);
                    }
                } else if(!regra.equals("!")) {
                    adicionaveis.add(regra);
                }
            }
            glcUnitarios.put(naoTerminal, new ArrayList<>(adicionaveis));
        }


        return glcUnitarios;
    }

    public static void main(String[] args) throws Exception {
        Gramatica glc = new Gramatica(args[0]);
        Forma2NF nf = new Forma2NF(glc);

        Map<String, List<String>> gramatica = Forma2NF.glc;
        Map<String, List<String>> glcCopy = Gramatica.clonarGramatica(gramatica);

        List<String> transicoesVaizas = nullable(glcCopy);
        System.out.println("\nLista com os vazios: " + transicoesVaizas);

        Map<String, List<String>> gramaticaUnitarios = pegarUnitarios(gramatica, transicoesVaizas);
        Gramatica.imprimirGramatica(gramaticaUnitarios);
    }
}