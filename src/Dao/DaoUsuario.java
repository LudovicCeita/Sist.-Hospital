/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Beans.BeansMedicos;
import Beans.BeansUsuarios;
import Conex.ConexaoBD;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Notorius B.I.G
 */
public class DaoUsuario {

    ConexaoBD conex = new ConexaoBD();
    BeansUsuarios mod = new BeansUsuarios();

    public void Salvar(BeansUsuarios mod) {
        conex.conexao();
        PreparedStatement pst;
        try {
            pst = conex.con.prepareStatement("insert into usuarios(usu_nome,usu_senha,usu_tipo) values(?,?,?)");
            pst.setString(1, mod.getUsuNome());
            pst.setString(2, mod.getUsuSenha());
            pst.setString(3, mod.getUsuTipo());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Usuario Inserido Com Sucesso");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Inserir o Usuario" + ex.getMessage());
        }

        conex.desconecta();

    }

    public void Alterar(BeansUsuarios mod) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("update usuarios set usu_nome=?, usu_senha=?,usu_tipo=? where usu_cod=?");
            pst.setString(1, mod.getUsuNome());
            pst.setString(2, mod.getUsuSenha());
            pst.setString(3, mod.getUsuTipo());
            pst.setInt(4, mod.getUsuCod());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Usuario Alterado Com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na Alterar do Usuario " + ex.getMessage());
        }

        conex.desconecta();
    }

    public BeansUsuarios buscaUsuario(BeansUsuarios mod) {
        conex.conexao();
        conex.executaSql("select * from usuarios where usu_nome like'%" + mod.getUsuPesquisar() + "%'");
        try {
            conex.rs.first();
            mod.setUsuCod(conex.rs.getInt("usu_cod"));
            mod.setUsuNome(conex.rs.getString("usu_nome"));
            mod.setUsuTipo(conex.rs.getString("usu_tipo"));
            mod.setUsuSenha(conex.rs.getString("usu_senha"));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Usuario Nao Cadastrado "+ex.getMessage());

        }
        conex.desconecta();
        return mod;
    }

    public void Excluir(BeansUsuarios mod) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("delete from usuarios where usu_cod=?");
            pst.setInt(1, mod.getUsuCod());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Usuario Excluido Com Sucesso");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro Ao Excluir O Usuario"+ ex.getMessage());

        }

        conex.desconecta();
    }

}
