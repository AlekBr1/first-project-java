package com.example.first_project_java.infra.repository;

import com.example.first_project_java.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, UserRepositoryCustom {
    Optional<User> findByEmailAndSenha(String email, String senha);

    Optional<User> findByEmail(String email);

}
