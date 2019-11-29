package com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class updateProfileClassJson {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("error")
    @Expose
    private List<Object> error = null;
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

    public List<Object> getError() {
        return error;
    }

    public void setError(List<Object> error) {
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
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("id_ref_jabatan")
        @Expose
        private String idRefJabatan;
        @SerializedName("id_ref_lokasi")
        @Expose
        private String idRefLokasi;
        @SerializedName("image")
        @Expose
        private String image;

        public String getIdAuthUser() {
            return idAuthUser;
        }

        public void setIdAuthUser(String idAuthUser) {
            this.idAuthUser = idAuthUser;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIdRefJabatan() {
            return idRefJabatan;
        }

        public void setIdRefJabatan(String idRefJabatan) {
            this.idRefJabatan = idRefJabatan;
        }

        public String getIdRefLokasi() {
            return idRefLokasi;
        }

        public void setIdRefLokasi(String idRefLokasi) {
            this.idRefLokasi = idRefLokasi;
        }


        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
