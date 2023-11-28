package Componentes;

import java.util.*;

import Componentes.CNF.RemoverTransicoesInuteis;
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
            if(!adicionaveis.isEmpty())
                glcUnitarios.put(naoTerminal, new ArrayList<>(adicionaveis));
        }


        return glcUnitarios;
    }

    public static Map<String, List<String>> glcTransitividade(Map<String, List<String>> glc) {
        Map<String, List<String>> outputMap = new LinkedHashMap<>();

        // Copiando as entradas para evitar modificar o mapa original
        for (Map.Entry<String, List<String>> entry : glc.entrySet()) {
            String key = entry.getKey();
            List<String> value = new ArrayList<>(entry.getValue());
            outputMap.put(key, value);
        }

        // Calculando a transitividade
        boolean modificado;
        do {
            modificado = false;
            for (Map.Entry<String, List<String>> entry : outputMap.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                List<String> novosElementos = new ArrayList<>();

                // Verificando a transitividade
                for (String elemento : value) {
                    if (outputMap.containsKey(elemento)) {
                        List<String> transitividadeElemento = outputMap.get(elemento);
                        for (String transitividade : transitividadeElemento) {
                            if (!value.contains(transitividade) && !novosElementos.contains(transitividade)) {
                                novosElementos.add(transitividade);
                                modificado = true;
                            }
                        }
                    }
                }

                // Adicionando novos elementos à lista
                value.addAll(novosElementos);
            }
        } while (modificado);

        return outputMap;
    }

    public static void main(String[] args) throws Exception {
        Gramatica glc = new Gramatica(args[0]);
        Forma2NF nf = new Forma2NF(glc);

        Map<String, List<String>> gramatica = Forma2NF.glc;
        Map<String, List<String>> glcCopy = Gramatica.clonarGramatica(gramatica);

        List<String> transicoesVaizas = nullable(glcCopy);
        System.out.println("\nLista com os vazios: " + transicoesVaizas);

        System.out.println("\nGramatica com Unitários: \n");
        Map<String, List<String>> gramaticaUnitarios = pegarUnitarios(gramatica, transicoesVaizas);
        Gramatica.imprimirGramatica(gramaticaUnitarios);

        System.out.println("\nGramatica Transitividade: \n");
        Map<String, List<String>> gramaticaTransitividade = Gramatica.clonarGramatica(gramaticaUnitarios);
        gramaticaTransitividade = glcTransitividade(gramaticaUnitarios);
        Gramatica.imprimirGramatica(gramaticaTransitividade);
    }
}