package com.tcc.redonate.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @Column(nullable = true)
    private String cnpjInstituicao;

    @Column(nullable = false)
    private String causaInstituicao;

    @Column(nullable = false)
    private String necessidadesInstituicao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuarioInstituicao;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "instituicao", orphanRemoval = true)
    private List<Doacao> doacoes;
}
