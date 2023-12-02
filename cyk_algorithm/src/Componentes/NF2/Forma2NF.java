package Componentes.NF2;

import java.util.*;
import Componentes.Gramatica;
import Componentes.CNF.*;

/**
 * Classe para a conversão de uma gramática para o padrão
 * descrito no artigo, a forma binária 2NF.
 */
public class Forma2NF {

    public static Map<String, List<String>> glc;

    public Forma2NF(Gramatica gramatica) {
        glc = gramatica.getGramaticaLida();

        System.out.println("\nGramatica Lida: \n");
        Gramatica.imprimirGramatica(glc);

        // Removendo possíveis inuteis da gramática.
        System.out.println("\nGramatica tirando os inuteis: \n");
        glc = RemoverTransicoesInuteis.removerInuteis(glc);
        Gramatica.imprimirGramatica(glc);

        System.out.println("\nGramatica em 2NF: \n");
        glc = ConverterNaoTerminais.converterNaoTerminais(glc);
        Gramatica.imprimirGramatica(glc);
    }

    public static Map<String, List<String>> getGlc() {
        return glc;
    }
}