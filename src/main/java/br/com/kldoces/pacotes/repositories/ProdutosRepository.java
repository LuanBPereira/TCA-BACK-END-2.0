package br.com.kldoces.pacotes.repositories;

import br.com.kldoces.pacotes.models.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<Produtos, Integer> {

    Produtos findByCodigoP(Integer codigoP);
}
