import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {

    public void salvar(Transacao t) throws SQLException {
        String tipo;
        if (t instanceof Receita) {
            tipo = "RECEITA";
        } else if (t instanceof Despesa) {
            tipo = "DESPESA";
        } else {
            throw new IllegalArgumentException("Tipo de transação desconhecido.");
        }

        String sql = "INSERT INTO transacoes (descricao, valor, data, tipo) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoFactory.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getDescricao());
            ps.setDouble(2, t.getValor());
            ps.setString(3, t.getData());
            ps.setString(4, tipo);
            ps.executeUpdate();
        }
    }

    public List<Transacao> listar() throws SQLException {
        List<Transacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM transacoes";

        try (Connection conn = ConexaoFactory.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String descricao = rs.getString("descricao");
                double valor = rs.getDouble("valor");
                String data = rs.getString("data");
                String tipo = rs.getString("tipo");

                try {
                    if ("RECEITA".equals(tipo)) {
                        lista.add(new Receita(descricao, valor, data));
                    } else if ("DESPESA".equals(tipo)) {
                        lista.add(new Despesa(descricao, valor, data));
                    }
                } catch (ValorInvalidoException e) {
                    System.out.println("Registro ignorado (valor inválido): " + descricao);
                }
            }
        }
        return lista;
    }
}
