import java.util.ArrayList;
import java.util.List;

public class GerenciadorEstoque {
    // Lista para armazenar todos os objetos Produto
    private List<Produto> estoque;

    public GerenciadorEstoque() {
        this.estoque = new ArrayList<>();
    }

    // --- Métodos Essenciais ---

    /**
     * Adiciona um novo produto ao estoque
     */
    public void adicionarProduto(Produto produto) {
        // Você pode adicionar lógica aqui para checar se o código já existe
        estoque.add(produto);
        System.out.println("Produto '" + produto.getNome() + "' adicionado com sucesso.");
    }

    /**
     * Lista todos os produtos no estoque
     */
    public void listarProdutos() {
        if (estoque.isEmpty()) {
            System.out.println("O estoque está vazio.");
            return;
        }
        System.out.println("\n--- LISTA DE PRODUTOS NO ESTOQUE ---");
        for (Produto p : estoque) {
            System.out.println(p.toString());
        }
    }

    /**
     * Busca um produto pelo código
     * @return O objeto Produto se encontrado, ou null caso contrário.
     */
    public Produto buscarProduto(String codigo) {
        for (Produto p : estoque) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    // ... Outros métodos de gerenciamento viriam aqui (atualizar, remover, etc.)
}