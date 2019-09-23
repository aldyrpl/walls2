package com.unilever.go.walls.Controller.SQL;

public class user_model {

    String id;
    String id_user_group;
    String group_name;
    String fullname;
    String img;

    // contrustor(empty)
    public user_model() {
    }

    // constructor
    public user_model(String id, String id_user_group, String group_name, String fullname, String img) {
        this.id = id;
        this.id_user_group = id_user_group;
        this.group_name = group_name;
        this.fullname = fullname;
        this.img = img;
    }

    /*Setter and Getter*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_user_group() {
        return id_user_group;
    }

    public void setId_user_group(String id_user_group) {
        this.id_user_group = id_user_group;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}