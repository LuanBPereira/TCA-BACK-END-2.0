package br.com.kldoces.pacotes.services;

import br.com.kldoces.pacotes.models.Produtos;

public class ItemDeCompra {

    private Produtos produto;
    private int quantidade;

    public ItemDeCompra(Produtos produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produtos getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getSubTotal(){
        return produto.getPreco() * quantidade;
    }
}
