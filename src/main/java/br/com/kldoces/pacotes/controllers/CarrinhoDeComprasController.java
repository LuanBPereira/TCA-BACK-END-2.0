package br.com.kldoces.pacotes.controllers;

import br.com.kldoces.pacotes.models.Produtos;
import br.com.kldoces.pacotes.services.CarrinhoDeCompras;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoDeComprasController {

    @Autowired
    private CarrinhoDeCompras cc;

    @PostMapping("/adicionar")
    public void adicionarItemCarrinho(@RequestBody Produtos produto, @RequestParam int quantidade){
        cc.adicionarItem(produto, quantidade);
    }

    @PostMapping("/limpar")
    public void limparCarrinho(){
        cc.limparCarrinho();
    }

}
