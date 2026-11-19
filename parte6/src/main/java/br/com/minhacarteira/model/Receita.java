package br.com.minhacarteira.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("RECEITA")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Receita extends Transacao implements Tributavel {

    public Receita(String descricao, double valor, String data) throws ValorInvalidoException {
        super(descricao, valor, data);
    }

    @Override
    public double calcularImposto() {
        return getValor() * 0.10; // 10% de imposto
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("=== RECEITA ===");
        System.out.println("Descrição: " + getDescricao());
        System.out.println("Valor: R$ " + getValor());
        System.out.println("Data: " + getData());
        System.out.println("Imposto Tributado (10%): R$ " + calcularImposto());
        System.out.println("===============");
    }
}
