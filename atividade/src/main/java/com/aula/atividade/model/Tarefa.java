package com.aula.atividade.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.annotation.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Column;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Document(collection = "tarefas")

public class Tarefa {

    @Id
    private String id;
    private String titulo;
    private String descricao;

    @Column (name = "data_criacao")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCriacao;
    // list <tarefas> findbydatabetween (localdata start, localdata end)
    // optional <tarefa> findbytitulo()
}
