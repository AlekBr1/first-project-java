package com.example.first_project_java.controller;

import com.example.first_project_java.controller.DTO.CreateUserDTO;
import com.example.first_project_java.controller.DTO.ListUserDTO;
import com.example.first_project_java.entity.User;
import com.example.first_project_java.service.UserService;
import com.example.first_project_java.ultil.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String email,
            @RequestParam String senha
    ) {
        try {
            System.out.println("não é pra entrar");
            Optional<User> user = userService.autenticar(email, senha);

            if (user.isEmpty()) {
                return ResponseEntity.status(401).body("Email ou senha inválidos!");
            }

            String token = JwtUtil.generateToken(user.get());

            Map<String, Object> response = new HashMap<>();
            response.put("user", user.get());
            response.put("token", token);

            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao autenticar usuário: " + e.getMessage());
        }
    }

    @PostMapping("/criar")
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO createUserDTO) {
        try {
            System.out.println("entrou");
            User user = userService.createUser(createUserDTO);
            System.out.println("222222");
            String token = JwtUtil.generateToken(user);

            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("token", token);

            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao criar usuário: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarUsuarios(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String perfil
    ) {
        try {
            System.out.println("não é para entrar");
            ListUserDTO filtros = new ListUserDTO(id, nome, email, perfil);
            List<User> usuarios = userService.listarComFiltros(filtros);
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao listar usuários: " + e.getMessage());
        }
    }
}
