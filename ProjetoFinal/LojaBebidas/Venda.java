package src.loja;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venda {
    // Armazena todos os itens vendidos na transação
    private List<ItemVenda> itens;
    private double valorTotal;
    private Date dataHora;

    // Construtor
    public Venda() {
        this.itens = new ArrayList<>();
        this.valorTotal = 0.0;
        this.dataHora = new Date(); // Registra o momento da criação
    }

    // --- Métodos de Lógica (Simples, para manter o encapsulamento) ---
    
    // Adiciona um item e atualiza o total automaticamente
    public void adicionarItem(ItemVenda item) {
        this.itens.add(item);
        this.valorTotal += item.getSubtotal();
    }

    // --- Getters ---
    public List<ItemVenda> getItens() {
        return itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public Date getDataHora() {
        return dataHora;
    }
}