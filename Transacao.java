public abstract class Transacao {

    private String descricao;
    private double valor;
    private String data;

    public Transacao(String descricao, double valor, String data) throws ValorInvalidoException {
        validarDescricao(descricao);
        validarValor(valor);
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        validarDescricao(descricao);
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) throws ValorInvalidoException {
        validarValor(valor);
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private static void validarDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição não pode ser vazia.");
        }
    }

    private static void validarValor(double valor) throws ValorInvalidoException {
        if (valor <= 0) {
            throw new ValorInvalidoException("O valor da transação deve ser maior que zero.");
        }
    }

    public abstract void exibirDetalhes();
}
