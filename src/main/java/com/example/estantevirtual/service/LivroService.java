package com.example.estantevirtual.service;

import com.example.estantevirtual.model.Livro;
import com.example.estantevirtual.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    LivroRepository livroRepository;

    public Optional<Livro> buscaLivroPorId(Long id) {
        if(livroRepository.findById(id).isPresent()) {
            return livroRepository.findById(id);
        }
        return Optional.empty();
    }

    public void cadastrarLivro(Livro livro) {
        livroRepository.save(livro);
    }

    public List<Livro> buscarLivros() {
        return livroRepository.findAll(Sort.by(Sort.Direction.ASC, "titulo"));
    }

    public boolean deletarLivro(Long id) {
        if(livroRepository.findById(id).isPresent()) {
            livroRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Boolean atualizarLivro(Long id, Livro livro) {
        Optional<Livro> livroParaAtualizar = livroRepository.findById(id);
        if(livroParaAtualizar.isPresent()) {
            livroParaAtualizar.get().setTitulo(livro.getTitulo());
            livroParaAtualizar.get().setAutor(livro.getAutor());
            livroRepository.save(livroParaAtualizar.get());
            return true;
        }
        return false;
    }
}
