package src.loja;
//import java.util.Scanner;

import java.util.List;
// NOTA: Assumimos que as classes EstoqueManager, DadosCSV, VendaManager e MenuInterface
// estão disponíveis no mesmo pacote ou foram devidamente importadas.

/**
 * Ponto de entrada (main) da aplicação.
 * Responsável por inicializar e orquestrar as principais classes do sistema.
 */
public class LojaApp {

    public static void main(String[] args) {
        System.out.println("--- INICIANDO SISTEMA DE LOJA DE Produto ---");

        // 1. Instanciar as Classes de Gerenciamento e Persistência
        // Estas classes contêm a lógica de negócio e o I/O de arquivos.
        EstoqueManager estoqueManager = new EstoqueManager();
        DadosCSV dados = new DadosCSV();

        // 2. O VendaManager depende do EstoqueManager e DadosCSV para operar.
        VendaManager vendaManager = new VendaManager(estoqueManager, dados);
        
        // 3. Carregar Dados do CSV ao Iniciar
        // O EstoqueManager recebe a lista carregada pela DadosCSV.
        System.out.println("\n[PASSO DE INICIALIZAÇÃO]");
        List<Produto> dadosCarregados = dados.carregarEstoque();
        estoqueManager.setEstoque(dadosCarregados);

        // 4. Instanciar a Interface do Console
        // A interface recebe todas as classes de gerenciamento para poder chamá-las.
        MenuInterface menu = new MenuInterface(estoqueManager, dados, vendaManager);

        // 5. Iniciar o Loop Principal do Menu
        // O programa fica rodando dentro deste método até o usuário escolher "Sair".
        menu.iniciarMenu(); 
        
        // 6. Salvamento Final (Shutdown Hook)
        // O salvamento é a última ação antes de o programa terminar, garantindo a permanência
        // das últimas alterações no estoque, caso não tenham sido salvas durante a operação.
        System.out.println("\n[PASSO DE ENCERRAMENTO]");
        System.out.println("Salvando dados finais antes de sair...");
        dados.salvarEstoque(estoqueManager.getEstoqueParaSalvar());
        
        System.out.println("\nObrigado por usar o sistema! Encerrando.");
    }
}