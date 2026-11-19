package br.com.minhacarteira.dto;

public class TransacaoDTO {

    private Long id;
    private String descricao;
    private double valor;
    private String data;
    private String tipo; // "RECEITA" ou "DESPESA"

    public TransacaoDTO() {
    }

    public TransacaoDTO(Long id, String descricao, double valor, String data, String tipo) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}