package br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ComprarItemRequest {
    
    private UUID produtoId;
}
