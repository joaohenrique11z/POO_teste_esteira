public class Produto {
    // 1. Atributos (Variáveis de Instância)
    private String codigo;
    private String nome;
    private double preco; // Usar double para valores monetários é comum em projetos simples
    private int quantidadeEstoque;

    // 2. Construtor: Usado para criar uma nova instância de Produto
    public Produto(String codigo, String nome, double preco, int quantidadeEstoque) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    // 3. Getters (Para ler os valores dos atributos)
    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    // 4. Setters (Para modificar os valores. O preço e o estoque precisam ser alterados)
    public void setPreco(double preco) {
        this.preco = preco;
    }

    // Método para atualizar o estoque (Essencial para vendas e recebimento)
    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    // 5. Método toString(): Útil para imprimir os detalhes do produto de forma fácil
    @Override
    public String toString() {
        return "--------------------------------------------------" +
               "\nCódigo: " + codigo + 
               " | Nome: " + nome + 
               " | Preço: R$" + String.format("%.2f", preco) + 
               " | Estoque: " + quantidadeEstoque + " unidades" +
               "\n--------------------------------------------------";
    }
}
