package br.com.kldoces.pacotes.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "tb_itens_compra")
@Getter
@Setter
@NoArgsConstructor
public class ItensCompra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "compra_id", referencedColumnName = "id")
    private Compra compraId;
    private int codigoProduto;
    private String nomeProduto;
    private int quantidade;
    private double precoUnitario;
    private double subTotal;
}