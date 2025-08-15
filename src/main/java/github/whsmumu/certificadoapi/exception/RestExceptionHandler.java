package github.whsmumu.certificadoapi.exception;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .tituloErro("Recurso Não Encontrado")
                .codigoStatusHTTP(HttpStatus.NOT_FOUND.value())
                .detalhesExcecao(ex.getMessage())
                .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .tituloErro("Requisição Inválida")
                .codigoStatusHTTP(HttpStatus.BAD_REQUEST.value())
                .detalhesExcecao(ex.getMessage())
                .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .tituloErro("Erro Interno no Servidor")
                .codigoStatusHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detalhesExcecao("Ocorreu um erro inesperado.")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

    List<String> errorMessages = ex.getBindingResult().getFieldErrors()
            .stream()
            .map(fieldError -> String.format("'%s': %s", fieldError.getField(), fieldError.getDefaultMessage()))
            .collect(Collectors.toList());

    String detailedMessage = "Erro de validação: " + String.join(", ", errorMessages);

    ErrorResponse errorResponse = ErrorResponse.builder()
            .tituloErro("Dados da Requisição Inválidos")
            .codigoStatusHTTP(HttpStatus.BAD_REQUEST.value())
            .detalhesExcecao(detailedMessage)
            .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
}

}
