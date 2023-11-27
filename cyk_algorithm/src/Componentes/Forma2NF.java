package Componentes;

import java.util.*;

import Componentes.CNF.ConverterNaoTerminais;
import Componentes.CNF.RemoverTransicoesInuteis;

/**
 * Classe para a conversão de uma gramática para o padrão
 * descrito no artigo, a forma binária 2NF.
 */
public class Forma2NF {
    Map<String, List<String>> glc;

    public Forma2NF(Gramatica gramatica) {
        this.glc = gramatica.getGramaticaLida();

        Map<String, List<String>> copiaMap = Gramatica.clonarGramatica(glc);

        System.out.println("\nGramatica Lida: \n");
        Gramatica.imprimirGramatica(copiaMap);

        // Removendo possíveis inuteis da gramática.
        System.out.println("\nGramatica tirando os inuteis: \n");
        copiaMap = RemoverTransicoesInuteis.removerInuteis(copiaMap);
        Gramatica.imprimirGramatica(copiaMap);
        
        System.out.println("\nGramatica em 2NF: \n");
        copiaMap = ConverterNaoTerminais.converterNaoTerminais(copiaMap);
        Gramatica.imprimirGramatica(copiaMap);
    }
}
