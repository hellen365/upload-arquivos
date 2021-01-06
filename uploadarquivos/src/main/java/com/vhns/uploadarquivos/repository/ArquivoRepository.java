package com.vhns.uploadarquivos.repository;

import com.vhns.uploadarquivos.model.DetalhesArquivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArquivoRepository extends JpaRepository<DetalhesArquivo, Long> {

    DetalhesArquivo findById(long id);
}
