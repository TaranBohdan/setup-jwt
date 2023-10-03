package com.bohdan.JwtTokenGeneratorProj.services;

import com.bohdan.JwtTokenGeneratorProj.entities.Role;
import com.bohdan.JwtTokenGeneratorProj.repositories.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService
{
    private final RoleRepo roleRepo;

    public Role getUserRole()
    {
        return roleRepo.findByName("ROLE_USER").get();
    }
}
