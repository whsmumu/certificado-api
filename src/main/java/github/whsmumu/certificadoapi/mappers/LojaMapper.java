package github.whsmumu.certificadoapi.mappers;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import github.whsmumu.certificadoapi.dto.LojaRequestDTO;
import github.whsmumu.certificadoapi.dto.LojaResponseDTO;
import github.whsmumu.certificadoapi.model.Loja;

@Mapper(componentModel = "spring")
public interface LojaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "prazoCertificado", ignore = true)
    @Mapping(target = "resultadoProcesso", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "historicos", ignore = true)
    Loja toEntity(LojaRequestDTO lojaRequestDTO);

    @Mapping(target = "prazoCertificado", ignore = true)
    @Mapping(target = "resultadoProcesso", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "certificadoRecebido", ignore = true)
    @Mapping(target = "codigoLoja", ignore = true)
    @Mapping(target = "enviadoFiscal", ignore = true)
    @Mapping(target = "lojaEnviado", ignore = true)
    @Mapping(target = "prazoExpiracaoCertificado", ignore = true)
    @Mapping(target = "nomeLoja", ignore = true)
    @Mapping(target = "historicos", ignore = true)
    Loja toEntityId(UUID id);

    LojaResponseDTO toDTO(Loja loja);

}
