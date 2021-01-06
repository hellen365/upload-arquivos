package com.vhns.uploadarquivos.resource;

import com.vhns.uploadarquivos.dto.UsuarioDTO;
import com.vhns.uploadarquivos.model.Usuario;
import com.vhns.uploadarquivos.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios(){
        List<Usuario> list = usuarioService.listarUsuarios();
        List<UsuarioDTO> listDTO  = list.stream().map(obj -> new UsuarioDTO(obj)).collect(Collectors.toList());
        usuarioService.listarUsuarios();
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping(value = "/user")
    public ResponseEntity<?> salvarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioService.fromDTO(usuarioDTO);
        usuario = usuarioService.salvarUsuario(usuario);
        usuarioService.salvarUsuario(usuario);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(usuario.getUsername()).toUri();

        return ResponseEntity.created(uri).build();
    }

}
