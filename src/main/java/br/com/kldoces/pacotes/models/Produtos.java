package br.com.kldoces.pacotes.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_produtos")
public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoP;
    private String nome;
    private double preco;


    public Integer getCodigoP() {
        return codigoP;
    }

    public void setCodigoP(Integer codigoP) {
        this.codigoP = codigoP;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

}
