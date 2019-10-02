package com.unilever.go.walls.Controller.Retrofit.jsonClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class myProfileClassJson {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("error")
    @Expose
    private List<Error> error = null;
    @SerializedName("result")
    @Expose
    private Result result;

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

    public List<Error> getError() {
        return error;
    }

    public void setError(List<Error> error) {
        this.error = error;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("id_auth_user")
        @Expose
        private String idAuthUser;
        @SerializedName("id_auth_user_grup")
        @Expose
        private String idAuthUserGrup;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("id_ref_active")
        @Expose
        private String idRefActive;
        @SerializedName("image")
        @Expose
        private String image;

        public String getIdAuthUser() {
            return idAuthUser;
        }

        public void setIdAuthUser(String idAuthUser) {
            this.idAuthUser = idAuthUser;
        }

        public String getIdAuthUserGrup() {
            return idAuthUserGrup;
        }

        public void setIdAuthUserGrup(String idAuthUserGrup) {
            this.idAuthUserGrup = idAuthUserGrup;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getIdRefActive() {
            return idRefActive;
        }

        public void setIdRefActive(String idRefActive) {
            this.idRefActive = idRefActive;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }

    public class Error {

        @SerializedName("field")
        @Expose
        private String field;
        @SerializedName("message")
        @Expose
        private String message;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}
