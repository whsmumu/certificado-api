package github.whsmumu.certificadoapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import github.whsmumu.certificadoapi.model.Loja;

public interface LojaRepository extends JpaRepository<Loja, UUID>{

    Optional<Loja> findByNomeLoja(String nomeLoja);
    Optional<Loja> findByCodigoLoja(String codigoLoja);


}
