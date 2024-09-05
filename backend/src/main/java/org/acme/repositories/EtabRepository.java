package org.acme.repositories;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.sql.DataSource;

import org.acme.entities.Etablissement;
import org.acme.entities.University;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EtabRepository {
    @Inject
    DataSource dataSource;

    private Encoder encoder = Base64.getEncoder();
    private Decoder decoder = Base64.getDecoder();

    public Etablissement getEtablissementById(String idFac) throws SQLException{
        Etablissement etab= null;
        String sql = "select ID_ETAB, NOM_FR_ETAB, NOM_AR_ETAB, UNI, PHOTO_ETAB from Etablissement where id_etab=?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);){
                ps.setString(1, idFac);
                try(ResultSet rs =ps.executeQuery()){
                    if(!rs.next())
                        return null;
                    University uni =GeneralOperations.getUniversityById(rs.getString("UNI"), dataSource, encoder);
                    etab = new Etablissement(rs.getString("ID_ETAB"), 
                    rs.getString("NOM_FR_ETAB"), rs.getString("NOM_AR_ETAB"), 
                    uni, 
                    encoder.encodeToString(rs.getBytes("PHOTO_ETAB")));
                    return etab;
                }
        }
    }

    public boolean updateEtudiant(Etablissement etab)throws SQLException {
        String updateQuery = "UPDATE Etablissement SET " +
            "NOM_FR_ETAB = ?, " +
            "NOM_AR_ETAB = ?, " +
            "UNI = ?, " +
            "PHOTO_ETAB = ? " +
            "WHERE ID_ETAB = ?";

        try (Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, etab.nomFrEtab());
            pstmt.setString(2, etab.nomArEtab());
            pstmt.setString(3, etab.uni().idUni());
            pstmt.setBlob(4, new ByteArrayInputStream(decoder.decode(etab.photoEtab())));
            pstmt.setString(5, etab.idEtab());
        
            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected>0)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existEtablissement(String idEtab)throws SQLException {
        String sql = "select\n" + // select
        "    case\n" + // case
        "        when count(*)>0 then 1\n" + // condition
        "        else 0\n" + //
        "    end \n" + // end case
        "    exist\n" + // column name
        "from Etablissement\n" + // table name
        "where ID_ETAB=?";
        try(Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, idEtab);
            try(ResultSet rs = ps.executeQuery()){
                rs.next();
                return rs.getBoolean("exist");
            }
        }
    }
    /* This year's uni students 
     * select au, count(etu) etudiants from inscription 
        where au = (select max(au) from inscription)
        group by au;
     */
}
