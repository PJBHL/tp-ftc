package Componentes;

import java.io.*;
import java.util.*;

import Componentes.CNF.*;

public class CykModificado {
    /**
     * Método para pegar os não terminais que possuem transições lambdas diretas e
     * indiretas.
     * 
     * @param glc - gramática para coletar os lambdas.
     * @return - lista com os não terminais que geram transições vazias.
     */
    public static List<String> nullable(Map<String, List<String>> glc) {
        List<String> transicoesVazias = new ArrayList<>();
        Map<String, List<String>> glcCopy = Gramatica.clonarGramatica(glc);
        transicoesVazias.add("!");

        transicoesVazias = RemoverTransicoesVazias.pegarTransicoesVazias(transicoesVazias, glcCopy);

        return transicoesVazias;
    }

    /**
     * Método para pegar uma gramática com os elementos unitários.
     * 
     * @param glc       - gramática para retornar uma gramática unitária.
     * @param nullables - lista com os não terminais que geram transições vazias.
     * @return - uma nova gramática com apenas unitários.
     */
    public static Map<String, List<String>> pegarUnitarios(Map<String, List<String>> glc, List<String> nullables) {
        Map<String, List<String>> glcUnitarios = new LinkedHashMap<>();

        for (Map.Entry<String, List<String>> entry : glc.entrySet()) {
            String naoTerminal = entry.getKey();
            List<String> regras = entry.getValue();
            List<String> adicionaveis = new ArrayList<>();

            for (String regra : regras) {
                if (regra.length() >= 2) {
                    // Unitários juntos de não terminais que formam lambda.
                    if (nullables.stream().anyMatch(regra::contains)) {
                        if (regra.matches(".*[a-z].*")) {
                            String terminal = new String(regra).replaceAll("[A-Z]", "");
                            adicionaveis.add(terminal);
                        } else
                            for (String nullable : nullables) {
                                if (regra.contains(nullable)) {
                                    // Verifica se o próximo caractere depois do nullable é um número.
                                    // Se for um número significa que ele na verdade é um outro não terminal gerado aleatoriamente.
                                    List<String> splitString = ConverterNaoTerminais.dividirString(regra);
                                    for(String split : splitString) {
                                        if(split.matches(".*\\d.*")) {
                                            adicionaveis.add(split);
                                        }
                                    }
                                }
                            }
                    }
                } else if (!regra.equals("!")) // Se a regra é diferente de lambda, nesse ponto já é unitária.
                    adicionaveis.add(regra);
            }
            if (!adicionaveis.isEmpty() && !adicionaveis.contains(""))
                glcUnitarios.put(naoTerminal, new ArrayList<>(adicionaveis));
        }

        return glcUnitarios;
    }

    /**
     * Método para pegar a transitividade de cada elemento da gramática de
     * unitários,
     * ou seja, a partir de um elemento X chego nos elementos w, y, z ...
     * 
     * @param glc - gramática com unitários.
     * @return - nova gramática com a transitividade de cada não terminal.
     */
    public static Map<String, List<String>> glcTransitividade(Map<String, List<String>> glc) {
        Map<String, List<String>> gramaticaTransitiva = Gramatica.clonarGramatica(glc);

        // Criando gramática de transitividade.
        boolean modificado;
        do {
            modificado = false;
            for (Map.Entry<String, List<String>> entry : gramaticaTransitiva.entrySet()) {
                List<String> value = entry.getValue();
                List<String> novosElementos = new ArrayList<>();

                // Verificando a transitividade
                for (String elemento : value) {
                    if (gramaticaTransitiva.containsKey(elemento)) {
                        List<String> transitividadeElemento = gramaticaTransitiva.get(elemento);
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

        return gramaticaTransitiva;
    }

    /**
     * Método para fazer o inverso da gramática anterior, ou seja, esse elemento X
     * chega-se nele por w, y, z...
     * 
     * @param glc - gramática com a transitividade de cada elemento.
     * @return - gramática com o inverso da transitividade de cada elemento.
     */
    public static Map<String, List<String>> inversoTransitiva(Map<String, List<String>> glc) {
        Map<String, List<String>> invertedMap = new LinkedHashMap<>();

        for (Map.Entry<String, List<String>> entry : glc.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();

            if (!invertedMap.containsKey(key)) {
                invertedMap.put(key, new ArrayList<>());
                invertedMap.get(key).add(key);  // Adiciona o próprio elemento à lista
            }

            for (String value : values) {
                if (!invertedMap.containsKey(value)) {
                    invertedMap.put(value, new ArrayList<>());
                    invertedMap.get(value).add(value);
                }
                invertedMap.get(value).add(key);
            }
        }

        invertedMap.remove(glc.keySet().iterator().next());

        return invertedMap;
    }

    /**
     * Método para printar a tabela do método CYK em um arquivo, para comparação de resultados e testes.
     * @param tabela - tabela [n][n] para print.
     * @param filename - nome do arquivo
     */
    public static void printTableToFile(List<String>[][] tabela, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            int n = tabela.length;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    writer.print("T[" + i + "][" + j + "]: ");
                    printListToFile(tabela[i][j], writer);
                }
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printListToFile(List<String> list, PrintWriter writer) {
        if (list == null) {
            writer.println("null");
            return;
        }

        writer.print("[ ");
        for (String value : list) {
            writer.print(value + " ");
        }
        writer.println("]");
    }

    /**
     * Método cyk modificidado proposto no artigo, aqui são usadas duas tabelas para comparação e duas gramáticas.
     * @param glc - gramática original, lida do arquivo.
     * @param inversa - gramática de transitividade inversa, o último dos passos dos métodos acima.
     * @param word - sentença a ser verificada se pertence ou não a gramática.
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

        // Colocando as regras e não terminais baseados na gramática inversa na primeira tabela.
        for (int i = 0; i < n; i++) {
            List<String> regras = inversa.get(String.valueOf(word.charAt(i)));
            if (regras != null) {
                tabela[i][i] = regras;
            } else {
                tabela[i][i].add(String.valueOf(word.charAt(i)));
            }
        }

        printTableToFile(tabela, "tabela-inicial.txt");

        for (int j = 1; j < n; j++) {
            for (int i = j - 1; i >= 0; i--) {
                tabela1[i][j] = new ArrayList<>();
                for (int h = i; h <= j - 1; h++) {
                    for (String naoTerminal : naoTerminais) {
                        for (String regra : glc.get(naoTerminal)) {
                            if (regra.length() != 1) {
                                List<String> splitRegra = ConverterNaoTerminais.dividirString(regra);
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

        printTableToFile(tabela, "tabela-final.txt");

        // Verifica se a tabela contém a primeira regra. Caso contenha, a sentença pertence a gramática.
        return tabela[0][n - 1].contains("E");
    }

    private static void testarSentencas(String caminhoArquivo, Map<String, List<String>> glc, Map<String, List<String>> inversa) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                // Remova espaços em branco no início e no final da linha
                linha = linha.trim();

                // Testar a sentença com a função cykModificado
                boolean resultado = cykModificado(glc, inversa, linha);

                // Exibir o resultado para cada sentença
                System.out.println("A frase ( " + linha + " ) " + (resultado ? "é da linguagem" : "não é da linguagem"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Gramatica glc = new Gramatica(args[0]);
        Forma2NF nf = new Forma2NF(glc);

        Map<String, List<String>> gramatica = Forma2NF.glc;
        Map<String, List<String>> glcCopy = Gramatica.clonarGramatica(gramatica);

        List<String> transicoesVaizas = nullable(glcCopy);

        System.out.println("\nGramatica com Unitários: \n");
        Map<String, List<String>> gramaticaUnitarios = pegarUnitarios(gramatica, transicoesVaizas);
        Gramatica.imprimirGramatica(gramaticaUnitarios);

        System.out.println("\nGramatica Transitividade: \n");
        Map<String, List<String>> gramaticaTransitividade = Gramatica.clonarGramatica(gramaticaUnitarios);
        gramaticaTransitividade = glcTransitividade(gramaticaUnitarios);
        Gramatica.imprimirGramatica(gramaticaTransitividade);

        System.out.println("\nGramatica Inversa Transitividade: \n");
        Map<String, List<String>> gramaticaInversa = Gramatica.clonarGramatica(gramaticaTransitividade);
        gramaticaInversa = inversoTransitiva(gramaticaTransitividade);
        Gramatica.imprimirGramatica(gramaticaInversa);

        testarSentencas("C:\\Users\\joaopc\\Documents\\tp-ftc\\cyk_algorithm\\src\\Componentes/frases.txt", glcCopy, gramaticaInversa);
    }
}