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

    // tá sendo usado
    public List<ItemDeCompra> getItens() {
        return itens;
    }

    // tá sendo usado
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

    public void removerItem(int codigoProduto, int quantidade) {
        for (ItemDeCompra item : itens) {
            if (item.getProduto().getCodigoP() == codigoProduto) {
                int quantidadeAtual = item.getQuantidade();
                if (quantidade > quantidadeAtual) {
                    System.out.println("Não é possível remover mais itens do que você tem.");
                    return;
                } else if (quantidadeAtual == quantidade) {
                    itens.remove(item);
                    System.out.println("Produto: " + item.getProduto().getNome() + " removido com sucesso!");
                } else {
                    item.setQuantidade(quantidadeAtual - quantidade);
                    System.out.println("Quantidade removida: " + quantidade + " do produto: " + item.getProduto().getNome());
                }
                return;
            }
        }
        // Se o item não for encontrado, exibe uma mensagem
        System.out.println("Produto não encontrado no carrinho.");
    }

    // tá sendo usado
    public double calcularTotal() {
        double subtotal = 0.0;
        for (ItemDeCompra item : itens) {
            subtotal += item.getProduto().getPreco() * item.getQuantidade();
        }
        // Taxa de entrega
        double taxaEntrega = 7.0;
        return subtotal + taxaEntrega;
    }

    // tá sendo usado
    public double calcularSubtotal() {
        List<ItemDeCompra> itens = getItens();
        double subtotal = 0.0;
        for (ItemDeCompra item : itens) {
            subtotal += item.getSubTotal();
        }
        return subtotal;
    }

    // tá sendo usado
    public boolean isVazio() {
        return itens.isEmpty();
    }

    // tá sendo usado
    public void limparCarrinho(){
        itens.clear();
    }

}
