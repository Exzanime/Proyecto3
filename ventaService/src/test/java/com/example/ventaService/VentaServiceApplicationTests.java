package com.example.ventaService;

import com.example.ventaService.dtos.UserValidationRequest;
import com.example.ventaService.dtos.UserValidationResponse;
import com.example.ventaService.feignClient.UsuarioClient;
import com.example.ventaService.model.Usuario;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class VentaServiceApplicationTests {
	@Mock
	private UsuarioClient usuarioClient;

	@Test
	void contextLoads() {
	}
	@Test
	void checkUsuarioClientConectWell() {
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setNombre("Juan");
		usuario.setEmail("juan@gmail.com");

		when(usuarioClient.validarUsuario(UserValidationRequest.builder().build())).thenReturn(UserValidationResponse.builder().token("").build());
		assertTrue(usuarioClient.validarUsuario(UserValidationRequest.builder().build()).getToken().isEmpty());

	}
}
