package com.aula.atividade.repository;

import com.aula.atividade.model.Tarefa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends MongoRepository<Tarefa, String> {
    // Método para encontrar todas as tarefas com excluida = true
    List<Tarefa> findByExcluida(boolean excluida);
    // Busca todas as tarefas excluídas de um usuário
    List<Tarefa> findByUsuarioIdAndExcluida(String usuarioId, boolean excluida);

    // Busca todas as tarefas excluídas de um usuário
    List<Tarefa> findByTituloContainingIgnoreCase(String titulo);
}


