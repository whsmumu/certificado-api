package github.whsmumu.certificadoapi.dto;


import github.whsmumu.certificadoapi.enums.StatusNotificacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SistemaRequestDTO(

    @NotBlank(message = "O nome do sistema é obrigatório")
    String nomeSistema,

    @NotNull(message = "O status do sistema é obrigatório")
    StatusNotificacao status

) {

}
