package src.loja;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DadosCSV {
    // Define o nome do arquivo CSV que será usado para armazenar os dados do estoque
    private static final String NOME_ARQUIVO = "estoque_bebidas.csv";
    // Define o separador usado dentro do arquivo (cuidado para não usar ';' se houver nos nomes)
    private static final String SEPARADOR = ";";

    // --- 1. CARREGAMENTO DE DADOS (Ao iniciar o programa) ---

    /**
     * Carrega a lista de bebidas do arquivo CSV.
     * Requisito: Retorna o formato exato esperado pelo EstoqueManager (List<Bebida>)
     * @return Uma lista de objetos Bebida carregados, ou uma lista vazia em caso de erro.
     */
    public List<Produto> carregarEstoque() {
        List<Produto> estoqueCarregado = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            System.out.println("Carregando dados do arquivo CSV...");
            
            // Loop para ler cada linha do arquivo
            while ((linha = br.readLine()) != null) {
                // Divide a linha usando o separador
                String[] dados = linha.split(SEPARADOR);
                
                // Verifica se a linha tem o número esperado de colunas
                if (dados.length == 4) {
                    try {
                        // 1. Converte a String de volta para o tipo de dado original
                        int codigo = Integer.parseInt(dados[0]);
                        String nome = dados[1];
                        double precoUnitario = Double.parseDouble(dados[2]);
                        int quantidadeEmEstoque = Integer.parseInt(dados[3]);
                        
                        // 2. Cria o objeto Bebida e adiciona à lista
                        Produto bebida = new Produto(codigo, nome, precoUnitario, quantidadeEmEstoque);
                        estoqueCarregado.add(bebida);
                        
                    } catch (NumberFormatException e) {
                        System.err.println("Linha inválida no CSV (erro de formato numérico): " + linha);
                    }
                } else {
                    System.err.println("Linha inválida no CSV (formato incorreto): " + linha);
                }
            }
            
        } catch (FileNotFoundException e) {
            // Isso é normal na primeira execução
            System.out.println("Arquivo " + NOME_ARQUIVO + " não encontrado. Iniciando com estoque vazio.");
        } catch (IOException e) {
            System.err.println("Erro de leitura ao carregar o estoque: " + e.getMessage());
        }
        
        return estoqueCarregado;
    }

    // --- 2. SALVAMENTO DE DADOS (Após venda, modificação ou ao sair) ---
    
    /**
     * Salva a lista atual de bebidas no arquivo CSV, sobrescrevendo o conteúdo anterior.
     * Requisito: Recebe o formato exato fornecido pelo EstoqueManager (List<Bebida>)
     * @param estoque A lista de bebidas a ser salva.
     * @return true se o salvamento foi bem-sucedido.
     */
    public boolean salvarEstoque(List<Produto> estoque) {
        // Usa 'FileWriter' com 'BufferedWriter' para escrita eficiente
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
            
            // Loop para escrever cada objeto Bebida no arquivo
            for (Produto bebida : estoque) {
                // Usamos o método toString() da classe Bebida que criamos,
                // que já retorna a string no formato CSV (Ex: 101;Cerveja;5.50;50)
                bw.write(bebida.toString());
                bw.newLine(); // Adiciona uma quebra de linha após cada item
            }
            
            System.out.println("Dados do estoque salvos com sucesso em " + NOME_ARQUIVO);
            return true;
            
        } catch (IOException e) {
            System.err.println("Erro de escrita ao salvar o estoque: " + e.getMessage());
            return false;
        }
    }
}