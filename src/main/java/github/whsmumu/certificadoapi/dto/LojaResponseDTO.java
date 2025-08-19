package github.whsmumu.certificadoapi.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import github.whsmumu.certificadoapi.enums.StatusNotificacao;
import github.whsmumu.certificadoapi.enums.StatusPrazo;

public record LojaResponseDTO(
    
    UUID id,
    String nomeLoja,
    String codigoLoja,
    
    @JsonFormat(pattern = "yyy-MM-dd")
    LocalDate prazoExpiracaoCertificado,

    StatusNotificacao lojaEnviado,
    StatusNotificacao certificadoRecebido,
    StatusNotificacao enviadoFiscal,
    StatusPrazo prazoCertificado,
    StatusNotificacao resultadoProcesso
) {

}
