package github.whsmumu.certificadoapi.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String tituloErro;
    private int codigoStatusHTTP;
    private String detalhesExcecao;

}
