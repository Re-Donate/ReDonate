package com.tcc.redonate.model;

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
public class Doador implements AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String nomeDoador;

    @Column(nullable = false)
    private String enderecoDoador;

    @Column(nullable = false)
    private String cidadeDoador;

    @Column(nullable = false)
    private String telefoneDoador;

    @Column(nullable = false)
    private String celularDoador;

    @Column(nullable = false)
    private String cpfDoador;

    @Column(nullable = false)
    private String idadeDoador;

    @Column(nullable = false)
    private String sexoDoador;

    @Column(nullable = false)
    private Long idUsuario;
}
