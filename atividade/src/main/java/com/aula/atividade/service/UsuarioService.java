package com.aula.atividade.service;

import com.aula.atividade.dto.TarefaDto;
import com.aula.atividade.dto.UsuarioDto;
import com.aula.atividade.model.Tarefa;
import com.aula.atividade.model.Usuario;
import com.aula.atividade.repository.TarefaRepository;
import com.aula.atividade.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TarefaRepository tarefaRepository;

    public Page<UsuarioDto> findALL(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(UsuarioDto::new);
    }

    public UsuarioDto save(UsuarioDto usuarioDto) {
        // Converte o DTO para a entidade Usuario, inicialmente sem tarefas
        Usuario usuario = Usuario.fromDto(usuarioDto);
        usuario.setTarefas(null); // Remove as tarefas temporariamente para evitar inconsistências

        // Salva o usuário para garantir que tenha um ID
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        // Verifica se existem tarefas associadas
        if (usuarioDto.tarefas() != null && !usuarioDto.tarefas().isEmpty()) {
            // Converte as tarefas recebidas no DTO para a entidade e associa o usuário salvo
            List<Tarefa> tarefas = usuarioDto.tarefas().stream()
                    .map(tarefaDto -> {
                        Tarefa tarefa = new Tarefa(); // Cria a tarefa manualmente
                        tarefa.setTitulo(tarefaDto.getTitulo());
                        tarefa.setDescricao(tarefaDto.getDescricao());
                        tarefa.setDataCriacao(tarefaDto.getDataCriacao());
                        tarefa.setExcluida(tarefaDto.getExcluida());
                        tarefa.setUsuario(usuarioSalvo); // Associa ao usuário salvo
                        return tarefa;
                    })
                    .toList();

            // Salva todas as tarefas no banco
            List<Tarefa> tarefasSalvas = tarefaRepository.saveAll(tarefas);

            // Atualiza o usuário com as tarefas salvas
            usuarioSalvo.setTarefas(tarefasSalvas);
            usuarioRepository.save(usuarioSalvo); // Atualiza o usuário no banco
        }

        // Retorna o DTO do usuário salvo
        return new UsuarioDto(usuarioSalvo);
    }




    public UsuarioDto update(String id, UsuarioDto usuarioDto) {
        Usuario usuario = Usuario.fromDto(usuarioDto);
        usuario.setId(id);
        return new UsuarioDto(usuarioRepository.save(usuario));
    }
}
