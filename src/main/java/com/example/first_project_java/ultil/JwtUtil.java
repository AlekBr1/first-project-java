package com.example.first_project_java.ultil;

import com.example.first_project_java.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    // A chave secreta que será usada para assinar o JWT. Usando HS256 (HMAC com SHA-256).
    // Em um ambiente de produção, a chave deve ser mais segura e armazenada de maneira protegida.
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Método para gerar o JWT com base em um objeto `User`
    public static String generateToken(User user) {

        // Obtém o tempo atual (em milissegundos) - a partir de quando o token será gerado
        long now = System.currentTimeMillis();

        // Define a expiração do token: 1 hora a partir do momento atual
        long exp = now + 3600000; // 3600000 ms = 1 hora

        // Mapa que contém as "claims" (informações) que serão incluídas no token
        // Claims são dados que você deseja armazenar dentro do JWT.
        Map<String, Object> claims = new HashMap<>();

        // Adiciona os dados do usuário no token como claims (não é criptografado, apenas assinado)
        claims.put("id", user.getUserId());      // ID do usuário
        claims.put("nome", user.getNome());      // Nome do usuário
        claims.put("email", user.getEmail());    // Email do usuário
        claims.put("perfil", user.getPerfil());  // Perfil do usuário (ex: ADMIN, USER)

        // Constrói o JWT com base nas claims e outras configurações
        return Jwts.builder()
                // Define as claims que foram preparadas
                .setClaims(claims)

                // Define o 'subject' do token (geralmente é um identificador único, como o e-mail ou ID do usuário)
                .setSubject(user.getEmail()) // Usando o email como identificador único no token

                // Define a data de emissão do token
                .setIssuedAt(new Date(now))

                // Define a data de expiração do token (1 hora após a emissão)
                .setExpiration(new Date(exp))

                // Assina o token com a chave secreta para garantir que ele não seja alterado
                .signWith(key)

                // Compacta o token (construção final) e retorna como uma String
                .compact();
    }
}
