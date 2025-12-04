// O arquivo ficaria em um package como 'view' ou 'interface'
package src.loja;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuInterface {

    private EstoqueManager estoqueManager;
    private DadosCSV dados;
    private VendaManager vendaManager;
    private Scanner scanner;

    // O construtor recebe as instâncias que ela precisa para operar
    public MenuInterface(EstoqueManager estoqueManager, DadosCSV dados, VendaManager vendaManager) {
        this.estoqueManager = estoqueManager;
        this.dados = dados;
        this.vendaManager = vendaManager;
        this.scanner = new Scanner(System.in);
    }
    
    // --- Lógica Principal do Menu ---

    public void iniciarMenu() {
        int opcao = -1;
        
        while (opcao != 0) {
            limparConsole();
            System.out.println("\n═════════════════════════════════════════");
            System.out.println("           LOJA DE BEBIDAS - MENU         ");
            System.out.println("═════════════════════════════════════════");
            System.out.println(" 1. Gerenciar Estoque (Adicionar/Alterar)");
            System.out.println(" 2. Listar Estoque");
            System.out.println(" 3. Iniciar nova venda");
            System.out.println(" 0. Sair e Salvar Dados");
            System.out.println("─────────────────────────────────────────");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt(); 
                scanner.nextLine(); // Consome a quebra de linha

                switch (opcao) {
                    case 1:
                        limparConsole();
                        menuGerenciarEstoque(); 
                        break;
                        case 2:
                        limparConsole();
                        estoqueManager.listarEstoque();
                        scanner.nextInt(); // Espera a entrada '0'
                        scanner.nextLine();
                        break;
                        case 3:
                        limparConsole();
                        vendaManager.iniciarVenda();
                        break;
                    case 0:
                        break;
                    default:
                        System.err.println("Opção inválida. Digite um número de 0 a 3.");
                }

            } catch (InputMismatchException e) {
                System.err.println("Erro de entrada! Por favor, digite apenas números inteiros.");
                scanner.nextLine(); // Limpa o buffer
            }
        }
        // Fechamos o scanner aqui, ao final do ciclo de vida
        scanner.close(); 
    }

    // --- Submenu e Métodos de I/O ---

    private void menuGerenciarEstoque() {
        limparConsole();
        int opcao = -1;
        
        while (opcao != 0) {
            System.out.println("\n─── SUBMENU: Gerenciar Estoque ───");
            System.out.println(" 1. Adicionar Novo Produto");
            System.out.println(" 2. Modificar Quantidade Existente");
            System.out.println(" 0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        adicionarProduto();
                        // Salva imediatamente
                        dados.salvarEstoque(estoqueManager.getEstoqueParaSalvar());
                        break;
                    case 2:
                        modificarQuantidade();
                        dados.salvarEstoque(estoqueManager.getEstoqueParaSalvar());
                        break;
                    case 0:
                        return;
                    default:
                        System.err.println("Opção inválida. Digite um número de 0 a 2.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Erro de entrada! Por favor, digite apenas números inteiros.");
                scanner.nextLine(); 
            }
        }
    }
    
    // Método de I/O para Adicionar Produto
    private void adicionarProduto() {
        // [CÓDIGO DE ADICIONAR PRODUTO com Try-Catch, conforme a resposta anterior,
        // apenas mudando o nome do método para ser mais simples e focado em I/O]
        try {
            System.out.println("\n--- Adicionar Novo Produto ---");
            // ... (restante do código de input e validação)
            System.out.print("Código (apenas números): ");
            int codigo = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Nome do Produto: ");
            String nome = scanner.nextLine();

            System.out.print("Preço Unitário (Ex: 12,50): ");
            double preco = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Quantidade Inicial em Estoque: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine();
            
            Produto novaBebida = new Produto(codigo, nome, preco, quantidade);
            if (estoqueManager.adicionarProduto(novaBebida)) {
                System.out.println("Produto '" + nome + "' adicionado com sucesso.");
            }
            
        } catch (InputMismatchException e) {
            System.err.println("ERRO: Entrada inválida para Código, Preço ou Quantidade. Voltando ao menu.");
            scanner.nextLine(); 
        }
    }
    
    // Método de I/O para Modificar Quantidade
    private void modificarQuantidade() {
        // [CÓDIGO DE MODIFICAR QUANTIDADE com Try-Catch, conforme a resposta anterior]
        try {
            System.out.println("\n--- Modificar Quantidade ---");
            System.out.print("Digite o Código do Produto a modificar: ");
            int codigo = scanner.nextInt();
            scanner.nextLine();

            Produto produto = estoqueManager.buscarProduto(codigo);
            if (produto == null) {
                System.err.println("Produto não encontrado com o código " + codigo + ".");
                return;
            }
            
            System.out.println("Produto: " + produto.getNome() + " | Estoque Atual: " + produto.getQuantidadeEmEstoque());
            System.out.print("Digite a NOVA quantidade total em estoque: ");
            int novaQuantidade = scanner.nextInt();
            scanner.nextLine();

            estoqueManager.modificarEstoque(codigo, novaQuantidade);
            
        } catch (InputMismatchException e) {
            System.err.println("ERRO: Entrada inválida para Código ou Quantidade. Voltando ao menu.");
            scanner.nextLine();
        }
    }

    private void limparConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                // Para Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Para Linux/Mac (Muitos terminais suportam esta sequência de escape)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            // Opção de fallback: se não funcionar, imprime várias linhas em branco
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}