package br.com.kldoces.pacotes.controllers;

import br.com.kldoces.pacotes.services.CarrinhoDeCompras;
import br.com.kldoces.pacotes.services.ItemDeCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("carrinho")
public class CarrinhoDeComprasController {

    @Autowired
    private CarrinhoDeCompras cc;

    @PostMapping("/adicionar")
    public void adicionarItemCarrinho(@RequestBody ItemDeCompra itemDeCompra){
        // log para mostrar se adicionou ou não
        if (itemDeCompra.getProduto() == null) {
            System.out.println("O produto é nulo. Não é possível adicionar ao carrinho.");
            return;
        } else {
            System.out.println("produto adicionado no carrinho com sucesso!");
        }

        cc.adicionarItem(itemDeCompra.getProduto(), itemDeCompra.getQuantidade());

    }

    @PostMapping("/valorTotal")
    public double calculoTotal(){
        double valorTotal = cc.calcularTotal();
        return valorTotal;
    }

    @GetMapping("/listar")
    public List<ItemDeCompra> obterItensCarrinho() {
        var listar = cc.getItens();
        System.out.println("Itens listado com sucesso!");
        return listar;
    }

    @PostMapping("/limpar")
    public void limparCarrinho(){
        cc.limparCarrinho();
        System.out.println("Carrinho foi limpado com sucesso!");
    }

}
