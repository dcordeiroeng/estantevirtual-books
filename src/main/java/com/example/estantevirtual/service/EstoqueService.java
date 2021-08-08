package com.example.estantevirtual.service;

import com.example.estantevirtual.model.Livro;
import com.example.estantevirtual.model.Status;
import com.example.estantevirtual.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {

    @Autowired
    EstoqueRepository estoqueRepository;

    public void adicionaNoEstoque(Livro livro) {
        livro.setStatus(Status.DISPONIVEL);
        estoqueRepository.save(livro);
    }

    public void adicionaNoEstoque(List<Livro> livros) {
        for (Livro livro: livros) {
            livro.setStatus(Status.DISPONIVEL);
        }
        estoqueRepository.saveAll(livros);
    }

    public void retiraDoEstoque(Long id) {
        estoqueRepository.deleteById(id);
    }

    public void retiraDoEstoque(List<Long> ids) {
        for (Long id: ids) {
            if(estoqueRepository.existsById(id)) {
                estoqueRepository.deleteById(id);
            }
        }
    }

    public List<Livro> estoqueDisponivel() {
        return estoqueRepository.findAll();
    }
}
