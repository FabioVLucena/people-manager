package com.attornatus.peoplemanager.dto;

import java.util.Arrays;
import java.util.List;

public class ErrorResponseDTO {

	private List<String> error;
	
	public ErrorResponseDTO(List<String> apiErroList) {
		this.error = apiErroList;
	}
	
	public ErrorResponseDTO(String mensagemErro) {
		this.error = Arrays.asList(mensagemErro);
	}

	public List<String> getError() {
		return this.error;
	}
	
}
