package br.edu.ifrs.riogrande.tads.apijogo.controller.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.Item;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@ApiModel(value = "Resumo Item", description = "O resumo de um item que pode ser equipado em uma personagem.")
@AllArgsConstructor
@JsonInclude(content = Include.NON_DEFAULT, value = Include.NON_DEFAULT)
public class ItemResumoResponse {
    
    @ApiModelProperty
    UUID id;

    @ApiModelProperty
    String nome;

    @ApiModelProperty
    double valor;

    @ApiModelProperty
    Integer duracao;
    
    @ApiModelProperty
    int ataque;

    @ApiModelProperty
    int defesa;

    @ApiModelProperty
    int vida;

    @ApiModelProperty
    int cura;

    public static ItemResumoResponse from(Item i) {
        return new ItemResumoResponse(
            i.getId(),
            i.getNome(),
            i.getValor(),
            i.getDuracao(),
            i.getEfeito().getAtaque(),
            i.getEfeito().getDefesa(),
            i.getEfeito().getVida(),
            i.getEfeito().getCura());
    }
}
