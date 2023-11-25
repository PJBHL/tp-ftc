package Componentes.Forma2NF;

import java.util.*;
import Componentes.*;
import Componentes.CNF.*;

public class Forma2NF {
    public static void main(String[] args) throws Exception {
        Gramatica gramatica = new Gramatica(args[0]);

        Map<String, List<String>> glc = gramatica.getGramaticaLida();
        Map<String, List<String>> copiaMap = Gramatica.clonarGramatica(glc);

        System.out.println("\nGramatica Lida: \n");
        Gramatica.imprimirGramatica(copiaMap);

        // Removendo possíveis inuteis da gramática.
        System.out.println("\nGramatica tirando os inuteis: \n");
        copiaMap = removerInuteis(copiaMap);
        Gramatica.imprimirGramatica(copiaMap);
        
        // System.out.println("\nGramatica binarizada: \n");
        // copiaMap = ConverterNaoTerminais.converterNaoTerminais(copiaMap);
        // Gramatica.imprimirGramatica(copiaMap);

        // // Removendo possíveis inuteis da gramática.
        // System.out.println("\nGramatica tirando os inuteis: \n");
        // copiaMap = removerInuteis(copiaMap);
        // Gramatica.imprimirGramatica(copiaMap);
    }

    public static Map<String, List<String>> removerInuteis(Map<String, List<String>> glc) {
        Map<String, List<String>> glcCopy = Gramatica.clonarGramatica(glc);
        glcCopy = RemoverTransicoesInuteis.removerRegraQueGeramElasMesmas(glcCopy);
        glcCopy = RemoverTransicoesInuteis.removerRegrasSoltas(glcCopy);
        glcCopy = RemoverTransicoesInuteis.removerRegraRepetida(glcCopy);
        glcCopy = RemoverTransicoesInuteis.removerLoop(glcCopy);
    
        return glcCopy;
    }
}