package org.acme.repositories;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.List;

import javax.sql.DataSource;

import org.acme.entities.Etudiant;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EtudiantRepository {
    @Inject 
    DataSource dataSource;

    private Encoder encoder = Base64.getEncoder();
    private Decoder decoder = Base64.getDecoder();

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
        if(cin.isEmpty()|| cin.length()!=8|| !cin.matches("^\\d{8}$")){
            return null;
        }
        Etudiant etu = null;
        String sql = "SELECT CIN, NOM_FR_ETU, PRENOM_FR_ETU, NOM_AR_ETU,"+
        "PRENOM_AR_ETU, DATE_NAISS, PHOTO_ETU, NOM_PERE, PRENOM_PERE,"+ 
        "PROFESSION_PERE, NOM_MERE, PRENOM_MERE, PROFESSION_MERE FROM Etudiant "+
        "where cin=?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);){
                ps.setString(1, cin);
                try(ResultSet rs =ps.executeQuery()){

                    if(!rs.next())
                        return null;

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

    public boolean existEtudiant(String cin)throws SQLException{

        String sql = "select\n" + // select
                    "    case\n" + // case
                    "        when count(*)>0 then 1\n" + // condition
                    "        else 0\n" + //
                    "    end \n" + // end case
                    "    exist\n" + // column name
                    "from Etudiant\n" + // table name
                    "where cin=?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(1, cin);
                try(ResultSet rs = ps.executeQuery()){
                    rs.next();
                    return rs.getBoolean("exist");
                }
        }
    }

    public boolean updateEtudiant(Etudiant etu)throws SQLException {
        String updateSQL = "UPDATE ETUDIANT SET " +
                "NOM_FR_ETU = ?, " +
                "PRENOM_FR_ETU = ?, " +
                "NOM_AR_ETU = ?, " +
                "PRENOM_AR_ETU = ?, " +
                "DATE_NAISS = ?, " +
                "PHOTO_ETU = ?, " +
                "NOM_PERE = ?, " +
                "PRENOM_PERE = ?, " +
                "PROFESSION_PERE = ?, " +
                "NOM_MERE = ?, " +
                "PRENOM_MERE = ?, " +
                "PROFESSION_MERE = ? " +
                "WHERE CIN = ?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(updateSQL)){
                ps.setString(1, etu.nomFrEtu());
                ps.setString(2, etu.prenomFrEtu());
                ps.setString(3, etu.nomArEtu());
                ps.setString(4, etu.prenomArEtu());
                ps.setDate(5, new java.sql.Date(etu.dateNaiss().getTime()));
                ps.setBlob(6, new ByteArrayInputStream(decoder.decode(etu.photoEtu())));
                ps.setString(7, etu.nomPere());
                ps.setString(8, etu.prenomPere());
                ps.setString(9, etu.professionPere());
                ps.setString(10, etu.nomMere());
                ps.setString(11, etu.prenomMere());
                ps.setString(12, etu.professionMere());
                ps.setString(13, etu.cin());
                
                int affectedRows = ps.executeUpdate();
                if(affectedRows>0)
                    return true;
                return false;
        }catch(Exception e){
            Log.error(e);
            return false;
        }
    }
}
