package github.whsmumu.certificadoapi.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import github.whsmumu.certificadoapi.enums.StatusNotificacao;

public record HistoricoResponseDTO(

    UUID id,
    UUID idLoja,

    String tecnicoResponsavel,
    LocalDate dataInstalacao,
    StatusNotificacao statusInstalacao,
    List<String> acompanhantes,
    List<SistemaResponseDTO> sistemasParaInstalarCertificado


) {

}
