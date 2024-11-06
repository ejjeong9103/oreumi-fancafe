package com.estsoft.oreumifancafe.service.auth;

import com.estsoft.oreumifancafe.exceptions.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsServiceInterf extends UserDetailsService {
    UserDetails loadUsersById(String userId) throws UserNotFoundException;
}
