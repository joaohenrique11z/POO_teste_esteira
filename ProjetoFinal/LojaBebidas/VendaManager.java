package src.loja;

import java.util.InputMismatchException;
import java.util.Scanner;

public class VendaManager {
    
    private EstoqueManager estoqueManager;
    private DadosCSV dados;
    // O Scanner é mantido aqui apenas para a fase de vendas,
    // garantindo que ele não interfira no Scanner principal da interface.
    private Scanner scanner; 

    // O VendaManager precisa do EstoqueManager e do DadosCSV
    public VendaManager(EstoqueManager estoqueManager, DadosCSV dados) {
        this.estoqueManager = estoqueManager;
        this.dados = dados;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Inicia o processo de venda, permitindo adicionar múltiplos itens
     * e finalizando a transação.
     */
    public void iniciarVenda() {
        System.out.println("\n--- INICIANDO NOVA VENDA ---");
        
        // 1. Cria o objeto Venda que irá armazenar os itens
        Venda novaVenda = new Venda();
        boolean vendendo = true;
        
        while (vendendo) {
            System.out.println("─────────────────────────────────────────");
            System.out.println("  Total Atual: R$" + String.format("%.2f", novaVenda.getValorTotal()));
            System.out.println("─────────────────────────────────────────");
            System.out.println("Ações: 1. Adicionar Item | 2. Finalizar Venda | 0. Cancelar Venda");
            System.out.print("Opção: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        adicionarItemAVenda(novaVenda);
                        break;
                    case 2:
                        finalizarVenda(novaVenda);
                        vendendo = false;
                        break;
                    case 0:
                        System.out.println("Venda cancelada. Estoque não foi alterado.");
                        vendendo = false;
                        break;
                    default:
                        System.err.println("Opção inválida.");
                }

            } catch (InputMismatchException e) {
                System.err.println("Erro de entrada! Digite um número válido.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Coleta o código e quantidade, busca o produto e adiciona um ItemVenda à Venda.
     */
    private void adicionarItemAVenda(Venda venda) {
        try {
            System.out.print("Digite o Código do Produto: ");
            int codigo = scanner.nextInt();
            scanner.nextLine();

            // 1. Busca do Produto no EstoqueManager (Comunicação entre Classes)
            Produto produto = estoqueManager.buscarProduto(codigo);

            if (produto == null) {
                System.err.println("Produto com código " + codigo + " não encontrado.");
                return;
            }

            System.out.println("Produto: " + produto.getNome() + " | Preço: R$" + String.format("%.2f", produto.getPrecoUnitario()));
            System.out.print("Quantidade desejada (Estoque: " + produto.getQuantidadeEmEstoque() + "): ");
            int quantidade = scanner.nextInt();
            scanner.nextLine();

            if (quantidade <= 0) {
                 System.err.println("Quantidade deve ser maior que zero.");
                 return;
            }

            // 2. Tenta remover do Estoque antes de criar o item de venda
            // Se o EstoqueManager.removerDoEstoque falhar (por falta de estoque), ele retorna false.
            if (estoqueManager.removerDoEstoque(codigo, quantidade)) {
                
                // 3. Se deu baixa no estoque, cria o ItemVenda
                ItemVenda item = new ItemVenda(produto, quantidade);
                
                // 4. Adiciona o item à transação (Venda)
                venda.adicionarItem(item);
                
                System.out.println("Item adicionado: " + item.getQuantidade() + "x " + produto.getNome());
                
            } else {
                // A mensagem de erro (estoque insuficiente) já é tratada pelo EstoqueManager.
            }

        } catch (InputMismatchException e) {
            System.err.println("ERRO: Entrada inválida para código ou quantidade.");
            scanner.nextLine();
        }
    }
    
    /**
     * Finaliza a venda e salva o estado atualizado do estoque.
     */
    private void finalizarVenda(Venda venda) {
        if (venda.getItens().isEmpty()) {
            System.err.println("A venda não pode ser finalizada sem itens.");
            return;
        }

        System.out.println("\n════════════ NOTA FISCAL ════════════");
        for (ItemVenda item : venda.getItens()) {
            // Usa o toString() customizado do ItemVenda
            System.out.println("- " + item.toString()); 
        }
        System.out.println("-------------------------------------");
        System.out.println("VALOR TOTAL: R$" + String.format("%.2f", venda.getValorTotal()));
        System.out.println("Data: " + venda.getDataHora());
        System.out.println("═════════════════════════════════════");
        
        // Requisito: Salvamento dos dados após venda
        dados.salvarEstoque(estoqueManager.getEstoqueParaSalvar());
    }
}