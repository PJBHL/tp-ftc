import java.io.*;
import java.util.*;

/**
 * Classe com métodos uteis para testes e saída de resultados.
 */

public class Tester {
    /**
     * Método para printar a tabela do método CYK em um arquivo, para comparação de
     * resultados e testes.
     * 
     * @param tabela   - tabela [n][n] para print.
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

    public static void testarSentencas(String caminhoArquivo, Map<String, List<String>> gramatica, Map<String, List<String>> gramaticaInversa, String resultadosArquivo, boolean usarCykModificado, long tempoDeInicio, long tempoConversao) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));
             PrintWriter writer = new PrintWriter(new FileWriter(resultadosArquivo))) {

            writer.println("Tempo de conversão para a Forma Normal de Chomsky ou 2NF: " + (tempoConversao - tempoDeInicio) + " milissegundos\n");

            String linha;
            while ((linha = br.readLine()) != null) {
                // Remova espaços em branco no início e no final da linha
                linha = linha.trim();
                
                // Testar a sentença com o algoritmo apropriado
                long tempoDeCadaTeste = System.currentTimeMillis();
                boolean resultado;
                if (usarCykModificado) {
                    resultado = CykModificado.cykModificado(gramatica, gramaticaInversa, linha);
                } else {
                    resultado = CykOriginal.cykOriginal(gramatica, linha);
                }

                // Medir o tempo de execução
                long tempoFim = System.currentTimeMillis();

                // Escrever o resultado para o arquivo especificado
                writer.println("Sentença: " + linha.length() + " - Resultado: " + (resultado ? "Aceita" : "Rejeitada") +
                        " - Tempo de execução: " + (tempoFim - tempoDeCadaTeste) + " milissegundos");
            }

            long tempoTotal = System.currentTimeMillis();

            writer.print("\nTempo total de todos os testes: " + (tempoTotal - tempoDeInicio) + " milissegundos");

            System.out.println("Resultados foram gravados no arquivo: " + resultadosArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
