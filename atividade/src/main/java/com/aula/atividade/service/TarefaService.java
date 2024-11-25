package com.aula.atividade.service;

import com.aula.atividade.dto.TarefaDto;
import com.aula.atividade.model.Tarefa;
import com.aula.atividade.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public Page<TarefaDto> listar(Pageable pageable) {
        return tarefaRepository.findAll(pageable).map(TarefaDto::new);
    }
    /**
     * Cadastra uma nova tarefa.
     *
     * @param tarefaDto Dados da tarefa a ser cadastrada.
     * @return A tarefa cadastrada.
     */
    public TarefaDto cadastrar(TarefaDto tarefaDto) {
        // Garante que o ID seja nulo para evitar sobrescrita de dados
        Tarefa tarefa = Tarefa.fromDto(tarefaDto);
        tarefa.setExcluida(false);

        return new TarefaDto(tarefaRepository.save(tarefa));
    }


}
