package github.whsmumu.certificadoapi.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import github.whsmumu.certificadoapi.enums.StatusNotificacao;
import github.whsmumu.certificadoapi.model.Historico;
import github.whsmumu.certificadoapi.model.Loja;
import github.whsmumu.certificadoapi.repository.HistoricoRepository;
import github.whsmumu.certificadoapi.validator.HistoricoValidator;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoricoService {

    private final HistoricoRepository historicoRepository;
    private final HistoricoValidator historicoValidator;
    private final LojaService lojaService;

    @Transactional(readOnly = true)
    public List<Historico> findAll() {
        return historicoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Historico findById(UUID id) {
        return historicoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Historico não encontrado"));
    }

    @Transactional
    public Historico saveOrUpdate(Historico historico) {

        Loja loja = lojaService.findById(historico.getLoja().getId());
        historico.setLoja(loja);

        if (historico.getId() != null) {
            Historico historicoExistente = findById(historico.getId());
            updateHistorico(historicoExistente, historico);
            historico = historicoExistente;
        }

        historicoValidator.validate(historico);

        return historicoRepository.save(historico);
    }

    @Transactional
    public void deleteById(UUID id) {
        if (!historicoRepository.existsById(id)) {
            throw new NoSuchElementException("Historico não encontrado");
        }

        historicoRepository.deleteById(id);
    }

    private void updateHistorico(Historico historicoExistente, Historico historicoDadosNovos) {
        historicoExistente.setDataInstalacao(historicoDadosNovos.getDataInstalacao());
        historicoExistente.setAcompanhantes(historicoDadosNovos.getAcompanhantes());
        historicoExistente.setTecnicoResponsavel(historicoDadosNovos.getTecnicoResponsavel());
        historicoExistente.setLoja(historicoDadosNovos.getLoja());
        historicoExistente.setStatusInstalacao(historicoDadosNovos.getStatusInstalacao());

        if (historicoExistente.getSistemasParaInstalarCertificado() != null) {
            historicoExistente.getSistemasParaInstalarCertificado().clear();
        }

        if (historicoDadosNovos.getSistemasParaInstalarCertificado() != null) {
            historicoExistente.getSistemasParaInstalarCertificado()
                    .addAll(historicoDadosNovos.getSistemasParaInstalarCertificado());
        }

        boolean sistemasConcluidos = historicoDadosNovos.getSistemasParaInstalarCertificado() != null &&
                !historicoDadosNovos.getSistemasParaInstalarCertificado().isEmpty() &&
                historicoDadosNovos.getSistemasParaInstalarCertificado().stream()
                        .allMatch(sistema -> sistema.getStatus() == StatusNotificacao.CONCLUIDO);

        if (sistemasConcluidos) {
            historicoExistente.setStatusInstalacao(StatusNotificacao.CONCLUIDO);
        } else {
            historicoExistente.setStatusInstalacao(StatusNotificacao.PENDENTE);
        }
    }
}
