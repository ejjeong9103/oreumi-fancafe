package com.estsoft.oreumifancafe.service.auth;

import com.estsoft.oreumifancafe.exceptions.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailsServiceInterf {
    UserDetails loadUsersById(String userId) throws UserNotFoundException;
}
