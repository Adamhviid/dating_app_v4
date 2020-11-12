package com.example.demo.Controllers;

import com.example.demo.Models.Profile;
import com.example.demo.Repositories.MessageRepository;
import com.example.demo.Repositories.ProfileRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DatingController {

    ProfileRepository rp = new ProfileRepository();
    MessageRepository mp = new MessageRepository();
    List<Profile> allProfiles = new ArrayList<>();
    List<Profile> allCandidates = new ArrayList<>();
    Profile currentLogin = new Profile(0,null,null,null,null,null,0,null);

    // Root
    @GetMapping("/")
    public String index(Model profileModel) throws SQLException{
        profileModel.addAttribute("profile", rp.listAllProfiles());
        return "index";
    }

    // Create profile
    @PostMapping("/createprofile")
    public String createProfile(WebRequest createProfileData) throws SQLException {
        int admin = 0;
        String name = createProfileData.getParameter("pName");
        String gender = createProfileData.getParameter("pGender");
        String email = createProfileData.getParameter("pEmail");
        String description = createProfileData.getParameter("pDescription");
        String kodeord = createProfileData.getParameter("pKodeord");
        rp.createProfile(name, kodeord, gender, email, description, admin);
/*          //virker ikke :(
            if (rp.testUsernameViability("pEmail")) {
                rp.createProfile(name, kodeord, gender, email, description, admin);
                System.out.println("laver profil");
            } else {
                System.out.println("fejl");
                return "errorcreate";
            }
 */
        return "login";
    }

    //log in controller
    @PostMapping("/correctlogin")
    public String login(WebRequest loginData)  throws SQLException{
        String email = loginData.getParameter("pEmail");
        String kodeord = loginData.getParameter("pKodeord");
        allProfiles = rp.searchLogin(email,kodeord);

        try {
            currentLogin = allProfiles.get(0);
            if(currentLogin.getAdmin()==1){
                System.out.println("hej");
                return "redirect:/adminPage";
            }
            System.out.println("logged in as " + allProfiles.get(0).toString());
        } catch (IndexOutOfBoundsException e) {
            return "errorlogin";
        }
        return "main";
    }

    //admin page
    @GetMapping("/adminPage")
    public String admin(Model m) throws SQLException{
        allProfiles = rp.listAllProfiles();
        m.addAttribute("allProfiles", allProfiles);
        return "adminPage";
    }
    // Delete Profile gammel metode
//    @PostMapping("/deleteprofile")
//    public String deleteProfile(WebRequest deleteProfile) {
//        try {
//            int id = Integer.parseInt(deleteProfile.getParameter("delete"));
//            rp.deleteProfile(id);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return "redirect:/";
//    }

    //delete profil ny metode
    @PostMapping("/deleteprofile")
    public String deleteProfile(WebRequest deleteProfile) {
        try {
            String id = deleteProfile.getParameter("delete-admin");
            rp.deleteProfile(Integer.parseInt(id));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "redirect:/adminPage";
    }

    // Edit Profile
    @PostMapping("/editprofile")
    public String editProfile(WebRequest editProfile) {
        try {
            int id = currentLogin.getId();
            //int id = Integer.parseInt(editProfile.getParameter("eId"));
            String name = editProfile.getParameter("eName");
            String gender = editProfile.getParameter("eGender");
            String email = editProfile.getParameter("eEmail");
            String description = editProfile.getParameter("eDescription");
            rp.editProfile(id,name,gender,email,description);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "redirect:/myprofile";
    }

    // Search Profiles
    @GetMapping("/main")
    public String searchProfiles(Model searchModel, WebRequest searchProfile){
        String gender = searchProfile.getParameter("pGender");
        try {
            allProfiles = rp.searchProfile(gender);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        searchModel.addAttribute("profileList",allProfiles);
        return "main";
    }

    @PostMapping("/maincandidate")
    public String searchCandidates(Model candidateModel) throws SQLException {
        allCandidates = rp.candidateList(currentLogin.getId());
        candidateModel.addAttribute("candidateList",allCandidates);
        return "main";
    }

    //myprofile
    @GetMapping("/myprofile")
    public String myprofile(Model myprofileModel) throws SQLException {
        int id = currentLogin.getId();
        myprofileModel.addAttribute("profileID",rp.profile(id));
        return "myprofile";
    }

    //Login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Model profileModel) {
        profileModel.addAttribute("profileList",allProfiles);
        return "profile";
    }

    @PostMapping("/profileId")
    public String getProfile(WebRequest profileClick){
        String id = profileClick.getParameter("profileId");
        System.out.println(id);

        try {
            allProfiles = rp.profile(Integer.parseInt(id));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("hov");
        }

        return "redirect:/profile";
    }

    @PostMapping("/addtokandidat")
    public String kandidatList(WebRequest kandidatButton) throws SQLException {
        String candidateId = kandidatButton.getParameter("addToKandidat");
        int currentId = currentLogin.getId();
        rp.addCandidate(candidateId,currentId);
        System.out.println("Hej");
        return "redirect:/profile";
    }

    //Admin
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    //Om os
    @GetMapping("/omos")
    public String omos() {
        return "omos";
    }

    //Sugar Mommy
    @GetMapping("/sugarmommy")
    public String sugarmommy() {
        return "sugarmommy";
    }

    //Sugar Daddy
    @GetMapping("/sugardaddy")
    public String sugardaddy() {
        return "sugardaddy";
    }

    //Send message
    @PostMapping("/sendmessage")
    public String sendMessage(WebRequest receiverBtn, WebRequest messageInput) throws SQLException{
        int receiverId = Integer.parseInt(receiverBtn.getParameter("getReceiverId"));
        String msg = messageInput.getParameter("getMessage");
        mp.sendMessage(currentLogin.getId(),receiverId,msg);
        System.out.println(receiverId + ", " + msg);
        return "redirect:/profile";
    }
}




