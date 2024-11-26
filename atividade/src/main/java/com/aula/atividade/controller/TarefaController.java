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

import java.util.List;

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

    /**
     * Endpoint para listar todas as tarefas excluídas.
     *
     * @return Lista de TarefaDto representando as tarefas excluídas.
     */
    @Operation(summary = "Listar Tarefas Excluídas", description = "Retorna todas as tarefas que estão marcadas como excluídas.")
    @GetMapping("/excluidas")
    public ResponseEntity<List<TarefaDto>> listarTarefasExcluidas() {
        List<TarefaDto> tarefasExcluidas = tarefaService.listarTarefasExcluidas();
        return ResponseEntity.ok(tarefasExcluidas);
    }

    /**
     * Endpoint para listar as tarefas por titulo.
     *
     * @return Lista de TarefaDto representando as tarefas que contem o titulo buscado.
     */
    @Operation(summary = "Listar Tarefas por titulo", description = "Retorna todas as tarefas buscadas por titulo.")
    @GetMapping("/{titulo}")
    public ResponseEntity<List<TarefaDto>> listarTarefasPorTitulo(@PathVariable String titulo) {
        List<TarefaDto> tarefas = tarefaService.listarTarefasPorTitulo(titulo);
        return ResponseEntity.ok(tarefas);
    }

    @Operation(description = "Cadastra uma nova tarefa se o usuario já existir no banco", summary= "Cadastra tarefa")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TarefaDto> save(@Valid @RequestBody TarefaDto tarefaDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaService.cadastrar(tarefaDto));
    }

    @Operation(description = "Atualiza os dados da tarefa", summary= "Atualiza tarefa")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TarefaDto> update(@PathVariable String id, @Valid @RequestBody TarefaDto tarefaDto){
        return ResponseEntity.ok(tarefaService.update(id, tarefaDto));
    }
}
