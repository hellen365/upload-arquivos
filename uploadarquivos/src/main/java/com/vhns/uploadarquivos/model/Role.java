package com.vhns.uploadarquivos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Set;

@Entity
public class Role implements GrantedAuthority {

    @Id
    private String nomeRole;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usuarios;

    public Role(){

    }

    public Role(String nomeRole){
       this.nomeRole = nomeRole;
    }

    public String getNomeRole() {
        return nomeRole;
    }

    public void setNomeRole(String nomeRole) {
        this.nomeRole = nomeRole;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public String getAuthority() {
        return this.nomeRole;
    }
}
