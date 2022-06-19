package br.edu.ifrs.riogrande.tads.apijogo.controller.dto;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.Personagem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Atributos Personagem", description = "Os atributos da personagem.")
public class AtributosPersonagemResponse {
    @ApiModelProperty
    int ataque;

    @ApiModelProperty
    int defesa;

    @ApiModelProperty
    int vida;

    public AtributosPersonagemResponse(Personagem p, boolean incluirEquipamento) {
        ataque = p.getAtributos().getAtaque();
        defesa = p.getAtributos().getDefesa();
        vida = p.getAtributos().getVida();
        if (incluirEquipamento) {
            var inventario = p.getInventario();
            inventario.stream().filter((i) -> i.getDuracao() == null || i.getDuracao() > 0)
                .forEach((i) -> {
                    ataque += i.getEfeito().getAtaque();
                    defesa += i.getEfeito().getDefesa();
                    vida += i.getEfeito().getVida();
                });
        }
    }
}