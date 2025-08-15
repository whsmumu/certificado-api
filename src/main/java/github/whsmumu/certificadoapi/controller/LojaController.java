package github.whsmumu.certificadoapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import github.whsmumu.certificadoapi.dto.LojaRequestDTO;
import github.whsmumu.certificadoapi.dto.LojaResponseDTO;
import github.whsmumu.certificadoapi.mappers.LojaMapper;
import github.whsmumu.certificadoapi.model.Loja;
import github.whsmumu.certificadoapi.service.LojaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lojas")
public class LojaController {

    private final LojaService lojaService;
    private final LojaMapper lojaMapper;


    @PostMapping
    public ResponseEntity<LojaResponseDTO> criarLoja(@RequestBody @Valid LojaRequestDTO lojaRequestDTO) {
       
        Loja lojaEntity = lojaMapper.toEntity(lojaRequestDTO);
        lojaEntity = lojaService.saveOrUpdate(lojaEntity);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(lojaEntity.getId())
                .toUri();

        return ResponseEntity.created(uri).body(lojaMapper.toDTO(lojaEntity));
       
    }

    @GetMapping()
    public ResponseEntity <List<LojaResponseDTO>> listarLojas() {
        List<Loja> lojas = lojaService.findAll();
        List<LojaResponseDTO> lojaResponse = lojas.stream()
                .map(lojaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(lojaResponse);
  
    }

    @GetMapping("/{id}")
    public ResponseEntity<LojaResponseDTO> findById(@PathVariable UUID id) {
        Loja lojaEntity = lojaMapper.toEntityId(id);
        lojaEntity = lojaService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(lojaMapper.toDTO(lojaEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLoja(@PathVariable UUID id) {
        lojaService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<LojaResponseDTO> atualizarLoja(@PathVariable UUID id, @RequestBody @Valid LojaRequestDTO lojaRequestDTO) {
        Loja lojaEntity = lojaMapper.toEntity(lojaRequestDTO);
        lojaEntity.setId(id);
        Loja lojaSalva = lojaService.saveOrUpdate(lojaEntity);
        
        return ResponseEntity.status(HttpStatus.OK).body(lojaMapper.toDTO(lojaSalva));
    }
}
