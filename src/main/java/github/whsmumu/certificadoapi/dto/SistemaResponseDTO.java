package github.whsmumu.certificadoapi.dto;

import github.whsmumu.certificadoapi.enums.StatusNotificacao;

public record SistemaResponseDTO(

    String nomeSistema,
    StatusNotificacao status

) {

}
