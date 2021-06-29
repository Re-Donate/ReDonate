package com.tcc.redonate.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Doacao implements AbstractEntity, Comparable<Doacao>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private float valorDoacao;

    @Column(nullable = false)
    private String causaDoacao;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_doador", referencedColumnName = "id")
    private Doador doador;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_instituicao", referencedColumnName = "id")
    private Instituicao instituicao;

    @Override
    public int compareTo(Doacao d){
        return this.getCausaDoacao().compareTo(d.getCausaDoacao());
    }
}
