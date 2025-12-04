package src.loja;
public class ItemVenda {
    // Comunicação entre Classes: Armazena o objeto Produto inteiro
    private Produto produto; 
    private int quantidade;
    private double subtotal;

    // Construtor
    public ItemVenda(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        // O subtotal é calculado no momento da criação
        this.subtotal = produto.getPrecoUnitario() * quantidade;
    }

    // --- Getters ---
    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getSubtotal() {
        return subtotal;
    }
    
    // Método para exibir o item na nota
    @Override
    public String toString() {
        return quantidade + "x " + produto.getNome() + " | Subtotal: R$" + String.format("%.2f", subtotal);
    }
}

