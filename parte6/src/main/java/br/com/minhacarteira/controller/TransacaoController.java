package br.com.minhacarteira.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.minhacarteira.dto.TransacaoDTO;
import br.com.minhacarteira.exception.DadosInvalidosException;
import br.com.minhacarteira.model.Despesa;
import br.com.minhacarteira.model.Receita;
import br.com.minhacarteira.model.Transacao;
import br.com.minhacarteira.model.ValorInvalidoException;
import br.com.minhacarteira.service.TransacaoService;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    @GetMapping
    public List<TransacaoDTO> listar() {
        List<TransacaoDTO> resposta = new ArrayList<>();
        for (Transacao t : service.listarTodas()) {
            resposta.add(paraDTO(t));
        }
        return resposta;
    }

    @GetMapping("/{id}")
    public TransacaoDTO buscarPorId(@PathVariable Long id) {
        return paraDTO(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<TransacaoDTO> criar(@RequestBody TransacaoDTO dto) {
        Transacao salva = service.salvar(paraEntidade(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(paraDTO(salva)); // 201
    }

    @PutMapping("/{id}")
    public TransacaoDTO atualizar(@PathVariable Long id, @RequestBody TransacaoDTO dto) {
        Transacao atualizada = service.atualizar(id, paraEntidade(dto));
        return paraDTO(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build(); // 204
    }

    // ---------- Conversões DTO <-> Entidade ----------

    private TransacaoDTO paraDTO(Transacao t) {
        String tipo = (t instanceof Receita) ? "RECEITA" : "DESPESA";
        return new TransacaoDTO(t.getId(), t.getDescricao(), t.getValor(), t.getData(), tipo);
    }

    private Transacao paraEntidade(TransacaoDTO dto) {
        try {
            // Os construtores já validam descrição e valor
            if ("RECEITA".equalsIgnoreCase(dto.getTipo())) {
                return new Receita(dto.getDescricao(), dto.getValor(), dto.getData());
            } else if ("DESPESA".equalsIgnoreCase(dto.getTipo())) {
                return new Despesa(dto.getDescricao(), dto.getValor(), dto.getData());
            }
        } catch (ValorInvalidoException | IllegalArgumentException e) {
            throw new DadosInvalidosException(e.getMessage());
        }
        throw new DadosInvalidosException("O tipo deve ser RECEITA ou DESPESA.");
    }
}