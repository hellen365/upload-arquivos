package com.vhns.uploadarquivos.repository;

import com.vhns.uploadarquivos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Usuario, String> {
        Usuario findByLogin(String name);
}
