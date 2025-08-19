package github.whsmumu.certificadoapi.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import github.whsmumu.certificadoapi.enums.StatusNotificacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LojaRequestDTO(

    @NotBlank(message = "O nome da loja é obrigatório")
    String nomeLoja,

    @NotBlank(message = "O código da loja é obrigatório")
    @Size(max = 5, message = "O código da loja deve ter no máximo 5 caracteres")
    String codigoLoja,

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "O prazo de expiração do certificado é obrigatório")
    LocalDate prazoExpiracaoCertificado,

    StatusNotificacao lojaEnviado,
    StatusNotificacao certificadoRecebido,
    StatusNotificacao enviadoFiscal
) {

}
