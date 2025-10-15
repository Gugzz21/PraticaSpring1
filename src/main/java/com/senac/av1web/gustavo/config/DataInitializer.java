package com.senac.av1web.gustavo.config;

import com.senac.av1web.gustavo.entity.Role;
import com.senac.av1web.gustavo.entity.RoleName;
import com.senac.av1web.gustavo.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Order(1)
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Iniciando a inicialização de dados...");

        try {
            // Criar roles se não existirem
            Arrays.stream(RoleName.values()).forEach(roleName -> {
                try {
                    if (roleRepository.findByNome(roleName).isEmpty()) {
                        Role role = new Role();
                        role.setNome(roleName);
                        roleRepository.save(role);
                        logger.info("Role criada: {}", roleName);
                    } else {
                        logger.info("Role já existe: {}", roleName);
                    }
                } catch (Exception e) {
                    logger.warn("Erro ao verificar/criar role {}: {}", roleName, e.getMessage());
                }
            });

            logger.info("Inicialização de dados concluída com sucesso!");

        } catch (Exception e) {
            logger.error("Erro durante a inicialização de dados: {}", e.getMessage());
            // Não relançar a exceção para permitir que a aplicação continue
        }
    }
}