package com.tcc.redonate.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Usuario implements AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    private String emailUsuario;

    @Column(nullable = false)
    private String senhaUsuario;

    @Column(nullable = false)
    private String nomeUsuario;

    @Column(nullable = false)
    private String enderecoUsuario;

    @Column(nullable = false)
    private String cidadeUsuario;

    @Column(nullable = false)
    private String estadoUsuario;

    @Column(nullable = true)
    private String telefoneUsuario;

    @Column(nullable = true)
    private String celularUsuario;

    @Column(nullable = false)
    private String cpfUsuario;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "usuarioInstituicao")
    private Instituicao instituicao;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "usuarioDoador")
    private Doador doador;
}
