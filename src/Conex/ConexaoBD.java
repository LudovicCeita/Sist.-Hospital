/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conex;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.*;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 *
 * @author Notorius B.I.G
 */
public class ConexaoBD {

    //Import  as BiBlioteca de conexao 
    public Statement stm;
    public ResultSet rs;
    private String driver = "org.postgresql.Driver";
    private String caminho = "jdbc:postgresql://localhost:5432/projetoclinica";
    private String usuario = "postgres";
    private String senha = "92178029";
    public Connection con;

    public void conexao() {
        //setar Propriedade do Drice de Conexao 
        try {
            System.setProperty("jdbc.Drivers", driver);
            con = DriverManager.getConnection(caminho, usuario, senha);
            //JOptionPane.showConfirmDialog(null, "Conexao Efetuda Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro Na Conexao : \n -> " + ex.getMessage());
        }
    }
    
    public void executaSql(String sql){
        try {
            //difere de maisc , minnusc
            stm = con.createStatement(rs.TYPE_SCROLL_INSENSITIVE , rs.CONCUR_READ_ONLY);
            rs = stm.executeQuery(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro AO Executar o Sql : \n -> " + ex.getMessage());
           }
    }
    
    
    public void desconecta() {
        try {
            con.close();
            // JOptionPane.showConfirmDialog(null, "BD Desconectado Com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showConfirmDialog(null, "Erro ao Fechar Conexao com BD : \n -> " + ex.getMessage());
        }
    }

}
