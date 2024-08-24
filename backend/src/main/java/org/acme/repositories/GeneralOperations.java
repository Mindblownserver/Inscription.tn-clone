package org.acme.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64.Encoder;

import javax.sql.DataSource;

import org.acme.entities.Etablissement;
import org.acme.entities.University;

public class GeneralOperations {

    public static University getUniversityById(String idUni, DataSource dataSource, Encoder encoder) throws SQLException{
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
    public static Etablissement getEtablissementById(String idFac, DataSource dataSource, Encoder encoder) throws SQLException{
        Etablissement etab;
        String sql = "select ID_ETAB, NOM_FR_ETAB, NOM_AR_ETAB, UNI, PHOTO_ETAB from Etablissement where id_etab=?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);){
                ps.setString(1, idFac);
                try(ResultSet rs =ps.executeQuery()){
                    rs.next();
                    University uni =GeneralOperations.getUniversityById(rs.getString("UNI"), dataSource, encoder);
                    etab = new Etablissement(rs.getString("ID_ETAB"), 
                    rs.getString("NOM_FR_ETAB"), rs.getString("NOM_AR_ETAB"), 
                    uni, 
                    encoder.encodeToString(rs.getBytes("PHOTO_ETAB")));
                    return etab;
                }
        }
    }

}
