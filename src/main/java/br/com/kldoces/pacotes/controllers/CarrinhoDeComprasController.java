package br.com.kldoces.pacotes.controllers;

import br.com.kldoces.pacotes.services.CarrinhoDeCompras;
import br.com.kldoces.pacotes.services.ItemDeCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("carrinho")
public class CarrinhoDeComprasController {

    @Autowired
    private CarrinhoDeCompras cc;

    @GetMapping("/listar")
    public List<ItemDeCompra> obterItensCarrinho() {
        var listar = cc.getItens();
        System.out.println("Itens listado com sucesso!");
        return listar;
    }

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

    @PostMapping("/limpar")
    public void limparCarrinho(){
        cc.limparCarrinho();
        System.out.println("Carrinho foi limpado com sucesso!");
    }

    @PostMapping("/valorSubTotal")
    public Map<String, Double> calculoSubtTotal() {
        List<ItemDeCompra> itens = cc.getItens();
        double valorSubTotal = cc.calcularSubtotal(itens);
        Map<String, Double> response = new HashMap<>();
        response.put("subTotal", valorSubTotal);
        return response;
    }

    @PostMapping("/valorTotal")
    public Map<String, Double> calculoTotal(){
        double valorTotal = cc.calcularTotal();
        Map<String, Double> response = new HashMap<>();
        response.put("total", valorTotal);
        return response;
    }

}
