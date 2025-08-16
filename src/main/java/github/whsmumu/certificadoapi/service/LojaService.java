package github.whsmumu.certificadoapi.service;

import github.whsmumu.certificadoapi.enums.StatusNotificacao;
import github.whsmumu.certificadoapi.enums.StatusPrazo;
import github.whsmumu.certificadoapi.model.Loja;
import github.whsmumu.certificadoapi.repository.LojaRepository;
import github.whsmumu.certificadoapi.validator.LojaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LojaService {

    private final LojaRepository lojaRepository;
    private final LojaValidator lojaValidator;

    public List<Loja> findAll() {
        return lojaRepository.findAll();
    }

    public Loja findById(UUID id) {
        return lojaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Loja não encontrada com o ID: " + id));
    }

    @Transactional
    public Loja saveOrUpdate(Loja loja) {
        if (loja.getId() != null) {
            Loja lojaExistente = findById(loja.getId());
            updateLoja(lojaExistente, loja);
            loja = lojaExistente;
        }

        lojaValidator.validate(loja);

        calcularEdefinirStatusPrazo(loja);
        definirResultadoDoProcesso(loja);

        return lojaRepository.save(loja);
    }

    @Transactional
    public void deleteById(UUID id) {
        if (!lojaRepository.existsById(id)) {
            throw new NoSuchElementException("Loja não encontrada com o ID: " + id);
        }
        lojaRepository.deleteById(id);
    }

    private void calcularEdefinirStatusPrazo(Loja loja) {
        if (loja.getPrazoExpiracaoCertificado().isBefore(LocalDate.now())
                || loja.getPrazoExpiracaoCertificado() == null) {
            throw new IllegalArgumentException("Deve inserir data de expiração valida para o certificado!");
        }

        LocalDate dataHoje = LocalDate.now();
        LocalDate dataExpiracaoCertificado = loja.getPrazoExpiracaoCertificado();

        long diasParaExpiracao = ChronoUnit.DAYS.between(dataHoje, dataExpiracaoCertificado);

        if (diasParaExpiracao <= 0) {
            loja.setPrazoCertificado(StatusPrazo.EXPIRADO);
        } else if (diasParaExpiracao <= 30) {
            loja.setPrazoCertificado(StatusPrazo.PROXIMO_AO_VENCIMENTO);
        } else {
            loja.setPrazoCertificado(StatusPrazo.NO_PRAZO);
        }
    }

    private void definirResultadoDoProcesso(Loja loja) {
        if (loja.getLojaEnviado() == StatusNotificacao.ENVIADO
                && loja.getCertificadoRecebido() == StatusNotificacao.RECEBIDO
                && loja.getEnviadoFiscal() == StatusNotificacao.ENVIADO) {
            loja.setResultadoProcesso(StatusNotificacao.CONCLUIDO);
            loja.setPrazoCertificado(StatusPrazo.CONCLUIDO);
        } else {
            loja.setResultadoProcesso(StatusNotificacao.PENDENTE);
        }
    }

    private void updateLoja(Loja lojaExistente, Loja lojaDadosNovos) {
        lojaExistente.setNomeLoja(lojaDadosNovos.getNomeLoja());
        lojaExistente.setCodigoLoja(lojaDadosNovos.getCodigoLoja());
        lojaExistente.setPrazoExpiracaoCertificado(lojaDadosNovos.getPrazoExpiracaoCertificado());
        lojaExistente.setLojaEnviado(lojaDadosNovos.getLojaEnviado());
        lojaExistente.setCertificadoRecebido(lojaDadosNovos.getCertificadoRecebido());
        lojaExistente.setEnviadoFiscal(lojaDadosNovos.getEnviadoFiscal());

    }

    public Loja iniciarInstalacao(UUID id) {
        Loja loja = lojaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loja com ID " + id + " não encontrada."));

        loja.setResultadoProcesso(StatusNotificacao.EM_ANDAMENTO);
        lojaRepository.save(loja);

        new Thread(() -> {
            try {
                Thread.sleep(5000);

                Loja lojaProcessada = lojaRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Loja desapareceu durante o processamento."));
                ;

                lojaProcessada.setResultadoProcesso(StatusNotificacao.CONCLUIDO);
                lojaRepository.save(lojaProcessada);

            } catch (InterruptedException e) {
                Loja lojaComFalha = lojaRepository.findById(id).get();
                lojaComFalha.setResultadoProcesso(StatusNotificacao.FALHA);
                lojaRepository.save(lojaComFalha);
                Thread.currentThread().interrupt();
            }
        }).start();

        return loja;
    }
}
