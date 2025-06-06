package br.com.fiap.sosclimatech.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") 
@Entity
@Table(name = "TB_REGISTRO_AJUDA")
public class RegistroAjuda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_REGISTRO_AJUDA")
    @SequenceGenerator(name = "SQ_REGISTRO_AJUDA", sequenceName = "SQ_REGISTRO_AJUDA", allocationSize = 1)
    @Column(name = "ID_REGISTRO")
    private Long id;

    @NotNull 
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "ID_PESSOA", nullable = false) 
    private PessoaAfetada pessoa;

    @NotNull 
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "ID_RECURSO", nullable = false) 
    private Recurso recurso;

    @Column(name = "DT_REGISTRO", nullable = false) 
    private LocalDateTime dataRegistro;

    @Column(name = "ST_ENTREGUE", nullable = false)
    private boolean entregue = false; 

}

