package github.whsmumu.certificadoapi.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import github.whsmumu.certificadoapi.enums.StatusNotificacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LojaRequestDTO(

    @NotBlank(message = "O nome da loja é obrigatório")
    String nomeLoja,

    @NotBlank(message = "O código da loja é obrigatório")
    String codigoLoja,

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "O prazo de expiração do certificado é obrigatório")
    LocalDate prazoExpiracaoCertificado,

    StatusNotificacao lojaEnviado,
    StatusNotificacao certificadoRecebido,
    StatusNotificacao enviadoFiscal
) {

}
