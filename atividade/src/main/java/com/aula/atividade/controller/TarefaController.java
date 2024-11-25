package com.aula.atividade.controller;

import com.aula.atividade.dto.TarefaDto;
import com.aula.atividade.dto.UsuarioDto;
import com.aula.atividade.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/tarefas")
public class TarefaController {
    private final TarefaService tarefaService;

    @Operation(description = "Lista todos tarefas", summary= "Listar tarefas")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<TarefaDto>> listar(@Parameter(required = true) @PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok(tarefaService.listar(pageable));
    }

    @Operation(description = "Cadastra uma nova tarefa", summary= "Cadastra tarefa")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TarefaDto> save(@Valid @RequestBody TarefaDto tarefaDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaService.cadastrar(tarefaDto));
    }
}
