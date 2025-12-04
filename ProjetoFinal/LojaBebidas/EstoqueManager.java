package src.loja;
import java.util.ArrayList;
import java.util.List;

public class EstoqueManager {
    // A coleção principal que armazena todas as bebidas
    // Usamos List para simplificar a manipulação sem a necessidade de Hashing
    private List<Produto> estoque;

    // Construtor
    public EstoqueManager() {
        // Inicializa a lista vazia.
        this.estoque = new ArrayList<>();
    }

    // --- Métodos de Persistência (Comunicação com Formato Exato) ---

    // Método para receber os dados carregados ao iniciar o programa
    // Requisito: O formato de retorno/parâmetro será agora uma List<Produto>
    public void setEstoque(List<Produto> dadosCarregados) {
        if (dadosCarregados != null) {
            this.estoque = dadosCarregados;
            System.out.println(this.estoque.size() + " produtos carregados no estoque.");
        }
    }

    // Método para retornar todos os dados para serem salvos (ao sair ou após venda)
    // Requisito: O formato de retorno/parâmetro é uma List<Produto>
    public List<Produto> getEstoqueParaSalvar() {
        return this.estoque;
    }

    // --- Métodos de Gerenciamento do Estoque ---

    /**
     * Adiciona um novo produto ao estoque.
     * @param bebida O objeto Produto a ser adicionado.
     * @return true se adicionado, false se o código já existir.
     */
    public boolean adicionarProduto(Produto bebida) {
        // Verifica se o código já existe antes de adicionar
        if (buscarProduto(bebida.getCodigo()) != null) {
            System.err.println("Erro: Código " + bebida.getCodigo() + " já existe no estoque.");
            return false;
        }
        this.estoque.add(bebida);
        return true;
    }

    /**
     * Busca um produto pelo código, iterando pela lista.
     * @param codigo O código da bebida a buscar.
     * @return O objeto Bebida encontrado, ou null se não existir.
     * (Formato exato de retorno: Bebida)
     */
    public Produto buscarProduto(int codigo) {
        // Itera sobre a lista até encontrar o produto com o código desejado
        for (Produto produto : this.estoque) {
            if (produto.getCodigo() == codigo) {
                return produto; // Encontrado!
            }
        }
        return null; // Não encontrado
    }
    
    /**
     * Altera a quantidade de um produto existente.
     * @param codigo O código da bebida.
     * @param novaQuantidade O novo valor total em estoque.
     * @return true se o produto foi encontrado e modificado.
     */
    public boolean modificarEstoque(int codigo, int novaQuantidade) {
        Produto produto = buscarProduto(codigo);
        if (produto != null) {
            produto.setQuantidadeEmEstoque(novaQuantidade);
            System.out.println("Estoque de " + produto.getNome() + " atualizado para " + novaQuantidade);
            return true;
        }
        System.err.println("Erro: Produto com código " + codigo + " não encontrado.");
        return false;
    }
    
    /**
     * Remove uma quantidade do estoque após uma venda.
     * @param codigo O código da bebida.
     * @param quantidadeVendida A quantidade a ser removida.
     * @return true se a remoção foi bem-sucedida (há estoque suficiente).
     */
    public boolean removerDoEstoque(int codigo, int quantidadeVendida) {
        Produto produto = buscarProduto(codigo);

        if (produto == null) {
            System.err.println("Venda Falhou: Produto não encontrado.");
            return false;
        }

        int estoqueAtual = produto.getQuantidadeEmEstoque();

        if (estoqueAtual < quantidadeVendida) {
            System.err.println("Venda Falhou: Estoque insuficiente. Disponível: " + estoqueAtual);
            return false;
        }

        // Atualiza a quantidade diretamente no objeto Bebida
        produto.setQuantidadeEmEstoque(estoqueAtual - quantidadeVendida);
        return true;
    }

    /**
     * Exibe todos os produtos do estoque no console.
     */
    public void listarEstoque() {
        System.out.println("\n--- ESTOQUE ATUAL ---");
        if (this.estoque.isEmpty()) {
            System.out.println("O estoque está vazio.");
            return;
        }

        for (Produto b : this.estoque) {
            System.out.println(
                "Cod: " + b.getCodigo() + 
                " | Nome: " + b.getNome() + 
                " | Preço: R$" + String.format("%.2f", b.getPrecoUnitario()) + 
                " | Qtd: " + b.getQuantidadeEmEstoque()
            );
        }
        System.out.println("\n--- Pressione 0 e Enter para voltar ---");

        
    }
}
