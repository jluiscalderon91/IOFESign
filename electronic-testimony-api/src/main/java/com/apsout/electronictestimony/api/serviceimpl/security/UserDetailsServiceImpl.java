package com.apsout.electronictestimony.api.serviceimpl.security;

import com.apsout.electronictestimony.api.entity.security.Authority;
import com.apsout.electronictestimony.api.entity.security.Role;
import com.apsout.electronictestimony.api.entity.security.User;
import com.apsout.electronictestimony.api.repository.security.UserRepository;
import com.apsout.electronictestimony.api.service.security.AuthorityService;
import com.apsout.electronictestimony.api.service.security.RoleService;
import com.apsout.electronictestimony.api.util.allocator.security.UserAllocator;
import com.apsout.electronictestimony.api.util.statics.States;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Qualifier("UserDetailServiceImplET")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository applicationUserRepository;
    private AuthorityService authorityService;
    private RoleService roleService;

    public UserDetailsServiceImpl(UserRepository applicationUserRepository, AuthorityService authorityService, RoleService roleService) {
        this.applicationUserRepository = applicationUserRepository;
        this.authorityService = authorityService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = applicationUserRepository.findByUsernameAndActiveAndDeleted(username, States.ACTIVE, States.EXISTENT);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        final List<Role> roles = roleService.findAllBy(user);
        final Collection<GrantedAuthority> grantedRoles = UserAllocator.toGrantedRoles(roles);
        final List<Authority> authorities = authorityService.findAllBy(user);
        final Collection<GrantedAuthority> grantedAuthorities = UserAllocator.toGrantedAuthorities(authorities);
        final List<GrantedAuthority> grantedUser = Stream.concat(grantedRoles.stream(), grantedAuthorities.stream()).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedUser);
    }
}
