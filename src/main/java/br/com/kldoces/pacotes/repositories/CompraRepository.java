package br.com.kldoces.pacotes.repositories;

import br.com.kldoces.pacotes.models.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Integer> {
}
