package com.example.first_project_java.infra.repository.impl;

import com.example.first_project_java.controller.DTO.ListUserDTO;
import com.example.first_project_java.entity.User;
import com.example.first_project_java.infra.repository.UserRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findByFiltros(ListUserDTO filtros) {
        StringBuilder jpql = new StringBuilder("SELECT u FROM User u WHERE 1=1");
        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        if (StringUtils.hasText(filtros.id())) {
            jpql.append(" AND u.id = :id");
            params.add(filtros.id());
        }

        if (StringUtils.hasText(filtros.nome())) {
            jpql.append(" AND LOWER(u.nome) LIKE LOWER(:nome)");
            params.add("%" + filtros.nome() + "%");
        }

        if (StringUtils.hasText(filtros.email())) {
            jpql.append(" AND LOWER(u.email) LIKE LOWER(:email)");
            params.add("%" + filtros.email() + "%");
        }

        if (StringUtils.hasText(filtros.perfil())) {
            jpql.append(" AND u.perfil = :perfil");
            params.add(filtros.perfil());
        }

        TypedQuery<User> query = entityManager.createQuery(jpql.toString(), User.class);

        int i = 0;
        if (StringUtils.hasText(filtros.id())) query.setParameter("id", java.util.UUID.fromString(filtros.id()));
        if (StringUtils.hasText(filtros.nome())) query.setParameter("nome", "%" + filtros.nome() + "%");
        if (StringUtils.hasText(filtros.email())) query.setParameter("email", "%" + filtros.email() + "%");
        if (StringUtils.hasText(filtros.perfil())) query.setParameter("perfil", filtros.perfil());

        return query.getResultList();
    }
}
