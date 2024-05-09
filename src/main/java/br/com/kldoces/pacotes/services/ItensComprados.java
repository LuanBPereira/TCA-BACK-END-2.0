package br.com.kldoces.pacotes.services;

import br.com.kldoces.pacotes.models.Compra;
import br.com.kldoces.pacotes.models.ItensCompra;
import org.springframework.stereotype.Service;

@Service
public class ItensComprados {

    public ItensCompra criarItemCompra(Compra compra, ItemDeCompra item) {
        ItensCompra itemCompra = new ItensCompra();
        itemCompra.setCompraId(compra);
        itemCompra.setCodigoProduto(item.getProduto().getCodigoP());
        itemCompra.setNomeProduto(item.getProduto().getNome());
        itemCompra.setQuantidade(item.getQuantidade());
        itemCompra.setPrecoUnitario(item.getProduto().getPreco());
        itemCompra.setSubTotal(item.getSubTotal());
        return itemCompra;
    }

}
