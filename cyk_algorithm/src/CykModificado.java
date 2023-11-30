import java.io.*;
import java.util.*;

import Componentes.*;
import Componentes.CNF.*;

public class CykModificado {
    /**
     * Método cyk modificidado proposto no artigo, aqui são usadas duas tabelas para
     * comparação e duas gramáticas.
     * 
     * @param glc     - gramática original, lida do arquivo.
     * @param inversa - gramática de transitividade inversa, o último dos passos dos
     *                métodos acima.
     * @param word    - sentença a ser verificada se pertence ou não a gramática.
     * @return - true se a sentença pertence a gramática.
     */
    @SuppressWarnings("unchecked") // Warning ao criar uma new List sem um tipo abstrato.
    public static boolean cykModificado(Map<String, List<String>> glc, Map<String, List<String>> inversa, String word) {
        int n = word.length();
        List<String>[][] tabela = new ArrayList[n][n];
        List<String>[][] tabela1 = new ArrayList[n][n];
        Set<String> naoTerminais = glc.keySet();

        // Inicializar tabelas com tamanho da palavra..
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tabela[i][j] = new ArrayList<>();
                tabela1[i][j] = new ArrayList<>();
            }
        }

        // Colocando as regras e não terminais baseados na gramática inversa na primeira
        // tabela.
        for (int i = 0; i < n; i++) {
            List<String> regras = inversa.get(String.valueOf(word.charAt(i)));
            if (regras != null) {
                tabela[i][i] = regras;
            } else {
                tabela[i][i].add(String.valueOf(word.charAt(i)));
            }
        }

        Tester.printTableToFile(tabela, "tabela-inicial.txt");

        for (int j = 1; j < n; j++) {
            for (int i = j - 1; i >= 0; i--) {
                tabela1[i][j] = new ArrayList<>();
                for (int h = i; h <= j - 1; h++) {
                    for (String naoTerminal : naoTerminais) {
                        for (String regra : glc.get(naoTerminal)) {
                            if (regra.length() != 1) {
                                List<String> splitRegra = Gramatica.dividirString(regra);
                                String regra1 = splitRegra.get(0);
                                String regra2 = splitRegra.get(1);
                                if (tabela[i][h].contains(regra1)
                                        && tabela[h + 1][j].contains(regra2)) {
                                    if (inversa.get(naoTerminal) == null) {
                                        tabela1[i][j].add(naoTerminal);
                                    } else {
                                        tabela1[i][j].addAll(inversa.get(naoTerminal));
                                    }
                                }
                            }
                        }
                    }
                }

                List<String> regras = tabela1[i][j];
                if (regras != null && regras.size() != 0) {
                    tabela[i][j] = regras;
                }
            }
        }

        Tester.printTableToFile(tabela, "tabela-final.txt");

        // Verifica se a tabela contém a primeira regra. Caso contenha, a sentença
        // pertence a gramática.
        return tabela[0][n - 1].contains("E");
    }

    public static void main(String[] args) throws Exception {
        Gramatica glc = new Gramatica(args[0]);
        FormaNormalChomsky chomsky = new FormaNormalChomsky(glc);
    }
}