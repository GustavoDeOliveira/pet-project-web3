package br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.responses;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ItemCompradoResponse {
    
    private UUID itemId;
}
