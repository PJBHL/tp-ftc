package Componentes;

import Componentes.CNF.*;

import java.util.*;

/**
 * Classe para fazer a conversão de uma GLC qualquer para uma no formato de
 * Chomsky.
 * Input é uma glc qualquer.
 * Output é uma glc na forma de chomsky.
 */
public class FormaNormalChomsky {
  /**
   * Construtor do método de Chomsky.
   * 
   * @param ut - recebe uma gramatica e realiza todas as operações
   *           do método de chomsky.
   */
  Map<String, List<String>> glc;

  public FormaNormalChomsky(Gramatica gramatica) {
    this.glc = gramatica.getGramaticaLida();

    System.out.println("\nGramatica Lida: \n");
    Map<String, List<String>> copiaMap = Gramatica.clonarGramatica(this.glc);
    Gramatica.imprimirGramatica(copiaMap);

    System.out.println("\nGramatica tirando Lambda: \n");
    copiaMap = RemoverTransicoesVazias.eliminarProducoesVazias(copiaMap);
    Gramatica.imprimirGramatica(copiaMap);

    System.out.println("\nGramatica tirando Unitários: \n");
    copiaMap = RemoverTransicoesInuteis.removerUnitarios(copiaMap);
    Gramatica.imprimirGramatica(copiaMap);

    System.out.println("\nGramatica tirando regras Inúteis: \n");
    copiaMap = RemoverTransicoesInuteis.removerInuteis(copiaMap);
    Gramatica.imprimirGramatica(copiaMap);

    System.out.println("\nGramatica convertendo terminais: \n");
    copiaMap = ConverterTerminais.converterTerminais(copiaMap);
    Gramatica.imprimirGramatica(copiaMap);

    System.out.println("\nGramatica em CNF: \n");
    copiaMap = ConverterNaoTerminais.converterNaoTerminais(copiaMap);
    Gramatica.imprimirGramatica(copiaMap);
  }
}