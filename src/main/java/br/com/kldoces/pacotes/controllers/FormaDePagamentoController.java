package br.com.kldoces.pacotes.controllers;

import br.com.kldoces.pacotes.models.Compra;
import br.com.kldoces.pacotes.models.FormaDePagamento;
import br.com.kldoces.pacotes.models.ItensCompra;
import br.com.kldoces.pacotes.repositories.CompraRepository;
import br.com.kldoces.pacotes.repositories.ItensCompraRepository;
import br.com.kldoces.pacotes.services.CarrinhoDeCompras;
import br.com.kldoces.pacotes.services.ItemDeCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("pagamento")
public class FormaDePagamentoController {

    @Autowired
    private CarrinhoDeCompras carrinhoDeCompras;

    @Autowired
    private CompraRepository cr;

    @Autowired
    private ItensCompraRepository icr;

    @PostMapping("/{tipoPagamento}")
    public ResponseEntity<String> realizarPagamento(@PathVariable("tipoPagamento") String tipoPagamento) {
        // verifica se o carrinho está vazio
        // caso esteja, não irá prosseguir com a função do pagamento
        if (carrinhoDeCompras.getItens().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não é possível finalizar a compra: carrinho de compras vazio.");
        }

        FormaDePagamento formaDePagamento = FormaDePagamento.valueOf(tipoPagamento.toUpperCase());
        FormaDePagamento.realizarPagamento(formaDePagamento);

        Compra compra = new Compra();
        List<ItemDeCompra> itensCompra = carrinhoDeCompras.getItens();

        Compra compraSalva = cr.save(compra);

        for (ItemDeCompra item : itensCompra) {
            ItensCompra itemCompra = new ItensCompra();
            itemCompra.setCompraId(compraSalva);
            itemCompra.setCodigoProduto(item.getProduto().getCodigoP());
            itemCompra.setNomeProduto(item.getProduto().getNome());
            itemCompra.setQuantidade(item.getQuantidade());
            itemCompra.setPrecoUnitario(item.getProduto().getPreco());
            itemCompra.setSubTotal(item.getSubTotal());

            icr.save(itemCompra);
        }

        // Limpar o carrinho após o pagamento ser realizado com sucesso
        carrinhoDeCompras.limparCarrinho();

        return ResponseEntity.ok("Pagamento realizado com sucesso!");
    }

}
