package com.vhns.uploadarquivos.service;

import com.vhns.uploadarquivos.dto.UsuarioDTO;
import com.vhns.uploadarquivos.model.Role;
import com.vhns.uploadarquivos.model.Usuario;
import com.vhns.uploadarquivos.repository.RoleRepository;
import com.vhns.uploadarquivos.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UsuarioService {

    private final UserRepository _userRepository;

    private final RoleRepository _roleRepository;

    public UsuarioService(UserRepository _userRepository, RoleRepository _roleRepository ){
        this._userRepository = _userRepository;
        this._roleRepository = _roleRepository;
    }

    public List<Usuario> listarUsuarios(){
        return _userRepository.findAll();
    }

    public Usuario salvarUsuario(Usuario usuario){
        return _userRepository.save(usuario);
    }


    public Usuario fromDTO(UsuarioDTO usuarioDTO) {
        BCryptPasswordEncoder pw = new BCryptPasswordEncoder();
        Usuario usuario = new Usuario(usuarioDTO.getLogin(), pw.encode(usuarioDTO.getPassword()));
        usuario.setRoles(new HashSet<>(_roleRepository.findAll()));

        return usuario;
    }

}
