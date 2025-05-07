package com.example.first_project_java.infra.repository;

import com.example.first_project_java.entity.User;
import com.example.first_project_java.controller.DTO.ListUserDTO;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {
    List<User> findByFiltros(ListUserDTO filtros);
}
