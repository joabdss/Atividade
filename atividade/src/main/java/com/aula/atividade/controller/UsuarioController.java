package com.aula.atividade.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.aula.atividade.dto.UsuarioDto;
import com.aula.atividade.service.UsuarioService;
import io.swagger.v3.oas.annotations.Parameter;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/usuarios")

public class UsuarioController {

    private final UsuarioService usuarioService;
    @Operation(description = "Lista todos os usuários", summary= "Listar usuarios")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<UsuarioDto>> findAll(@Parameter(required = true) @PageableDefault(size = 5)Pageable pageable){
        return ResponseEntity.ok(usuarioService.findALL(pageable));
    }

    @Operation(description = "Cadastra um novo usuário com ou sem tarefas. As tarefas são cadastradas automanticamente.", summary= "Cadastra usuario")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioDto> save(@Valid @RequestBody UsuarioDto usuarioDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuarioDto));
    }

    @Operation(description = "Atualiza os dados do usuário", summary= "Atualiza usuario")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioDto> update(@PathVariable String id, @Valid @RequestBody UsuarioDto usuarioDto){
        return ResponseEntity.ok(usuarioService.update(id, usuarioDto));
    }
}
