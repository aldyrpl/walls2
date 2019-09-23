package com.unilever.go.walls.Controller.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class loginClassJson {


    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("result")
    @Expose
    private Result result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {


        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("id_user_group")
        @Expose
        private String idUserGroup;
        @SerializedName("group_name")
        @Expose
        private String groupName;
        @SerializedName("fullname")
        @Expose
        private String fullname;
        @SerializedName("img")
        @Expose
        private String img;
        @SerializedName("token")
        @Expose
        private String token;
//
//        public Result() {
//        }
//
//        public Result(String id) {
//            this.id = id;
//        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdUserGroup() {
            return idUserGroup;
        }

        public void setIdUserGroup(String idUserGroup) {
            this.idUserGroup = idUserGroup;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

}

