package github.whsmumu.certificadoapi.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import github.whsmumu.certificadoapi.model.Historico;
import github.whsmumu.certificadoapi.repository.HistoricoRepository;
import github.whsmumu.certificadoapi.validator.HistoricoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoricoService {

    private final HistoricoRepository historicoRepository;
    private final HistoricoValidator historicoValidator;

    public List<Historico> findAll(){
        return historicoRepository.findAll();
    }

    public Historico findById(UUID id){
        return historicoRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Historico não encontrado"));
    }

    @Transactional
    public Historico saveOrUpdate(Historico historico){
        if (historico.getId() != null) {
            Historico historicoExistente = findById(historico.getId());
            updateHistorico(historicoExistente, historico);
            historico = historicoExistente;
        }

        historicoValidator.validate(historico);

        return historicoRepository.save(historico);
    }

    @Transactional
    public void deleteById(UUID id){
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
    }
}
