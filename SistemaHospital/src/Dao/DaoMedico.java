/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Conex.ConexaoBD;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import Beans.BeansMedicos;

/**
 *
 * @author Notorius B.I.G
 */
public class DaoMedico {

    ConexaoBD conex = new ConexaoBD();
    BeansMedicos mod = new BeansMedicos();

    public void Salvar(BeansMedicos mod) {
        conex.conexao();
        PreparedStatement pst;
        try {
            pst = conex.con.prepareStatement("insert into medicos(nome_medico,especialidade_medico,crm_medico) values(?,?,?)");
            pst.setString(1, mod.getNome());
            pst.setString(2, mod.getEspecialidade());
            pst.setInt(3, mod.getCrm());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Dados Inserido Com Sucesso");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Inserir Dados" + ex.getMessage());
        }

        conex.desconecta();

    }

    public void Editar(BeansMedicos mod) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("update medicos set nome_medico=?, especialidade_medico=?,crm_medico=? where cod_medico=?");
            pst.setString(1, mod.getNome());
            pst.setString(2, mod.getEspecialidade());
            pst.setInt(3, mod.getCrm());
            pst.setInt(4, mod.getCodigo());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Dados Alterado Com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar o Dados " + ex.getMessage());
        }

        conex.desconecta();
    }

    public void Excluir(BeansMedicos mod) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("delete from medicos where cod_medico=?");
            pst.setInt(1, mod.getCodigo());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Dados Excluido Com Sucesso");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro Ao Deletar Os Dados" + ex.getMessage());

        }

        conex.desconecta();
    }

    public BeansMedicos buscaMedico(BeansMedicos mod) {
        conex.conexao();
        conex.executaSql("select * from medicos where nome_medico like'%" + mod.getPesquisa() + "%'");
        try {
            conex.rs.first();
            mod.setNome(conex.rs.getString("nome_medico"));
            mod.setEspecialidade(conex.rs.getString("especialidade_medico"));
            mod.setCrm(conex.rs.getInt("crm_medico"));
            mod.setCodigo(conex.rs.getInt("cod_medico"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Medico Nao Cadastrado");

        }
        conex.desconecta();
        return mod;
    }

}

/*

 nome_medico character varying(50) NOT NULL,
 especialidade_medico character varying(50) NOT NULL,
 crm_medico integer NOT NULL,
 cod_medico 
 */
