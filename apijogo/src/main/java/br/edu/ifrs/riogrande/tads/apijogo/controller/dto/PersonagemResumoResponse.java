package br.edu.ifrs.riogrande.tads.apijogo.controller.dto;

import java.util.UUID;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.Personagem;
import br.edu.ifrs.riogrande.tads.apijogo.app.model.enums.EnumClassePersonagem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@ApiModel(value = "Resumo Personagem", description = "O resumo de uma personagem.")
@AllArgsConstructor
public class PersonagemResumoResponse {

    @ApiModelProperty
    UUID id;

    @ApiModelProperty
    String nome;
    
    @ApiModelProperty
    EnumClassePersonagem classe;

    @ApiModelProperty
    int dano;    
    
    public static PersonagemResumoResponse from(Personagem p) {
        return new PersonagemResumoResponse(p.getId(), p.getNome(), p.getClasse(), p.getDano());
    }
}
