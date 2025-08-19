package github.whsmumu.certificadoapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import github.whsmumu.certificadoapi.dto.HistoricoRequestDTO;
import github.whsmumu.certificadoapi.dto.HistoricoResponseDTO;
import github.whsmumu.certificadoapi.model.Historico;

@Mapper(componentModel = "spring")
public interface HistoricoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "loja.id", source = "idLoja")
    Historico toEntity(HistoricoRequestDTO historicoRequestDTO);

    HistoricoResponseDTO toDTO(Historico historico);

}
