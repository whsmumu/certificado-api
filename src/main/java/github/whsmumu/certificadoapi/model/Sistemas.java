package github.whsmumu.certificadoapi.model;

import github.whsmumu.certificadoapi.enums.StatusNotificacao;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sistemas")
public class Sistemas {

    @Column(name = "sistema", nullable = false)
    private String nomeSistema;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusNotificacao status;


}
