import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

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

    public static void testarSentencas(String caminhoArquivo, Map<String, List<String>> glc,
            Map<String, List<String>> inversa) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                // Remova espaços em branco no início e no final da linha
                linha = linha.trim();

                // Testar a sentença com a função cykModificado
                boolean resultado = CykModificado.cykModificado(glc, inversa, linha);

                // Exibir o resultado para cada sentença
                System.out
                        .println("A frase ( " + linha + " ) " + (resultado ? "é da linguagem" : "não é da linguagem"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
