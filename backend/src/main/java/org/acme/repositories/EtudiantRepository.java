package org.acme.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;

import javax.sql.DataSource;

import org.acme.entities.Etudiant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EtudiantRepository {
    @Inject 
    DataSource dataSource;

    private Encoder encoder = Base64.getEncoder();

    public List<Etudiant> getEtudiants()throws SQLException{
        List<Etudiant> etuList = new ArrayList<>();
        Encoder encoder = Base64.getEncoder();
        String sql = "SELECT CIN, NOM_FR_ETU, PRENOM_FR_ETU, NOM_AR_ETU, PRENOM_AR_ETU, DATE_NAISS, PHOTO_ETU, NOM_PERE, PRENOM_PERE, PROFESSION_PERE, NOM_MERE, PRENOM_MERE, PROFESSION_MERE FROM Etudiant";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Etudiant etu = new Etudiant(rs.getString("CIN"), 
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
                    etuList.add(etu);
                }
                return etuList;
        }
    }

    public Etudiant getEtudiantByCin(String cin)throws SQLException {
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
