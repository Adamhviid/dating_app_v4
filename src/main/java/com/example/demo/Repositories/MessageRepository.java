package com.example.demo.Repositories;

import com.example.demo.Models.Message;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageRepository {
    ProfileRepository rp = new ProfileRepository();
    List<Message> allMessages = new ArrayList<>();

    public void sendMessage(int senderId, int receiverId, String message) throws SQLException {
        PreparedStatement ps = rp.establishConnection().prepareStatement("INSERT INTO messages(senderId,receiverId,msg) VALUES(?,?,?)");
        ps.setInt(1,senderId);
        ps.setInt(2,receiverId);
        ps.setString(3,message);

        ps.executeUpdate();
    }

    public List<Message> seeMessage(int currentId) throws SQLException {
        PreparedStatement ps = rp.establishConnection().prepareStatement("SELECT senderId,recieverId,message FROM messages WHERE senderId = ? OR receiverId = ?");
        ps.setInt(1,currentId);
        ps.setInt(2,currentId);

        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Message temp = new Message(rs.getInt(1), rs.getInt(2), rs.getString(3));
            allMessages.add(temp);
        }
        return allMessages;
    }
}
