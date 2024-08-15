package org.acme.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Base64.Encoder;

import javax.sql.DataSource;

import org.acme.entities.Etablissement;
import org.acme.entities.Etudiant;
import org.acme.entities.Fill_Etab;
import org.acme.entities.Filliere;
import org.acme.entities.Inscription;
import org.acme.entities.University;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class InscriptionRepository {
    @Inject
    DataSource dataSource;

    private Encoder encoder = Base64.getEncoder();

    public List<Inscription> getInscriptions() throws SQLException{
        List<Inscription> inscripList= new ArrayList<>();
        String sql = "select i.Etu, i.AU, i.FAC, i.Fill, i.Niveau, i.paid, fe.periode from Inscription i, FILL_ETAB fe where fe.niveau=i.niveau and "+
        "fe.Fill=i.FILL and fe.ETAB = i.FAC";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Etudiant etu = getEtudiantByCin(rs.getString("ETU"));
                    Etablissement etab = getEtablissementById(rs.getString("FAC"));
                    Filliere fill= getFilliereById(rs.getString("Fill"), rs.getInt("Niveau"));
                    Inscription insc = new Inscription(etu, rs.getString("AU"), new Fill_Etab(etab, fill,rs.getInt("PERIODE"),rs.getInt("PRIX_TOTAL"),rs.getInt("PRIX_TR1"), rs.getInt("PRIX_TR2")), rs.getInt("PAID"));
                    inscripList.add(insc);
                }
                return inscripList;

        }
    }

    public List<Inscription> getInscription(String cin) throws SQLException{
        List<Inscription> inscripList= new ArrayList<>();
        String sql = "select i.Etu, i.AU, i.FAC, i.Fill, i.Niveau, i.paid, fe.periode, fe.PRIX_TOTAL, fe.PRIX_TR1, fe.PRIX_TR2 "+ 
        "from Inscription i, FILL_ETAB fe where fe.niveau=i.niveau and "+
        "fe.Fill=i.FILL and fe.ETAB = i.FAC and i.ETU=?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(1, cin);
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        Etudiant etu = getEtudiantByCin(rs.getString("ETU"));
                        Etablissement etab = getEtablissementById(rs.getString("FAC"));
                        Filliere fill= getFilliereById(rs.getString("Fill"), rs.getInt("Niveau"));
                        Inscription insc = new Inscription(etu, rs.getString("AU"), new Fill_Etab(etab, fill,rs.getInt("PERIODE"),rs.getInt("PRIX_TOTAL"),rs.getInt("PRIX_TR1"), rs.getInt("PRIX_TR2")),rs.getInt("PAID"));
                        inscripList.add(insc);
                    }
                    return inscripList;
            }

        }
    }

    private Filliere getFilliereById(String fillId, int niveau) throws SQLException{
        Filliere fill;
        String sql = "select ID_FILL, NOM_AR_FILL, NOM_FR_FILL, NIVEAU from Filliere where ID_FILL=? and Niveau=?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);){
                ps.setString(1, fillId);
                ps.setInt(2, niveau);
                try(ResultSet rs =ps.executeQuery()){
                    rs.next();
                    fill = new Filliere(fillId, rs.getString("NOM_AR_fill"), rs.getString("NOM_FR_FILL"), 
                    niveau);
                    return fill;
                }
        }
    }

    private Etablissement getEtablissementById(String idFac) throws SQLException{
        Etablissement etab;
        String sql = "select ID_ETAB, NOM_FR_ETAB, NOM_AR_ETAB, UNI, PHOTO_ETAB from Etablissement where id_etab=?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);){
                ps.setString(1, idFac);
                try(ResultSet rs =ps.executeQuery()){
                    rs.next();
                    University uni =getUniversityById(rs.getString("UNI"));
                    etab = new Etablissement(rs.getString("ID_ETAB"), 
                    rs.getString("NOM_FR_ETAB"), rs.getString("NOM_AR_ETAB"), 
                    uni, 
                    encoder.encodeToString(rs.getBytes("PHOTO_ETAB")));
                    return etab;
                }
        }
    }

    private University getUniversityById(String idUni) throws SQLException{
        University uni;
        String sql = "select ID_UNI, NOM_FR_UNI, NOM_AR_UNI, PHOTO_UNI from University where ID_UNI=?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);){
                ps.setString(1, idUni);
                try(ResultSet rs =ps.executeQuery()){
                    rs.next();
                    uni =new University(idUni, rs.getString("NOM_FR_UNI"), 
                    rs.getString("NOM_AR_UNI"), 
                    encoder.encodeToString(rs.getBytes("PHOTO_UNI")));
                    return uni;
                }
        }
    }

    private Etudiant getEtudiantByCin(String cin)throws SQLException {
        Etudiant etu;
        String sql = "SELECT CIN, NOM_FR_ETU, PRENOM_FR_ETU, NOM_AR_ETU,"+
        "PRENOM_AR_ETU, DATE_NAISS, PHOTO_ETU, NOM_PERE, PRENOM_PERE,"+ 
        "PROFESSION_PERE, NOM_MERE, PRENOM_MERE, PROFESSION_MERE FROM Etudiant "+
        "where cin=?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);){
                ps.setString(1, cin);
                try(ResultSet rs =ps.executeQuery()){
                    rs.next();
                    
                    etu =new Etudiant(rs.getString("CIN"), 
                    rs.getString("NOM_FR_ETU"), 
                    rs.getString("PRENOM_FR_ETU"),
                    rs.getString("NOM_AR_ETU"),
                    rs.getString("PRENOM_AR_ETU"),
                    rs.getDate("DATE_NAISS"),
                    rs.getString("NOM_PERE"),
                    rs.getString("PRENOM_PERE"),
                    rs.getString("PROFESSION_PERE"),
                    rs.getString("NOM_MERE"),
                    rs.getString("PRENOM_MERE"),
                    rs.getString("PROFESSION_MERE"),
                    encoder.encodeToString(rs.getBytes("PHOTO_ETU"))
                    );
                    return etu;
                }
        }
    }

}
