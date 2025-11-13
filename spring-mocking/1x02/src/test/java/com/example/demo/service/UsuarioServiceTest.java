package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarUsuarioQuandoIdExistir() {
        Long id = 1L;
        Usuario usuarioEsperado = new Usuario(id, "Teste teste", "teste.teste@teste.com");
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioEsperado));
        Usuario usuarioRetornado = usuarioService.buscarUsuarioPorId(id);
        assertNotNull(usuarioRetornado);
        assertEquals(usuarioEsperado.getId(), usuarioRetornado.getId());
        assertEquals(usuarioEsperado.getNome(), usuarioRetornado.getNome());
        assertEquals(usuarioEsperado.getEmail(), usuarioRetornado.getEmail());
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistir() {
        Long idInexistente = 999L;
        when(usuarioRepository.findById(idInexistente)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.buscarUsuarioPorId(idInexistente);
        });
        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(idInexistente);
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        Usuario usuarioParaSalvar = new Usuario("Teste teste", "teste.teste@teste.com");
        Usuario usuarioSalvo = new Usuario(2L, "Teste teste", "teste.teste@teste.com");
        when(usuarioRepository.save(usuarioParaSalvar)).thenReturn(usuarioSalvo);
        Usuario usuarioRetornado = usuarioService.salvarUsuario(usuarioParaSalvar);
        assertNotNull(usuarioRetornado);
        assertNotNull(usuarioRetornado.getId());
        assertEquals(usuarioSalvo.getId(), usuarioRetornado.getId());
        assertEquals(usuarioSalvo.getNome(), usuarioRetornado.getNome());
        assertEquals(usuarioSalvo.getEmail(), usuarioRetornado.getEmail());
        verify(usuarioRepository, times(1)).save(usuarioParaSalvar);
    }
}
