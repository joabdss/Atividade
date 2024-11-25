package com.aula.atividade.service;

import com.aula.atividade.dto.TarefaDto;
import com.aula.atividade.model.Tarefa;
import com.aula.atividade.model.Usuario;
import com.aula.atividade.repository.TarefaRepository;
import com.aula.atividade.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;

    public Page<TarefaDto> listar(Pageable pageable) {
        return tarefaRepository.findAll(pageable).map(TarefaDto::new);
    }
    /**
     * Cadastra uma nova tarefa, se o usaurio associado existir no banco.
     *
     * @param tarefaDto Dados da tarefa a ser cadastrada.
     * @return A tarefa cadastrada.
     */
    public TarefaDto cadastrar(TarefaDto tarefaDto) {
        // Verifica se o usuário associado à tarefa existe no banco
        if (tarefaDto.usuario() == null || tarefaDto.usuario().id() == null) {
            throw new IllegalArgumentException("Usuário associado à tarefa é obrigatório.");
        }

        Usuario usuario = usuarioRepository.findById(tarefaDto.usuario().id())
                .orElseThrow(() -> new IllegalArgumentException("Usuário associado não encontrado."));

        // Converte o DTO para a entidade Tarefa
        Tarefa tarefa = Tarefa.fromDto(tarefaDto);
        tarefa.setExcluida(false); // Define a tarefa como não excluída
        tarefa.setUsuario(usuario); // Associa o usuário existente à tarefa

        // Salva a tarefa no repositório e retorna o DTO
        return new TarefaDto(tarefaRepository.save(tarefa));
    }



}
