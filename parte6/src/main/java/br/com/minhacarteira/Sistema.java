package br.com.minhacarteira;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.minhacarteira.model.Despesa;
import br.com.minhacarteira.model.Receita;
import br.com.minhacarteira.model.Transacao;
import br.com.minhacarteira.repository.TransacaoRepository;

@SpringBootApplication
public class Sistema implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Sistema.class, args);
    }

    @Autowired
    private TransacaoRepository repository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- Salvando transações ---");
        Receita receita = new Receita("Salário Mensal", 5000.00, "05/02/2026");
        Despesa despesa = new Despesa("Conta de Luz", 150.00, "15/02/2026");

        repository.save(receita);
        repository.save(despesa);

        System.out.println("--- Listando transações do banco ---");
        List<Transacao> transacoes = repository.findAll();
        for (Transacao t : transacoes) {
            System.out.println(t);
        }
    }
}
