package github.whsmumu.certificadoapi.model;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import github.whsmumu.certificadoapi.enums.StatusNotificacao;
import github.whsmumu.certificadoapi.enums.StatusPrazo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lojas")
@EntityListeners(AuditingEntityListener.class)
public class Loja {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nomeLoja;

    @Column(nullable = false, length = 5, unique = true)
    private String codigoLoja;

    @Column(nullable = false)
    private LocalDate prazoExpiracaoCertificado;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusNotificacao lojaEnviado;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusNotificacao certificadoRecebido;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusNotificacao enviadoFiscal;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPrazo prazoCertificado;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusNotificacao resultadoProcesso;

    @CreatedDate
    private LocalDate dataCadastro;


}
