package br.com.kldoces.pacotes.controllers;

import br.com.kldoces.pacotes.models.Produtos;
import br.com.kldoces.pacotes.repositories.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProdutosController {

    @Autowired
    private ProdutosRepository pr;

    @GetMapping("/produtos")
    public List<Produtos> getProdutosList(){
        System.out.println("\nProdutos enviados para o localhost:8080/produtos");
        return pr.findAll();
    }

}
