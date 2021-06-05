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
public class Instituicao implements AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String nomeInstituicao;

    @Column(nullable = false)
    private String enderecoInstituicao;

    @Column(nullable = false)
    private String cidadeInstituicao;

    @Column(nullable = true)
    private String telefoneInstituicao;

    @Column(nullable = true)
    private String celularInstituicao;

    @Column(nullable = true)
    private String cnpjInstituicao;

    @Column(nullable = false)
    private String causaInstituicao;

    @Column(nullable = false)
    private String necessidadesInstituicao;

    @Column(nullable = true)
    private String cpfInstituicao;

    @Column(nullable = false)
    private Long idUsuario;
}
