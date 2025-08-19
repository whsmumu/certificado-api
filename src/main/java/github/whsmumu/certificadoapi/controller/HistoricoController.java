package github.whsmumu.certificadoapi.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import github.whsmumu.certificadoapi.dto.HistoricoRequestDTO;
import github.whsmumu.certificadoapi.dto.HistoricoResponseDTO;
import github.whsmumu.certificadoapi.mappers.HistoricoMapper;
import github.whsmumu.certificadoapi.model.Historico;
import github.whsmumu.certificadoapi.service.HistoricoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/historicos")
@RequiredArgsConstructor
public class HistoricoController {

    private final HistoricoService historicoService;
    private final HistoricoMapper historicoMapper;

    @PostMapping
    public ResponseEntity<HistoricoResponseDTO> criarHistorico(@RequestBody @Valid HistoricoRequestDTO historicoRequestDTO) {
        Historico historicoEntity = historicoMapper.toEntity(historicoRequestDTO);
        historicoEntity = historicoService.saveOrUpdate(historicoEntity);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(historicoEntity.getId())
                .toUri();

        return ResponseEntity.created(uri).body(historicoMapper.toDTO(historicoEntity));
    }

    @GetMapping
    public ResponseEntity<List<HistoricoResponseDTO>> listarHistoricos() {
        List<Historico> historicos = historicoService.findAll();
        List<HistoricoResponseDTO> historicoResponse = historicos.stream()
                .map(historicoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(historicoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricoResponseDTO> findById(@PathVariable UUID id) {
        Historico historicoEntity = historicoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(historicoMapper.toDTO(historicoEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarHistorico(@PathVariable UUID id) {
        historicoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoricoResponseDTO> atualizarHistorico(@PathVariable UUID id,
            @RequestBody @Valid HistoricoRequestDTO historicoRequestDTO) {
        Historico historicoEntity = historicoMapper.toEntity(historicoRequestDTO);
        historicoEntity.setId(id);
        Historico historicoSalvo = historicoService.saveOrUpdate(historicoEntity);

        return ResponseEntity.status(HttpStatus.OK).body(historicoMapper.toDTO(historicoSalvo));
    }
}
