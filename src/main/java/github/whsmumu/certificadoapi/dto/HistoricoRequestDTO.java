package github.whsmumu.certificadoapi.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import github.whsmumu.certificadoapi.enums.StatusNotificacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HistoricoRequestDTO(

    @NotBlank(message = "O nome do tecnico é obrigatório")
    String tecnicoResponsavel,

    List<String> acompanhantes,

    @NotNull(message = "Os sistemas para instalar o certificado são obrigatórios")
    List<SistemaRequestDTO> sistemasParaInstalarCertificado,

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "A data de instalação é obrigatória")
    LocalDate dataInstalacao,

    @NotNull(message = "O status da instalação é obrigatório")
    StatusNotificacao statusInstalacao,

    @NotNull(message = "A loja é obrigatória")
    UUID idLoja,

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "O prazo de expiração do certificado é obrigatório")
    LocalDate prazoExpiracaoCertificado

) {


}
