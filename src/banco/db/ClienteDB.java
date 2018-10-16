/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.db;

import banco.modelo.Cliente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nathan Not
 */
public class ClienteDB {

    private static final String URL = "jdbc:sqlite:banco.db";
    private static final String TABLE = "cliente";
    private Connection conn;

    private PreparedStatement selectTodos;
    private PreparedStatement selectOne;
    private PreparedStatement insertNovo;
    private PreparedStatement update;
    private PreparedStatement delete;
    private PreparedStatement deleteAll;

    public ClienteDB() {
        try {
            conn = DriverManager.getConnection(URL);

            createTable();

            selectTodos = conn.prepareStatement("SELECT * FROM cliente");
            selectOne = conn.prepareStatement("SELECT * FROM cliente WHERE id=?");
            insertNovo = conn.prepareStatement("INSERT INTO cliente (nome, endereco, cpf, rg, telefone, rendaMensal) VALUES (?,?,?,?,?,?)");
            update = conn.prepareStatement("UPDATE cliente SET nome=?, endereco=?, cpf=?, rg=?, telefone=?, rendaMensal=? WHERE id=?");
            delete = conn.prepareStatement("DELETE FROM cliente WHERE id=?");
            deleteAll = conn.prepareStatement("DELETE FROM cliente");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void createTable() throws SQLException {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS " + TABLE
                + "  (id                    INTEGER,"
                + "   nome                  VARCHAR(50),"
                + "   endereco              VARCHAR(255),"
                + "   cpf                   LONG,"
                + "   rg                    LONG,"
                + "   telefone              LONG,"
                + "   rendaMensal           DOUBLE,"
                + "   PRIMARY KEY (id))";

        Statement stmt = conn.createStatement();
        stmt.execute(sqlCreate);
    }

    private Cliente getClienteFromRs(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("endereco"),
                rs.getLong("cpf"),
                rs.getLong("rg"),
                rs.getLong("telefone"),
                rs.getDouble("rendaMensal")
        );
    }

    // obtém todas as pessoas
    public List<Cliente> getPessoas() {
        List<Cliente> resultado = null;
        ResultSet rs = null;

        try {
            rs = selectTodos.executeQuery();
            resultado = new ArrayList<>();

            while (rs.next()) {
                resultado.add(getClienteFromRs(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                close();
            }
        }
        return resultado;
    }

    // obtém todas as pessoas
    public Cliente getPessoa(int id) {
        Cliente resultado = null;
        ResultSet rs = null;

        try {
            selectOne.setInt(1, id);
            
            rs = selectOne.executeQuery();

            while (rs.next()) {
                resultado = getClienteFromRs(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                close();
            }
        }
        return resultado;
    }

    public int addCliente(Cliente c) {
        int resultado = addCliente(c.getNome(), c.getEndereco(), c.getCpf(), c.getRg(), c.getTelefone(), c.getRendaMensal());

        try {

            ResultSet generatedKeys = insertNovo.getGeneratedKeys();

            if (generatedKeys.next()) {
                c.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating client failed, no ID obtained.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            close();
        }

        return resultado;
    }

    // adiciona uma pessoa
    public int addCliente(String nome, String endereco, Long cpf, Long rg, Long telefone, double rendaMensal) {
        int resultado = 0;

        try {
            insertNovo.setString(1, nome);
            insertNovo.setString(2, endereco);
            insertNovo.setLong(3, cpf);
            insertNovo.setLong(4, rg);
            insertNovo.setLong(5, telefone);
            insertNovo.setDouble(6, rendaMensal);

            // insere e retorna o numero de linhas atualizadas
            resultado = insertNovo.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            close();
        }

        return resultado;
    }

    public int updateCliente(Cliente c) {
        return updateCliente(c.getId(), c.getNome(), c.getEndereco(), c.getCpf(), c.getRg(), c.getTelefone(), c.getRendaMensal());
    }

    public int updateCliente(int id, String nome, String endereco, Long cpf, Long rg, Long telefone, double rendaMensal) {
        int resultado = 0;

        try {
            update.setString(1, nome);
            update.setString(2, endereco);
            update.setLong(3, cpf);
            update.setLong(4, rg);
            update.setLong(5, telefone);
            update.setDouble(6, rendaMensal);

            update.setInt(7, id);

            // retorna o numero de linhas atualizadas
            resultado = update.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            close();
        }

        return resultado;
    }

    public int deleteCliente(Cliente c) {
        return deleteCliente(c.getId());
    }

    public int deleteCliente(int id) {
        int resultado = 0;

        try {
            delete.setInt(1, id);
            // deleta e retorna o numero de linhas atualizadas
            resultado = delete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            close();
        }

        return resultado;
    }

    public int deleteAllEntries() {
        int resultado = 0;

        try {
            // deleta e retorna o numero de linhas atualizadas
            resultado = deleteAll.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            close();
        }

        return resultado;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
