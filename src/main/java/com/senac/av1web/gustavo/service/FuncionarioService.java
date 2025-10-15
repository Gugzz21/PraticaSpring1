package com.senac.av1web.gustavo.service;

import com.senac.av1web.gustavo.dto.*;
import com.senac.av1web.gustavo.dto.response.FuncionarioResponseDto;
import com.senac.av1web.gustavo.entity.FolhaPagamento;
import com.senac.av1web.gustavo.entity.Funcionario;
import com.senac.av1web.gustavo.entity.Role;
import com.senac.av1web.gustavo.entity.RoleName;
import com.senac.av1web.gustavo.repository.FolhaPagamentoRepository;
import com.senac.av1web.gustavo.repository.FuncionarioRepository;
import com.senac.av1web.gustavo.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FolhaPagamentoRepository folhaPagamentoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ========== MÉTODOS PÚBLICOS (ABERTOS) ==========

    /**
     * Criar novo funcionário - Endpoint aberto
     */
    @Transactional
    public void criarFuncionario(CreateFuncionarioDto createFuncionarioDto) {
        // Verificar se matrícula já existe
        if (funcionarioRepository.findByMatricula(createFuncionarioDto.matricula()).isPresent()) {
            throw new RuntimeException("Matrícula já cadastrada: " + createFuncionarioDto.matricula());
        }

        // Buscar role
        RoleName roleName = RoleName.valueOf(createFuncionarioDto.role().toUpperCase());
        Role role = roleRepository.findByNome(roleName)
                .orElseThrow(() -> new RuntimeException("Role não encontrada: " + roleName));

        // Criar funcionário - Status 1 (Ativo) por padrão
        Funcionario funcionario = new Funcionario();
        funcionario.setMatricula(createFuncionarioDto.matricula());
        funcionario.setNome(createFuncionarioDto.nome());
        funcionario.setDataNascimento(createFuncionarioDto.dataNascimento());
        funcionario.setChaveAcesso(passwordEncoder.encode(createFuncionarioDto.chaveAcesso()));
        funcionario.setStatus(1); // SEMPRE ATIVO AO CRIAR
        funcionario.setRoles(List.of(role));

        funcionarioRepository.save(funcionario);
    }

    /**
     * Login do funcionário - Endpoint aberto
     * Só permite login se status = 1 (Ativo)
     */
    public RecoveryJwtTokenDto autenticarFuncionario(LoginFuncionarioDto loginFuncionarioDto) {
        try {
            // Primeiro verifica se o funcionário existe e está ativo
            Funcionario funcionario = funcionarioRepository.findByMatricula(loginFuncionarioDto.matricula())
                    .orElseThrow(() -> new RuntimeException("Matrícula não encontrada"));

            // Verificar se está ativo
            if (funcionario.getStatus() != 1) {
                throw new RuntimeException("Funcionário bloqueado. Contate o administrador.");
            }

            // Se estiver ativo, faz a autenticação
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginFuncionarioDto.matricula(), loginFuncionarioDto.chaveAcesso());

            Authentication authentication = authenticationManager.authenticate(authToken);
            FuncionarioDetailsImpl funcionarioDetails = (FuncionarioDetailsImpl) authentication.getPrincipal();

            return new RecoveryJwtTokenDto(jwtTokenService.generateToken(funcionarioDetails));

        } catch (BadCredentialsException e) {
            throw new RuntimeException("Matrícula ou senha inválidas");
        }
    }

    // ========== MÉTODOS DE GERENTE ==========

    /**
     * Alterar status do funcionário - Apenas Gerente
     * 1 = Ativo, 0 = Bloqueado
     */
    @Transactional
    public void alterarStatusFuncionario(Integer id, Integer status) {
        if (status != 0 && status != 1) {
            throw new RuntimeException("Status deve ser 0 (Bloqueado) ou 1 (Ativo)");
        }

        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));

        funcionario.setStatus(status);
        funcionarioRepository.save(funcionario);
    }

    /**
     * Criar folha de pagamento - Apenas Gerente
     * Só cria para funcionários ativos (status = 1)
     */
    @Transactional
    public void criarFolhaPagamento(FolhaPagamentoDto folhaPagamentoDto) {
        Funcionario funcionario = funcionarioRepository.findById(folhaPagamentoDto.funcionarioId())
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));

        // Verificar se funcionário está ativo
        if (funcionario.getStatus() != 1) {
            throw new RuntimeException("Não é possível criar folha de pagamento para funcionário bloqueado");
        }

        FolhaPagamento folhaPagamento = new FolhaPagamento();
        folhaPagamento.setMes(folhaPagamentoDto.mes());
        folhaPagamento.setAno(folhaPagamentoDto.ano());
        folhaPagamento.setSalario(folhaPagamentoDto.salario());
        folhaPagamento.setFuncionario(funcionario);

        folhaPagamentoRepository.save(folhaPagamento);
    }

    /**
     * Listar todos os funcionários com folhas - Apenas Gerente
     * Mostra apenas funcionários ativos
     */
    @Transactional(readOnly = true)
    public List<FuncionarioResponseDto> listarFuncionariosComFolhas() {
        return funcionarioRepository.findAllAtivos().stream()
                .map(this::convertToDtoComFolhas)
                .collect(Collectors.toList());
    }

    // ========== MÉTODOS DE COLABORADOR ==========

    /**
     * Consultar funcionário por ID - Gerente e Colaborador
     * Só retorna se o funcionário estiver ativo
     */
    @Transactional(readOnly = true)
    public FuncionarioResponseDto consultarFuncionarioPorId(Integer id) {
        Funcionario funcionario = funcionarioRepository.findByIdAndStatusAtivo(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário ativo não encontrado"));
        return convertToDtoComFolhas(funcionario);
    }

    /**
     * Listar apenas funcionários ativos - Para qualquer usuário autenticado
     */
    @Transactional(readOnly = true)
    public List<FuncionarioResponseDto> listarFuncionariosAtivos() {
        return funcionarioRepository.findByStatusAtivo().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // ========== MÉTODOS AUXILIARES ==========

    private FuncionarioResponseDto convertToDto(Funcionario funcionario) {
        FuncionarioResponseDto dto = new FuncionarioResponseDto();
        dto.setId(funcionario.getId());
        dto.setMatricula(funcionario.getMatricula());
        dto.setNome(funcionario.getNome());
        dto.setDataNascimento(funcionario.getDataNascimento());
        dto.setStatus(funcionario.getStatus());

        dto.setRoles(funcionario.getRoles().stream()
                .map(role -> role.getNome().name())
                .collect(Collectors.toList()));

        return dto;
    }

    private FuncionarioResponseDto convertToDtoComFolhas(Funcionario funcionario) {
        FuncionarioResponseDto dto = convertToDto(funcionario);

        List<FolhaPagamentoDto> folhas = folhaPagamentoRepository
                .findByFuncionarioIdAndAtivo(funcionario.getId()).stream()
                .map(this::convertFolhaToDto)
                .collect(Collectors.toList());

        dto.setFolhasPagamento(folhas);

        return dto;
    }

    private FolhaPagamentoDto convertFolhaToDto(FolhaPagamento folha) {
        return new FolhaPagamentoDto(
                folha.getMes(),
                folha.getAno(),
                folha.getSalario(),
                folha.getFuncionario().getId()
        );
    }

    /**
     * Verificar se funcionário é gerente
     */
    @Transactional(readOnly = true)
    public boolean isGerente(Integer funcionarioId) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
        return funcionario.isGerente();
    }

    /**
     * Buscar funcionário por matrícula (útil para testes)
     */
    @Transactional(readOnly = true)
    public FuncionarioResponseDto buscarPorMatricula(String matricula) {
        Funcionario funcionario = funcionarioRepository.findByMatricula(matricula)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
        return convertToDto(funcionario);
    }
}