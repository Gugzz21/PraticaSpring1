package com.senac.av1web.gustavo.config;

import com.senac.av1web.gustavo.entity.Funcionario;
import com.senac.av1web.gustavo.repository.FuncionarioRepository;
import com.senac.av1web.gustavo.service.FuncionarioDetailsImpl;
import com.senac.av1web.gustavo.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class FuncionarioAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checkIfEndpointIsNotPublic(request)) {
            String token = recoveryToken(request);
            if (token != null) {
                try {
                    String subject = jwtTokenService.getSubjectFromToken(token);
                    Funcionario funcionario = funcionarioRepository.findByMatricula(subject)
                            .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

                    // Verifica se o funcionário está ativo
                    if (funcionario.getStatus() != 1) {
                        throw new RuntimeException("Funcionário inativo");
                    }

                    FuncionarioDetailsImpl funcionarioDetails = new FuncionarioDetailsImpl(funcionario);

                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            funcionarioDetails.getUsername(), null, funcionarioDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (RuntimeException e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write(e.getMessage());
                    return;
                }
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token está ausente");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return Arrays.stream(SecurityConfiguration.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED)
                .noneMatch(publicEndpoint -> requestURI.startsWith(publicEndpoint.replace("/**", "")));
    }
}