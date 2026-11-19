package br.com.minhacarteira.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("DESPESA")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Despesa extends Transacao {

    public Despesa(String descricao, double valor, String data) throws ValorInvalidoException {
        super(descricao, valor, data);
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("=== DESPESA ===");
        System.out.println("Descrição: " + getDescricao());
        System.out.println("Valor: -R$ " + getValor());
        System.out.println("Data: " + getData());
        System.out.println("===============");
    }
}
