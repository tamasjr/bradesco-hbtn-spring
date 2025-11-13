package com.example.demo.service;

import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveRetornarProdutoQuandoIdExistir() {
        // Arrange - Preparar os dados de teste
        Long id = 1L;
        Produto produtoEsperado = new Produto(id, "Produto Teste", 100.0);

        // Configurar o mock para retornar um Optional com o produto quando findById for chamado
        Mockito.when(produtoRepository.findById(id)).thenReturn(Optional.of(produtoEsperado));

        // Act - Executar o método que está sendo testado
        Produto produtoRetornado = produtoService.buscarPorId(id);

        // Assert - Verificar se o resultado é o esperado
        assertNotNull(produtoRetornado);
        assertEquals(produtoEsperado.getId(), produtoRetornado.getId());
        assertEquals(produtoEsperado.getNome(), produtoRetornado.getNome());
        assertEquals(produtoEsperado.getPreco(), produtoRetornado.getPreco());

        // Verificar se o método findById foi chamado uma vez com o ID correto
        Mockito.verify(produtoRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExistir() {
        // Arrange - Preparar os dados de teste
        Long idInexistente = 999L;

        // Configurar o mock para retornar um Optional vazio quando findById for chamado
        Mockito.when(produtoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // Act & Assert - Executar o método e verificar se a exceção é lançada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.buscarPorId(idInexistente);
        });

        // Verificar se a mensagem da exceção é a esperada
        assertEquals("Produto não encontrado", exception.getMessage());

        // Verificar se o método findById foi chamado uma vez com o ID correto
        Mockito.verify(produtoRepository, Mockito.times(1)).findById(idInexistente);
    }
}
