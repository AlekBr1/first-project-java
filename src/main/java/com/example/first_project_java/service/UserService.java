package com.example.first_project_java.service;

import com.example.first_project_java.controller.DTO.CreateUserDTO;
import com.example.first_project_java.controller.DTO.ListUserDTO;
import com.example.first_project_java.entity.User;
import com.example.first_project_java.infra.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(CreateUserDTO createUserDTO) {
        ZoneOffset offset = ZoneOffset.of("-03:00");
        Instant brasiliaInstant = OffsetDateTime.now(offset).toInstant();
        var newUser = new User();
        newUser.setNome(createUserDTO.nome());
        newUser.setEmail(createUserDTO.email());
        newUser.setSenha(createUserDTO.senha());
        newUser.setPerfil("user");
        newUser.setCreatedTimestamp(brasiliaInstant);
        newUser.setUpdateTimestamp(null);

        try {
            Optional<User> existingUser = userRepository.findByEmail(createUserDTO.email());
            if (existingUser.isPresent()) {
                throw new RuntimeException("Usuário já existe");
            }
            System.out.println("tamo ai");
            return userRepository.save(newUser);
        } catch (Exception e) {
            System.out.println("errouuuuu: " + e.getMessage());
            throw new RuntimeException("Erro ao criar usuário", e);
        }
    }

    public Optional<User> autenticar(String email, String senha) {
        try {
            return userRepository.findByEmailAndSenha(email, senha);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao autenticar usuário", e);
        }
    }

    public List<User> listarComFiltros(ListUserDTO filtros) {
        try {
            if (filtros.id() == null && filtros.nome() == null && filtros.email() == null && filtros.perfil() == null) {
                return userRepository.findAll();
            }
            return userRepository.findByFiltros(filtros);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar usuários com filtros", e);
        }
    }

}
