package com.example.estantevirtual.controller;

import com.example.estantevirtual.dto.LivroDTO;
import com.example.estantevirtual.model.Livro;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController("/")
public class CarrinhoController {

    @Autowired
    private EstoqueController estoqueController;

    @GetMapping("/carrinho")
    public ResponseEntity<List<LivroDTO>> retornaItens() {
        return ResponseEntity.ok(estoqueController.retornaItens());
    }

    @PostMapping("/carrinho/{livros}")
    public ResponseEntity<?> adicionaItens(@PathVariable List<Livro> livros) {
        return estoqueController.adicionaItens(livros);
    }

    @DeleteMapping("/carrinho/{id}")
    public ResponseEntity<?> excluiItens(@PathVariable Long id) {
        return estoqueController.excluiItem(id);
    }

    @DeleteMapping("/carrinho")
    public ResponseEntity<?> excluiItens(@RequestBody List<Long> ids) {
        return estoqueController.excluiItens(ids);
    }

}
