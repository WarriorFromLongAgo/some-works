package com.orhonit.ole.tts.config;

import com.orhonit.ole.tts.dto.FlowDTO;
import com.orhonit.ole.tts.dto.PersonDTO;

public class ThreadLocalVariables {

	private static ThreadLocal<FlowDTO> flowDTO = new ThreadLocal<>();
	
	private static ThreadLocal<PersonDTO> personDTO = new ThreadLocal<>();
	
	public static PersonDTO getPersonDTO() {
		return personDTO.get();
	}

	public static void setPersonDTO(PersonDTO apersonDTO) {
		personDTO.set(apersonDTO);
	}

	public static FlowDTO getFlowDTO() {
		return flowDTO.get();
	}
	
	public static void setFlowDTO(FlowDTO pflowDTO) {
		flowDTO.set(pflowDTO);
	}

	public static void removePersonDTO() {
		personDTO.remove();
		
	}
	
	public static void removeFlowDTO() {
		flowDTO.remove();
		
	}
}
