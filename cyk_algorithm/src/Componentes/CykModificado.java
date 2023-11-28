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

    /** Mudar, tem que ser um map - pegar o valor do naoTerminal que vai para ele também. */
    public static Set<String> pegarUnitarios(Map<String, List<String>> glc, List<String> nullables) {
        Map<String, List<String>> glcCopy = Gramatica.clonarGramatica(glc);
        Set<String> unitarios = new LinkedHashSet<>();

        nullables = nullable(glcCopy);

        for (Map.Entry<String, List<String>> entry : glc.entrySet()) {
            String naoTerminal = entry.getKey();
            List<String> regras = entry.getValue();

            for(String regra : regras) {
                if(regra.length() >= 2) {
                    // Unitários juntos de não terminais que formam lambda.
                    if(nullables.stream().anyMatch(regra::contains)) {
                        String terminal = new String(regra).replaceAll("[A-Z]", "");
                        unitarios.add(terminal);
                    }
                } else {
                    // Unitários sozinhos.
                    unitarios.add(regra);
                }
            }
        }


        unitarios.remove("!");
        System.out.println("\nUnitários: " + unitarios);

        return unitarios;
    }

    public static void main(String[] args) throws Exception {
        Gramatica glc = new Gramatica(args[0]);
        Forma2NF nf = new Forma2NF(glc);
        Map<String, List<String>> gramatica = Forma2NF.glc;
        List<String> transicoesVaizas = nullable(gramatica);
        System.out.println("\nLista com os vazios: " + transicoesVaizas);
        pegarUnitarios(gramatica, transicoesVaizas);
    }
}