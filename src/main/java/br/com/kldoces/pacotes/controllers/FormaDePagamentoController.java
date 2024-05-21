package br.com.kldoces.pacotes.controllers;

import br.com.kldoces.pacotes.models.Compra;
import br.com.kldoces.pacotes.models.FormaDePagamento;
import br.com.kldoces.pacotes.models.ItensCompra;
import br.com.kldoces.pacotes.repositories.CompraRepository;
import br.com.kldoces.pacotes.repositories.ItensCompraRepository;
import br.com.kldoces.pacotes.services.CarrinhoDeCompras;
import br.com.kldoces.pacotes.services.ItemDeCompra;
import br.com.kldoces.pacotes.services.ItensComprados;
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
    private CarrinhoDeCompras carrinhoDeCompras;

    @Autowired
    private ItensComprados ic;

    @Autowired
    private CompraRepository cr;

    @Autowired
    private ItensCompraRepository icr;

    @PostMapping("/{tipoPagamento}")
    public ResponseEntity<String>pagamento(@PathVariable("tipoPagamento") String tipoPagamento) {
        // verifica se o carrinho está vazio
        // caso esteja, não irá prosseguir com a função do pagamento
        if (carrinhoDeCompras.isVazio()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não é possível finalizar a compra: carrinho de compras vazio.");
        }

        realizarPagamento(tipoPagamento);

        Compra compra = new Compra(tipoPagamento, carrinhoDeCompras);
        Compra compraSalva = cr.save(compra);

        for (ItemDeCompra item : carrinhoDeCompras.getItens()) {
            ItensCompra itemCompra = ic.criarItemCompra(compraSalva, item);
            icr.save(itemCompra);
        }

        // Limpar o carrinho após o pagamento ser realizado com sucesso
        carrinhoDeCompras.limparCarrinho();

        return ResponseEntity.ok("Pagamento realizado com sucesso!");
    }

    private void realizarPagamento(String tipoPagamento) {
        FormaDePagamento formaDePagamento = FormaDePagamento.valueOf(tipoPagamento.toUpperCase());
        FormaDePagamento.realizarPagamento(formaDePagamento);
    }

}
