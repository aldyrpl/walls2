package com.unilever.go.walls.Controller.SQL;

public class user_model {

    String id;
    String id_user_group;
    String group_name;
    String fullname;
    String img;

    String email;
    String password;
    // contrustor(empty)
    public user_model() {
    }

    // constructor
//    public user_model(String id, String id_user_group, String group_name, String fullname, String img) {
//        this.id = id;
//        this.id_user_group = id_user_group;
//        this.group_name = group_name;
//        this.fullname = fullname;
//        this.img = img;
//    }

    public user_model(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /*Setter and Getter*/

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getId_user_group() {
//        return id_user_group;
//    }
//
//    public void setId_user_group(String id_user_group) {
//        this.id_user_group = id_user_group;
//    }
//
//    public String getGroup_name() {
//        return group_name;
//    }
//
//    public void setGroup_name(String group_name) {
//        this.group_name = group_name;
//    }
//
//    public String getFullname() {
//        return fullname;
//    }
//
//    public void setFullname(String fullname) {
//        this.fullname = fullname;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}