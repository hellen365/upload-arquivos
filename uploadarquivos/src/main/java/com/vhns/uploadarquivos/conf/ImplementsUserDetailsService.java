package com.vhns.uploadarquivos.conf;

import com.vhns.uploadarquivos.model.Usuario;
import com.vhns.uploadarquivos.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class  ImplementsUserDetailsService implements UserDetailsService {

    private final UserRepository _userRepository;

    @Autowired
    public ImplementsUserDetailsService(UserRepository _userRepository){
        this._userRepository = _userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Usuario user = _userRepository.findByLogin(name);

        if(user == null){
            throw new UsernameNotFoundException("Usuario não encontrado!");
        }
        return new User(user.getUsername(), user.getPassword(), true, true,
                true, true, user.getAuthorities());
    }
}
