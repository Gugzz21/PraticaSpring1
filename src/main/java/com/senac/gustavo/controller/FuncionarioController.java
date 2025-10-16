package com.senac.gustavo.controller;

import com.senac.gustavo.entity.Funcionario;
import com.senac.gustavo.service.FuncionarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/funcionario")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    //Endpoints para Listar Funcionários
    @GetMapping("/listar")
    public ResponseEntity<List<Funcionario>> listarFuncionarios(){

        return ResponseEntity.ok(funcionarioService.listarTodos());

    }


    @GetMapping("/listar/{id}")
    public ResponseEntity<Funcionario> listarFuncionarios(@PathVariable("id") Integer id){

        return ResponseEntity.ok(funcionarioService.listarPorId(id));

    }

}
