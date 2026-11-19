package br.com.minhacarteira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.minhacarteira.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
