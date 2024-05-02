package br.com.kldoces.pacotes.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_compra")
@Getter
@Setter
@NoArgsConstructor
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;
    private double valorTotalProdutos;
    private double taxaEntrega;
    private double valorTotalCompra;
    private String formaPagamento;
}