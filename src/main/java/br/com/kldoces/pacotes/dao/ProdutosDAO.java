package br.com.kldoces.pacotes.dao;

import br.com.kldoces.pacotes.connection.Conexao;
import br.com.kldoces.pacotes.models.Produtos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProdutosDAO {

    public Produtos cadastrar(Produtos produtos) {
        String sql = "INSERT INTO tb_produtos (Nome, Preco)" +
                "VALUES (?, ?) ";

        Connection conn;
        PreparedStatement ps;

        try {
            conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ps.setString(1, produtos.getNome());
            ps.setDouble(2, produtos.getPreco());

            ps.execute();
            ps.close();
            conn.close();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return produtos;
    }

    public List<Produtos> listar() {
        String sql = "SELECT * FROM tb_produtos";

        List<Produtos> produtos = new ArrayList<>();

        Connection conn;
        PreparedStatement ps;
        ResultSet rs;

        try {
            conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(); // realização de consulta

            while (rs.next()) {
                Produtos produto = new Produtos();

                produto.setCodigoP(rs.getInt(1));
                produto.setNome(rs.getString(2)); // recuperar nome SQL
                produto.setPreco(rs.getDouble(3)); // recuperar preco SQL

                produtos.add(produto);
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return produtos;
    }

    public Produtos listarPorCodigo(int codigo) {
        String sql = "SELECT * FROM tb_produtos WHERE codigoP = ?";

        Connection conn;
        PreparedStatement ps;
        ResultSet rs;
        Produtos produto = null;

        try {
            conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, codigo);
            rs = ps.executeQuery();

            if (rs.next()) {
                produto = new Produtos();
                produto.setCodigoP(rs.getInt("codigoP"));
                produto.setNome(rs.getString("Nome"));
                produto.setPreco(rs.getDouble("Preco"));
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return produto;
    }

    public void remover() {
        Scanner leitura = new Scanner(System.in);

        System.out.println("Qual produto gostaria de remover da tabela?\n" +
                "\nDigite o código do produto para remove-lo.");
        int escolhaRemocao = leitura.nextInt();

        String sql = "DELETE FROM tb_produtos WHERE codigoP = ?";

        Connection conn;
        PreparedStatement ps;

        try{
            conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, escolhaRemocao);
            System.out.println("Produto do código: " + escolhaRemocao + " " +  " " +
                    "removido com sucesso!");

            ps.execute();
            ps.close();
            conn.close();
        }  catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void modificar() {
        Scanner leitura = new Scanner(System.in);

        System.out.println("\nO que você quer mudar na tabela? Nome/Preco");
        String nomeOuPreco = leitura.nextLine();

        System.out.println("Digite o código do produto:");
        int codigoProduto = leitura.nextInt();

        // Limpar o buffer (o \n) após a leitura do número inteiro
        leitura.nextLine();

        Connection conn;
        PreparedStatement ps;
        String sql;

        if (nomeOuPreco.equalsIgnoreCase("nome")) {
            System.out.println("Digite o novo nome:");
            String novoNome = leitura.nextLine();
            sql = "UPDATE tb_produtos SET nome = ? WHERE codigoP = ?";

            try {
                conn = Conexao.getConexao();
                ps = conn.prepareStatement(sql);
                ps.setString(1, novoNome);
                ps.setInt(2, codigoProduto);

                ps.executeUpdate();
                ps.close();
                conn.close();
                System.out.println("Nome do produto atualizado com sucesso!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else if (nomeOuPreco.equalsIgnoreCase("preco")) {
            System.out.println("Digite o novo preço:");
            double novoPreco = leitura.nextDouble();
            sql = "UPDATE tb_produtos SET preco = ? WHERE codigoP = ?";

            try {
                conn = Conexao.getConexao();
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, novoPreco);
                ps.setInt(2, codigoProduto);

                ps.executeUpdate();
                ps.close();
                conn.close();
                System.out.println("Preço do produto atualizado com sucesso!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Opção inválida. Por favor, escolha 'nome' ou 'preco'.");
        }
    }

}

