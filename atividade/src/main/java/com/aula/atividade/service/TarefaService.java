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
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;

    public Page<TarefaDto> listar(Pageable pageable) {
        return tarefaRepository.findAll(pageable).map(TarefaDto::new);
    }

    public List<TarefaDto> listarTarefasExcluidas() {
        // Busca todas as tarefas excluídas
        List<Tarefa> tarefasExcluidas = tarefaRepository.findByExcluida(true);

        // Converte as tarefas para DTO antes de retornar
        return tarefasExcluidas.stream()
                .map(TarefaDto::new)
                .collect(Collectors.toList());
    }

    public List<TarefaDto> listarTarefasPorTitulo(String titulo) {
        // Busca todas as tarefas excluídas
        List<Tarefa> tarefas = tarefaRepository.findByTituloContainingIgnoreCase(titulo);

        // Converte as tarefas para DTO antes de retornar
        return tarefas.stream()
                .map(TarefaDto::new)
                .collect(Collectors.toList());
    }

    public UsuarioDto listarTarefasExcluidasDoUsuario(String usuarioId) {
        // Verificar se o usuário existe no banco de dados
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário informado não foi encontrado."));

        // Buscar todas as tarefas excluídas do usuário
        List<Tarefa> tarefasExcluidas = tarefaRepository.findByUsuarioIdAndExcluida(usuarioId, true);

        // Se não houver tarefas excluídas, retornar null
        if (tarefasExcluidas.isEmpty()) {
            return null;
        }


        // Retornar o usuário com as tarefas excluídas
        return new UsuarioDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getExcluido(),
                tarefasExcluidas // Associar as tarefas excluídas ao DTO do usuário
        );
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

    public TarefaDto update(String id, TarefaDto tarefaDto) {
        // Verifica se a tarefa existe no banco de dados
        Tarefa tarefaExistente = tarefaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada para o ID: " + id));

        // Verifica se o usuário associado à tarefa existe
        if (tarefaDto.usuario() == null || tarefaDto.usuario().id() == null) {
            throw new IllegalArgumentException("Usuário associado à tarefa é obrigatório.");
        }

        Usuario usuario = usuarioRepository.findById(tarefaDto.usuario().id())
                .orElseThrow(() -> new IllegalArgumentException("Usuário associado não encontrado."));

        // Atualiza os dados da tarefa existente
        tarefaExistente.setTitulo(tarefaDto.titulo());
        tarefaExistente.setDescricao(tarefaDto.descricao());
        tarefaExistente.setDataCriacao(tarefaDto.dataCriacao());
        tarefaExistente.setExcluida(tarefaDto.excluida());
        tarefaExistente.setUsuario(usuario);

        // Salva as alterações no repositório
        return new TarefaDto(tarefaRepository.save(tarefaExistente));
    }




}
