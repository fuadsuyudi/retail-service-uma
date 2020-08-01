package net.suyudi.retail_uma.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.suyudi.retail_uma.model.User;
import net.suyudi.retail_uma.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService  {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userService.getByUsername(username);

        if (user != null) {
            return user;
        }

        throw new UsernameNotFoundException("User not found");
    }

}
