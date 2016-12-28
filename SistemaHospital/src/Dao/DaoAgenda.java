/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Beans.BeansAgenda;
import Conex.ConexaoBD;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 *
 * @author Notorius B.I.G
 */
public class DaoAgenda {

    BeansAgenda agenda = new BeansAgenda();
    ConexaoBD conex = new ConexaoBD();
    ConexaoBD conexPaciente = new ConexaoBD();
    ConexaoBD conexMedico = new ConexaoBD();
    int codMed;
    int codPac;

    public void Salvar(BeansAgenda agenda) {
        BuscaMedico(agenda.getNomeMed());
        BuscaPaciente(agenda.getNomePac());
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("insert into agenda (agenda_codpac,agemda_codMedico,agenda_motivp,agenda_turno,agenda_data,agenda_status) values(?,?,?,?,?,?)");
            pst.setInt(1, codPac);
            pst.setInt(2, codMed);
            pst.setString(3, agenda.getMotivo());
            pst.setString(4, agenda.getTurno());
            pst.setDate(5, new java.sql.Date(agenda.getData().getTime()));
            pst.setString(6, agenda.getStatus());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Agendamento marcado com Sucesso");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar o Agendamento " + ex.getMessage());

        }
        conex.desconecta();

    }

    public void BuscaMedico(String nomeMedico) {
        conexMedico.conexao();
        conexMedico.executaSql("select *from medicos where nome_medico='" + nomeMedico + "'");

        try {
            conexMedico.rs.first();
            codMed = conexMedico.rs.getInt("cod_medico");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Medico Nao Cadastrado " + ex.getMessage());
        }

    }

    public int BuscaCodMedico(String nomeMedico) {
        conexMedico.conexao();
        conexMedico.executaSql("select *from medicos where nome_medico='" + nomeMedico + "'");

        try {
            conexMedico.rs.first();
            codMed = conexMedico.rs.getInt("cod_medico");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Medico Nao Cadastrado " + ex.getMessage());
        }
        return codMed;

    }

    public void BuscaPaciente(String nomePaciente) {
        conexPaciente.conexao();
        conexPaciente.executaSql("select *from Pacientes where paci_nome='" + nomePaciente + "'");

        try {
            conexPaciente.rs.first();
            codPac = conexPaciente.rs.getInt("paci_codigo");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Medico Nao Cadastrado " + ex.getMessage());
        }

    }

       public int BuscaAgendamento(BeansAgenda agenda) {
        conexMedico.conexao();
        conexMedico.executaSql("select *from agenda where agenda_data='" +agenda.getData()+"'");

        try {
            conexMedico.rs.first();
            codMed = conexMedico.rs.getInt("cod_medico");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "agendamento nao encontrando" + ex.getMessage());
        }
        return codMed;

    }
    public void Alterar(BeansAgenda agenda) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("update agenda set agenda_status=? where agenda_cod=?");
            pst.setString(1, agenda.getStatus());
            pst.setInt(2, agenda.getAgendaCod());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Agendamento em atendimento");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao Atender o Agendamento" + e.getMessage());
        }
        conex.desconecta();
    }

    public BeansAgenda BuscaAgendaPorCodigo(int cod) {
        BeansAgenda agen = new BeansAgenda();
        conexMedico.conexao();
        conexMedico.executaSql("select *from agenda inner join pacientes on agenda_codpac=paci_codigo inner join medicos on agemda_codmedico=cod_medico where agenda_cod='" + cod + "'");

        try {
            conexMedico.rs.first();
            agen.setNomePac(conex.rs.getString("paci_nome"));
            agen.setNomeMed(conex.rs.getString("nome_medico"));
            agen.setMotivo(conex.rs.getString("agenda_motivp"));
            agen.setPacientesNasc(conex.rs.getString("paci_nasc"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Buscar agendamento por codigo !" + ex.getMessage());
        }
        return agen;
    }

}
