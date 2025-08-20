package github.whsmumu.certificadoapi.validator;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;

import github.whsmumu.certificadoapi.model.Historico;
import github.whsmumu.certificadoapi.repository.HistoricoRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HistoricoValidator {

    private final HistoricoRepository historicoRepository;

    public void validate(Historico historico) {
        validateDataFutura(historico);
        validateDataAndLojaDuplicate(historico);
    }

    private void validateDataFutura(Historico historico) {
        if (historico.getDataInstalacao() != null && historico.getDataInstalacao().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de instalação não pode ser futura.");
        }
    }

    private void validateDataAndLojaDuplicate(Historico historico){
        Optional<Historico> historicoExistente = historicoRepository.findByDataInstalacaoAndLoja_Id(historico.getDataInstalacao(), historico.getLoja().getId());
        if (historicoExistente.isPresent() && !historicoExistente.get().getId().equals(historico.getId())) {
            throw new IllegalArgumentException("Já existe um histórico de instalação para esta loja nesta mesma data.");
        }
    }
}
