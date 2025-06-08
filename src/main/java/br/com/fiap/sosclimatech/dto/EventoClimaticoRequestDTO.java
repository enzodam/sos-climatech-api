package br.com.fiap.sosclimatech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoClimaticoRequestDTO {
        private Long id;
        @NotBlank(message = "Tipo do evento é obrigatório")
        private String tipo;
        @NotBlank(message = "Descrição é obrigatória")
        private String descricao;
        @NotNull(message = "Data de início é obrigatória")
        private LocalDate dataInicio;
        @NotNull(message = "Localidade é obrigatória")
        private Long localidadeId;
        private String impacto;
}
