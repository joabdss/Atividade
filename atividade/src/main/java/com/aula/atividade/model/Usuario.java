package com.aula.atividade.model;

import com.aula.atividade.dto.UsuarioDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;
    private String nome;
    private String email;
    private String telefone;


    public static Usuario fromDto(UsuarioDto usuarioDto){
        return new Usuario(null, usuarioDto.nome(), usuarioDto.email(), usuarioDto.telefone());
    }
}
