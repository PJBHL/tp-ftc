package Componentes;

import Componentes.CNF.*;

import java.util.*;

public class Chomsky {
  /**
   * Construtor do método de Chomsky.
   * 
   * @param ut - recebe uma gramatica e realiza todas as operações
   *           do método de chomsky.
   */
  Map<String, List<String>> glc;

  public Chomsky(Gramatica ut) {
    this.glc = ut.getGramaticaLida();

    System.out.println("\nGramatica Lida: \n");
    Map<String, List<String>> copiaMap = Gramatica.clonarGramatica(this.glc);
    Gramatica.imprimirGramatica(copiaMap);

    System.out.println("\nGramatica tirando Lambda: \n");
    copiaMap = RemoverTransicoesVazias.eliminarProducoesVazias(this.glc);
    Gramatica.imprimirGramatica(copiaMap);

    System.out.println("\nGramatica tirando Unitários: \n");
    copiaMap = RemoverTransicoesUnitarias.removerUnitarios(copiaMap);
    Gramatica.imprimirGramatica(copiaMap);

    System.out.println("\nGramatica em CNF: \n");
    copiaMap = FormaNormalChomsky.convertToCNF(copiaMap);
    Gramatica.imprimirGramatica(copiaMap);
  }
}