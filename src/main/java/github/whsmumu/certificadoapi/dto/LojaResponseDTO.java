package github.whsmumu.certificadoapi.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import github.whsmumu.certificadoapi.enums.StatusNotificacao;
import github.whsmumu.certificadoapi.enums.StatusPrazo;

public record LojaResponseDTO(

    String nomeLoja,
    String codigoLoja,
    
    @DateTimeFormat(style = "dd/mm/yyyy")
    LocalDate prazoExpiracaoCertificado,

    StatusNotificacao lojaEnviado,
    StatusNotificacao certificadoRecebido,
    StatusNotificacao enviadoFiscal,
    StatusPrazo prazoCertificado,
    StatusNotificacao resultadoProcesso
) {

}
