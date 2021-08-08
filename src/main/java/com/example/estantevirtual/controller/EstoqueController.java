package com.example.estantevirtual.controller;

import com.example.estantevirtual.dto.LivroDTO;
import com.example.estantevirtual.model.Livro;
import com.example.estantevirtual.service.EstoqueService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("/t")
public class EstoqueController {

    @Autowired
    EstoqueService estoqueService;

    @GetMapping("/estoque")
    public List<LivroDTO> retornaItens() {
        List<LivroDTO> livrosDto = new ArrayList<>();
        for (Livro livro : estoqueService.estoqueDisponivel()) {
            LivroDTO livroDTO = new LivroDTO();
            BeanUtils.copyProperties(livro, livroDTO);
            livrosDto.add(livroDTO);
        }
        return livrosDto;
    }

    @PostMapping("/estoque")
    public ResponseEntity<?> adicionaItens(@RequestBody List<Livro> livros) {
        if(livros.size() == 1) {
            estoqueService.adicionaNoEstoque(livros.get(0));
        } else {
            estoqueService.adicionaNoEstoque(livros);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/estoque/{id}")
    public ResponseEntity<?> excluiItem(@PathVariable Long id) {
        estoqueService.retiraDoEstoque(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/estoque")
    public ResponseEntity<?> excluiItens(@RequestBody(required = false) List<Long> ids) {
        if(ids.size() == 1) {
            estoqueService.retiraDoEstoque(ids.get(0));
        } else {
            estoqueService.retiraDoEstoque(ids);
        }
        return ResponseEntity.ok().build();
    }
}
