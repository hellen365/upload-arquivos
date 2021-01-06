package com.vhns.uploadarquivos.dto;

import com.vhns.uploadarquivos.model.Role;
import com.vhns.uploadarquivos.model.Usuario;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;

public class UsuarioDTO {

    @NotEmpty(message = "Preenchimento Obrigatório")
    @Length(min = 5, max = 120,  message = "O tamanho deve ser entre 5 e 120 caracteres")    private String login;

    @NotEmpty(message = "Preenchimento Obrigatório")
    private String password;

    private List<Role> role;

    public UsuarioDTO(){

    }

    public UsuarioDTO(Usuario usuario) {
        login = usuario.getLogin();
        password = usuario.getPassword();
        role = Arrays.asList(new Role("ROLE_USER")); // Todo usuario é um ROLE_USER
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }
}
