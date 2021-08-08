package com.example.estantevirtual;

import com.example.estantevirtual.model.Livro;
import com.example.estantevirtual.repository.LivroRepository;
import com.example.estantevirtual.service.LivroService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class EstanteVirtualApplicationTests {

//    @InjectMocks
//    private LivroService livroService;
//
//    @Mock
//    private LivroRepository livroRepository;
//
//    @Test
//    @Order(4)
//    void contextLoads() {
//    }
//
//    /*@Test
//    @Order(2)
//    void testDeletaLivro() {
//        livroService.deletarLivro(1L);
//    }*/
//
//    @Test
//    @Order(3)
//    void testRetornaTodosOsLivros() {
//        List<Livro> livros = livroService.buscarLivros();
//        livros.forEach(livro -> System.out.println(livro.getId()));
//    }

}
