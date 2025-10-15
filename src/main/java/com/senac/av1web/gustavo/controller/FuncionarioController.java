package com.senac.av1web.gustavo.controller;

import com.senac.av1web.gustavo.dto.*;
import com.senac.av1web.gustavo.dto.response.FuncionarioResponseDto;
import com.senac.av1web.gustavo.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionario")
@Tag(name = "Funcionário", description = "API para gerenciamento de funcionários e folhas de pagamento")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    // ========== ENDPOINTS ABERTOS ==========

    @PostMapping("/criar")
    @Operation(summary = "Criar novo funcionário", description = "Endpoint aberto para criar novo funcionário (sempre cria como ATIVO)")
    public ResponseEntity<Void> criarFuncionario(@RequestBody CreateFuncionarioDto createFuncionarioDto) {
        funcionarioService.criarFuncionario(createFuncionarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    @Operation(summary = "Login do funcionário", description = "Endpoint aberto para login de funcionário (apenas ATIVOS podem logar)")
    public ResponseEntity<RecoveryJwtTokenDto> login(@RequestBody LoginFuncionarioDto loginFuncionarioDto) {
        RecoveryJwtTokenDto token = funcionarioService.autenticarFuncionario(loginFuncionarioDto);
        return ResponseEntity.ok(token);
    }

    // ========== ENDPOINTS DE GERENTE ==========

    @PutMapping("/alterar-status/{id}")
    @Operation(summary = "Alterar status do funcionário", description = "Apenas gerentes podem alterar o status (0=Bloqueado, 1=Ativo)")
    public ResponseEntity<Void> alterarStatus(@PathVariable Integer id, @RequestParam Integer status) {
        funcionarioService.alterarStatusFuncionario(id, status);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/folha-pagamento/criar")
    @Operation(summary = "Criar folha de pagamento", description = "Apenas gerentes podem criar folhas de pagamento (apenas para ATIVOS)")
    public ResponseEntity<Void> criarFolhaPagamento(@RequestBody FolhaPagamentoDto folhaPagamentoDto) {
        funcionarioService.criarFolhaPagamento(folhaPagamentoDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/listar-com-folhas")
    @Operation(summary = "Listar funcionários com folhas", description = "Apenas gerentes podem ver a lista completa (apenas ATIVOS)")
    public ResponseEntity<List<FuncionarioResponseDto>> listarFuncionariosComFolhas() {
        List<FuncionarioResponseDto> funcionarios = funcionarioService.listarFuncionariosComFolhas();
        return ResponseEntity.ok(funcionarios);
    }

    // ========== ENDPOINTS PARA TODOS AUTENTICADOS ==========

    @GetMapping("/consultar/{id}")
    @Operation(summary = "Consultar funcionário por ID", description = "Gerentes e colaboradores podem consultar (apenas ATIVOS)")
    public ResponseEntity<FuncionarioResponseDto> consultarFuncionario(@PathVariable Integer id) {
        FuncionarioResponseDto funcionario = funcionarioService.consultarFuncionarioPorId(id);
        return ResponseEntity.ok(funcionario);
    }

    @GetMapping("/listar-ativos")
    @Operation(summary = "Listar funcionários ativos", description = "Todos usuários autenticados podem ver lista de ativos")
    public ResponseEntity<List<FuncionarioResponseDto>> listarFuncionariosAtivos() {
        List<FuncionarioResponseDto> funcionarios = funcionarioService.listarFuncionariosAtivos();
        return ResponseEntity.ok(funcionarios);
    }

    // ========== ENDPOINT PARA TESTES ==========

    @GetMapping("/buscar-por-matricula/{matricula}")
    @Operation(summary = "Buscar por matrícula", description = "Endpoint para testes - buscar funcionário por matrícula")
    public ResponseEntity<FuncionarioResponseDto> buscarPorMatricula(@PathVariable String matricula) {
        FuncionarioResponseDto funcionario = funcionarioService.buscarPorMatricula(matricula);
        return ResponseEntity.ok(funcionario);
    }
}