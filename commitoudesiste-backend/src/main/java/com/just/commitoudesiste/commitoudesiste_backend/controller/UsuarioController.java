package com.just.commitoudesiste.commitoudesiste_backend.controller;

import com.just.commitoudesiste.commitoudesiste_backend.model.Usuario;
import com.just.commitoudesiste.commitoudesiste_backend.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            logger.warn("[USUÁRIO] Nome inválido ao tentar criar usuário");
            return ResponseEntity.badRequest().body("Nome do usuário é obrigatório");
        }

        try {
            usuario.setDataCriacao(LocalDateTime.now());
            Usuario novoUsuario = usuarioService.salvar(usuario);
            logger.info("[USUÁRIO] Criado com sucesso: {}", novoUsuario);
            return ResponseEntity.ok(novoUsuario);
        } catch (Exception e) {
            logger.error("[USUÁRIO] Erro ao criar: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Erro ao criar usuário: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        try {
            logger.info("[USUÁRIO] Listando todos os usuários...");
            return ResponseEntity.ok(usuarioService.buscarTodos());
        } catch (Exception e) {
            logger.error("[USUÁRIO] Erro ao listar todos: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        if (id <= 0) {
            logger.warn("[USUÁRIO] ID inválido ao buscar: {}", id);
            return ResponseEntity.badRequest().body("ID deve ser maior que zero");
        }

        try {
            Optional<Usuario> usuario = usuarioService.buscarPorId(id);
            if (usuario.isPresent()) {
                logger.info("[USUÁRIO] Encontrado: {}", usuario.get());
                return ResponseEntity.ok(usuario.get());
            } else {
                logger.warn("[USUÁRIO] Não encontrado com ID: {}", id);
                return ResponseEntity.status(404).body("Usuário não encontrado");
            }
        } catch (Exception e) {
            logger.error("[USUÁRIO] Erro ao buscar por ID: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Erro ao buscar usuário: " + e.getMessage());
        }
    }

    @GetMapping("/buscar-por-cpf-cnpj")
    public ResponseEntity<?> buscarPorCpfCnpj(@RequestParam String cpfCnpj) {
        if (cpfCnpj == null || cpfCnpj.isEmpty()) {
            logger.warn("[USUÁRIO] CPF/CNPJ inválido ao buscar");
            return ResponseEntity.badRequest().body("CPF/CNPJ é obrigatório");
        }

        try {
            Optional<Usuario> usuario = usuarioService.buscarPorCpfCnpj(cpfCnpj);
            if (usuario.isPresent()) {
                logger.info("[USUÁRIO] Encontrado por CPF/CNPJ: {}", usuario.get());
                return ResponseEntity.ok(usuario.get());
            } else {
                logger.warn("[USUÁRIO] Não encontrado com CPF/CNPJ: {}", cpfCnpj);
                return ResponseEntity.status(404).body("Usuário não encontrado");
            }
        } catch (Exception e) {
            logger.error("[USUÁRIO] Erro ao buscar por CPF/CNPJ: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Erro ao buscar usuário: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario atualizado) {
        if (id <= 0) {
            logger.warn("[USUÁRIO] ID inválido ao atualizar: {}", id);
            return ResponseEntity.badRequest().body("ID deve ser maior que zero");
        }

        if (atualizado.getNome() == null || atualizado.getNome().isEmpty()) {
            logger.warn("[USUÁRIO] Nome inválido ao atualizar usuário");
            return ResponseEntity.badRequest().body("Nome do usuário é obrigatório");
        }

        try {
            Optional<Usuario> usuarioExistente = usuarioService.buscarPorId(id);
            if (usuarioExistente.isEmpty()) {
                logger.warn("[USUÁRIO] Não encontrado para atualização com ID: {}", id);
                return ResponseEntity.status(404).body("Usuário não encontrado");
            }

            Usuario usuario = usuarioExistente.get();
            usuario.setNome(atualizado.getNome());
            usuario.setCpfCnpj(atualizado.getCpfCnpj());
            usuario.setTipo(atualizado.getTipo());

            Usuario salvo = usuarioService.salvar(usuario);
            logger.info("[USUÁRIO] Atualizado com sucesso: {}", salvo);
            return ResponseEntity.ok(salvo);
        } catch (Exception e) {
            logger.error("[USUÁRIO] Erro ao atualizar: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        if (id <= 0) {
            logger.warn("[USUÁRIO] ID inválido ao deletar: {}", id);
            return ResponseEntity.badRequest().body("ID deve ser maior que zero");
        }

        try {
            Optional<Usuario> usuario = usuarioService.buscarPorId(id);
            if (usuario.isEmpty()) {
                logger.warn("[USUÁRIO] Não encontrado para exclusão com ID: {}", id);
                return ResponseEntity.status(404).body("Usuário não encontrado");
            }

            usuarioService.deletar(id);
            logger.info("[USUÁRIO] Deletado com sucesso com ID: {}", id);
            return ResponseEntity.ok("Usuário deletado com sucesso");
        } catch (Exception e) {
            logger.error("[USUÁRIO] Erro ao deletar: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Erro ao deletar usuário: " + e.getMessage());
        }
    }
}