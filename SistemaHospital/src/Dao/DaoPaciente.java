/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Beans.BeansPacientes;
import Conex.ConexaoBD;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 *
 * @author Notorius B.I.G
 */
public class DaoPaciente {

    //BeansPacientes pac = new BeansPacientes();
    ConexaoBD conex = new ConexaoBD();
    ConexaoBD conexBairro = new ConexaoBD();
    String nomeBairro;
    int codBai;

    public void Salvar(BeansPacientes pac) {
        buscaBaiCod(pac.getNomeBairro());
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("insert into pacientes (paci_nome,paci_rg,paci_telefone,paci_rua,paci_cep,paci_complemento,paci_baicodigo,paci_nasc) values(?,?,?,?,?,?,?,?)");
            pst.setString(1, pac.getNomePac());
            pst.setString(2, pac.getRg());
            pst.setString(3, pac.getTelefone());
            pst.setString(4, pac.getRua());
            pst.setString(5, pac.getCep());
            pst.setString(6, pac.getComplemento());
            pst.setInt(7, codBai);
            pst.setString(8, pac.getNasc());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Paciente Salvo com Sucesso ");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar bairro" + e.getMessage());
        }
        conex.desconecta();
    }

    public void Alterar(BeansPacientes pac) {
        buscaBaiCod(pac.getNomeBairro());
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("update pacientes set paci_nome=?,paci_rg=?,paci_telefone=?,paci_rua=?,paci_cep=?,paci_complemento=?,paci_baicodigo=?,paci_nasc=?,paci_baicodigo=? where paci_codigo=?");
            pst.setString(1, pac.getNomePac());
            pst.setString(2, pac.getRg());
            pst.setString(3, pac.getTelefone());
            pst.setString(4, pac.getRua());
            pst.setString(5, pac.getCep());
            pst.setString(6, pac.getComplemento());
            pst.setInt(7, codBai);
            pst.setString(8, pac.getNasc());
            pst.setInt(9, pac.getCodPac());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Paciente Alterar com Sucesso ");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar bairro" + e.getMessage());
        }
        conex.desconecta();
    }

    public void buscaBaiCod(String nome) {
        conex.conexao();
        conex.executaSql("select *from bairro where badnome ='" + nome + "'");
        try {
            conex.rs.first();
            codBai = conex.rs.getInt("badcodigo");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar bairro" + e.getMessage());
        }
        conex.desconecta();
    }

    public BeansPacientes buscaPaciente(BeansPacientes pac) {
        conex.conexao();
        try {
            conex.executaSql("select * from pacientes where paci_nome like'%" + pac.getPequisas() + "%'");
            conex.rs.first();
            buscaNomeBairro(conex.rs.getInt("paci_baicodigo"));
            pac.setNomePac(conex.rs.getString("paci_nome"));
            pac.setCep(conex.rs.getString("paci_cep"));
            pac.setCodPac(conex.rs.getInt("paci_codigo"));
            pac.setComplemento(conex.rs.getString("paci_complemento"));
            pac.setNasc(conex.rs.getString("paci_nasc"));
            pac.setRg(conex.rs.getString("paci_rg"));
            pac.setTelefone(conex.rs.getString("paci_telefone"));
            pac.setRua(conex.rs.getString("paci_rua"));
            pac.setNomeBairro(nomeBairro);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Paciente Nao Existe. " + ex.getMessage());

        }
        conex.desconecta();
        return pac;
    }

    public void buscaNomeBairro(int cod) {
        conexBairro.conexao();
        try {
            conexBairro.executaSql("select *from bairro where badcodigo=" + cod);
            conexBairro.rs.first();
            nomeBairro = conexBairro.rs.getString("badnome");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar Nome do bairro" + e.getMessage());

        }
        conexBairro.desconecta();
    }

    public void Excluir(BeansPacientes pac) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("delete from pacientes where paci_codigo=?");
            pst.setInt(1, pac.getCodPac());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Paciente Excluido com Sucesso ");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao Paciente Excluido com Sucesso " + e.getMessage());
        }
        conex.desconecta();
    }

}
