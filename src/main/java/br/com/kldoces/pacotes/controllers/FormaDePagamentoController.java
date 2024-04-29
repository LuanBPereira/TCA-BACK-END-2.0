package br.com.kldoces.pacotes.controllers;

import br.com.kldoces.pacotes.models.FormaDePagamento;
import br.com.kldoces.pacotes.services.CarrinhoDeCompras;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pagamento")
public class FormaDePagamentoController {

    @Autowired
    CarrinhoDeCompras carrinhoDeCompras;

    @PostMapping("/{tipoPagamento}")
    public ResponseEntity<String> realizarPagamento(@PathVariable("tipoPagamento") String tipoPagamento) {
        // verifica se o carrinho está vazio
        // caso esteja, não irá prosseguir com a função do pagamento
        if (carrinhoDeCompras.getItens().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não é possível finalizar a compra: carrinho de compras vazio.");
        }

        FormaDePagamento formaDePagamento = FormaDePagamento.valueOf(tipoPagamento.toUpperCase());
        FormaDePagamento.realizarPagamento(formaDePagamento);

        // Limpar o carrinho após o pagamento ser realizado com sucesso
        carrinhoDeCompras.limparCarrinho();

        return ResponseEntity.ok("Pagamento realizado com sucesso!");
    }
}
