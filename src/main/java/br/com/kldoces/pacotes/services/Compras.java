package br.com.kldoces.pacotes.services;

import br.com.kldoces.pacotes.models.Compra;
import br.com.kldoces.pacotes.repositories.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class Compras {

    @Autowired
    private CompraRepository cr;

    public Compra salvarCompra(Compra compra){
        compra.setDataHora(LocalDateTime.now());
        return cr.save(compra);
    }

}
