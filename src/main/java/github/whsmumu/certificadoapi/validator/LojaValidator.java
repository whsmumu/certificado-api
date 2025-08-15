package github.whsmumu.certificadoapi.validator;

import github.whsmumu.certificadoapi.model.Loja;
import github.whsmumu.certificadoapi.repository.LojaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LojaValidator {

    private final LojaRepository lojaRepository;

    public void validate(Loja loja) {
        validateDuplicateNome(loja);
        validateDuplicateCodigo(loja);
    }


    private void validateDuplicateNome(Loja loja) {
        Optional<Loja> lojaPorNome = lojaRepository.findByNomeLoja(loja.getNomeLoja());

        if (lojaPorNome.isPresent() && !lojaPorNome.get().getId().equals(loja.getId())) {
            throw new IllegalArgumentException("Já existe uma loja com o nome informado.");
        }
    }

    private void validateDuplicateCodigo(Loja loja) {
        Optional<Loja> lojaPorCodigo = lojaRepository.findByCodigoLoja(loja.getCodigoLoja());
        
        if (lojaPorCodigo.isPresent() && !lojaPorCodigo.get().getId().equals(loja.getId())) {
            throw new IllegalArgumentException("Já existe uma loja com o código informado.");
        }
    }
}
