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

    @Column(nullable = false)
    private Long idDoador;

    @Column(nullable = false)
    private Long idInstituicao;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @Override
    public int compareTo(Doacao d){
        return this.getCausaDoacao().compareTo(d.getCausaDoacao());
    }
}
