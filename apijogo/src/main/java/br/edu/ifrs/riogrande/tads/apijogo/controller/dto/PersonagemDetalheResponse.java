package br.edu.ifrs.riogrande.tads.apijogo.controller.dto;

import java.util.UUID;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.Personagem;
import br.edu.ifrs.riogrande.tads.apijogo.app.model.enums.EnumClassePersonagem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@ApiModel(value = "Detalhes Personagem", description = "Visão detalhada de uma personagem.")
@AllArgsConstructor
public class PersonagemDetalheResponse {

    @ApiModelProperty
    UUID id;

    @ApiModelProperty
    String nome;
    
    @ApiModelProperty
    EnumClassePersonagem classe;

    @ApiModelProperty
    int dano;
    
    @ApiModelProperty
    long xp;

    @ApiModelProperty
    int nivel;

    @ApiModelProperty
    long xpParaProximoNivel;

    @ApiModelProperty
    AtributosPersonagemResponse atributosBase;

    @ApiModelProperty
    AtributosPersonagemResponse atributosEquipado;
    
    public static PersonagemDetalheResponse from(Personagem p) {
        return new PersonagemDetalheResponse(p.getId(),
        p.getNome(),
        p.getClasseEnum(),
        p.getDano(),
        p.getAtributos().getXp(),
        p.getAtributos().getNivel(),
        p.getAtributos().getXpParaProximoNivel(),
        new AtributosPersonagemResponse(p, false),
        new AtributosPersonagemResponse(p, true)
        );
    }
}