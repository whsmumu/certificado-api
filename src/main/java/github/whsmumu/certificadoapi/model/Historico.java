package github.whsmumu.certificadoapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import github.whsmumu.certificadoapi.enums.StatusNotificacao;

@Entity
@Table(name = "historicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Historico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String tecnicoResponsavel;

    private List<String> acompanhantes;

    @Column(nullable = false)
    private LocalDate dataInstalacao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusNotificacao statusInstalacao;

    private List<String> sistemasParaInstalarCertificado;

    @ManyToOne
    @JoinColumn(name = "loja_id")
    private Loja loja;

 
}