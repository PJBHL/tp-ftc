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

    Map<String, List<String>> copiaMap = Gramatica.clonarGramatica(this.glc);

    System.out.println("\nGramatica tirando Lambda: \n");
    copiaMap = RemoverTransicoesVazias.eliminarProducoesVazias(this.glc);
    Gramatica.imprimirGramatica(copiaMap);
    copiaMap = RemoverTransicoesUnitarias.removerUnitarios(copiaMap);
    System.out.println("\nGramatica tirando Unitários: \n");
    Gramatica.imprimirGramatica(copiaMap);
  }
}