package com.rbac.finance.security;

import com.rbac.finance.model.Status;
import com.rbac.finance.model.User;
import com.rbac.finance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        
        if (user.getStatus() == Status.INACTIVE) {
            throw new DisabledException("User account is inactive");
        }

        return UserDetailsImpl.build(user);
    }
}
