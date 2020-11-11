package com.example.demo.Repositories;

import com.example.demo.Models.Profile;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProfileRepository {

    //liste med alle profiler
    List<Profile> allProfiles = new ArrayList<>();
    //List<Profile> searchLogin = new ArrayList<Profile>();

    //Denne metode laver forbindelsen til mysql databasen
    private Connection establishConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/dating_app?serverTimezone=UTC", "root", "Orange10");
        //standard: user=root, password=1
    }
    //Metode i stedet for dupliceret kode
    //Foretager en ps.execute og læser resulSet ind i allProfiles array
    public List<Profile> returnProfile(PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();

        //lave resultattet om til objekter, og derefter ind i en arrayliste
        while (rs.next()) {
            Profile temp = new Profile(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getInt(7));
            allProfiles.add(temp);
        }
        return allProfiles;
    }

    public List<Profile> listAllProfiles() throws SQLException{
        allProfiles.clear();
        //lavet et statement og eksekvere en query
        PreparedStatement ps = establishConnection().prepareStatement(" SELECT * FROM profiles;");

        return returnProfile(ps);
    }


    public void createProfile(String pName, String pKodeord, String pGender, String pEmail, String pDescription, int pAdmin) throws SQLException {
        allProfiles.clear();
        //lavet et statement og eksekvere en query
        PreparedStatement ps = establishConnection().prepareStatement("INSERT INTO profiles (name, kodeord, gender,email,description) VALUES (?,?,?,?,?);");

        ps.setString(1,pName);
        ps.setString(2,pKodeord);
        ps.setString(3,pGender);
        ps.setString(4,pEmail);
        ps.setString(5,pDescription);

        ps.executeUpdate();
    }
    //metode til at teste om et username allerede findes i databasen
    public boolean testUsernameViability(String email) throws SQLException{
        boolean usernameIsViable;
        PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM profiles where email = ?");
        ps.setString(1,email);
        ResultSet rs = ps.executeQuery();
        usernameIsViable = rs.next();
        return usernameIsViable;
    }

    public void deleteProfile(int id) throws SQLException {
        PreparedStatement ps = establishConnection().prepareStatement("DELETE FROM profiles WHERE id=?");
        ps.setInt(1,id);
        ps.executeUpdate();
    }

    public void editProfile(int id, String name, String gender, String email, String description) throws SQLException {
        PreparedStatement ps = establishConnection().prepareStatement("UPDATE profiles SET name = ?, gender = ?, email = ?, description = ? where id= ?");
        ps.setString(1,name);
        ps.setString(2,gender);
        ps.setString(3,email);
        ps.setString(4,description);
        ps.setInt(5,id);
        ps.executeUpdate();
    }

    public List<Profile> searchProfile(String gender) throws SQLException {
        allProfiles.clear();
        PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM profiles where gender like ?");
        ps.setString(1,gender);

        return returnProfile(ps);
    }


    public List<Profile> searchLogin(String email, String kodeord) throws SQLException {
        allProfiles.clear();
        PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM profiles where email = ? AND kodeord = ?");
        ps.setString(1, email);
        ps.setString(2, kodeord);

        return returnProfile(ps);
    }

    // Finder bruger med x id
    public List<Profile> profile(int id) throws SQLException {
        allProfiles.clear();
        PreparedStatement ps = establishConnection().prepareStatement("SELECT * FROM profiles where id = ?");
        ps.setInt(1, id);

        return returnProfile(ps);
    }
}
