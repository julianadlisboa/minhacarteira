package br.com.minhacarteira.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.minhacarteira.exception.DadosInvalidosException;
import br.com.minhacarteira.exception.TransacaoNaoEncontradaException;
import br.com.minhacarteira.model.Receita;
import br.com.minhacarteira.model.Transacao;
import br.com.minhacarteira.model.ValorInvalidoException;
import br.com.minhacarteira.repository.TransacaoRepository;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    public List<Transacao> listarTodas() {
        return repository.findAll();
    }

    public Transacao buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TransacaoNaoEncontradaException(id));
    }

    public Transacao salvar(Transacao transacao) {
        validarData(transacao);
        return repository.save(transacao);
    }

    public Transacao atualizar(Long id, Transacao dadosNovos) {
        Transacao existente = buscarPorId(id); // lança 404 se não existir
        validarData(dadosNovos);

        // Uma Receita não pode virar Despesa (e vice-versa) no mesmo registro
        boolean existenteEhReceita = existente instanceof Receita;
        boolean novaEhReceita = dadosNovos instanceof Receita;
        if (existenteEhReceita != novaEhReceita) {
            throw new DadosInvalidosException("Não é possível alterar o tipo da transação.");
        }

        try {
            existente.setDescricao(dadosNovos.getDescricao());
            existente.setValor(dadosNovos.getValor());
        } catch (ValorInvalidoException | IllegalArgumentException e) {
            throw new DadosInvalidosException(e.getMessage());
        }
        existente.setData(dadosNovos.getData());

        return repository.save(existente);
    }

    public void excluir(Long id) {
        Transacao existente = buscarPorId(id); // lança 404 se não existir
        repository.delete(existente);
    }

    // A coluna "data" é obrigatória no banco (nullable = false)
    private void validarData(Transacao transacao) {
        if (transacao.getData() == null || transacao.getData().trim().isEmpty()) {
            throw new DadosInvalidosException("A data é obrigatória.");
        }
    }
}