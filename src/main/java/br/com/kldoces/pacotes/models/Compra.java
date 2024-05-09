package br.com.kldoces.pacotes.models;

import br.com.kldoces.pacotes.services.CarrinhoDeCompras;
import br.com.kldoces.pacotes.services.ItemDeCompra;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_compra")
@Getter
@Setter
@AllArgsConstructor
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime dataHora;
    private double valorTotalProdutos;
    private double taxaEntrega;
    private double valorTotalCompra;
    private String formaPagamento;

    public Compra(String formaPagamento, CarrinhoDeCompras carrinho) {
        this.dataHora = LocalDateTime.now();
        this.valorTotalProdutos = carrinho.calcularSubtotal(); ;
        this.taxaEntrega = 7.00;
        this.valorTotalCompra = carrinho.calcularTotal();
        this.formaPagamento = formaPagamento;
    }
}