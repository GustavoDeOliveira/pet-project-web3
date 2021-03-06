package br.edu.ifrs.riogrande.tads.apijogo.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "ServerResponse")
@Getter @Setter
public class ResponseWrapper<T> { // Generics, Templates

	@ApiModelProperty(value = "data")
    @JsonInclude(value = Include.NON_NULL)
	protected final T data; // payload

	protected ResponseWrapper(T data) {
		this.data = data;
	}

	// inferência do tipo, método fábrica
	public static <T> ResponseWrapper<T> wrap(T response) {
		return new ResponseWrapper<T>(response);
	}

}