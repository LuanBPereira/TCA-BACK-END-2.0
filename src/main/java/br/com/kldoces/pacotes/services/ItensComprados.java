package br.com.kldoces.pacotes.services;

import br.com.kldoces.pacotes.models.ItensCompra;
import br.com.kldoces.pacotes.repositories.ItensCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItensComprados {

    @Autowired
    private ItensCompraRepository icr;

    public ItensCompra salvarItensComprados(ItensCompra itensCompra){
        return icr.save(itensCompra);
    }
}
