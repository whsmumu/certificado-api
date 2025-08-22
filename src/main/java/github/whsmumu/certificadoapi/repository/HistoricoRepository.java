package github.whsmumu.certificadoapi.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import github.whsmumu.certificadoapi.model.Historico;

public interface HistoricoRepository extends JpaRepository<Historico, UUID> {

    Optional<Historico> findByDataInstalacaoAndLoja_IdAndIdNot(LocalDate dataInstalacao, UUID lojaId, UUID id);

    Optional<Historico> findByDataInstalacao(LocalDate dataInstalacao);

    Optional<Historico> findByDataInstalacaoAndLoja_IdAndPrazoExpiracaoCertificado(LocalDate dataInstalacao, UUID lojaId, LocalDate prazoExpiracaoCertificado);

}
