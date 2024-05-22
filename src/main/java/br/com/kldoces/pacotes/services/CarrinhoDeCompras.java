package br.com.kldoces.pacotes.services;

import br.com.kldoces.pacotes.models.Produtos;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarrinhoDeCompras {
    private DecimalFormat df = new DecimalFormat("0.00");
    private List<ItemDeCompra> itens;

    public CarrinhoDeCompras() {
        this.itens = new ArrayList<>();
    }


    public List<ItemDeCompra> getItens() {
        return itens;
    }

    public void adicionarItem(Produtos produto, int quantidade) {
        // Verifica se o produto é nulo
        if (produto == null) {
            System.out.println("O produto é nulo. Não é possível adicionar ao carrinho.");
            return;
        }

        // Verifica se o produto já está no carrinho
        for (ItemDeCompra item : itens) {
            if (item.getProduto().getCodigoP().equals(produto.getCodigoP())) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                return; // Sai do método após atualizar a quantidade
            }
        }
        // Se o produto não está no carrinho, adiciona um novo item
        itens.add(new ItemDeCompra(produto, quantidade));
    }

    public void incrementarItem(int codigoProduto) {
        for (ItemDeCompra item : itens) {
            if (item.getProduto().getCodigoP() == codigoProduto) {
                item.setQuantidade(item.getQuantidade() + 1);
                System.out.println("Quantidade do produto " + item.getProduto().getNome() + " incrementada.");
                return;
            }
        }
    }

    public void decrementarItem(int codigoProduto) {
        for (ItemDeCompra item : itens) {
            if (item.getProduto().getCodigoP() == codigoProduto) {
                if (item.getQuantidade() > 1) {
                    item.setQuantidade(item.getQuantidade() - 1);
                    System.out.println("Quantidade do produto " + item.getProduto().getNome() + " decrementada.");
                } else {
                    itens.remove(item);
                    System.out.println("Produto " + item.getProduto().getNome() + " removido do carrinho.");
                }
                return;
            }
        }
    }

    public double calcularTotal() {
        double subtotal = 0.0;
        for (ItemDeCompra item : itens) {
            subtotal += item.getProduto().getPreco() * item.getQuantidade();
        }
        // Taxa de entrega
        double taxaEntrega = 7.0;
        return subtotal + taxaEntrega;
    }

    public double calcularSubtotal() {
        List<ItemDeCompra> itens = getItens();
        double subtotal = 0.0;
        for (ItemDeCompra item : itens) {
            subtotal += item.getSubTotal();
        }
        return subtotal;
    }

    public boolean isVazio() {
        return itens.isEmpty();
    }

    public void limparCarrinho(){
        itens.clear();
    }

}
