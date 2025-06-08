package br.com.fiap.sosclimatech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaAfetadaRequestDTO {
        private Long id;
        @NotBlank(message = "Nome é obrigatório")
        private String nome;
        @NotBlank(message = "CPF é obrigatório")
        @Pattern(regexp = "^\\d{11}$", message = "CPF deve conter 11 dígitos")
        private String cpf;
        @NotBlank(message = "Cidade é obrigatória")
        private String cidade;
        private Long eventoId;
}
