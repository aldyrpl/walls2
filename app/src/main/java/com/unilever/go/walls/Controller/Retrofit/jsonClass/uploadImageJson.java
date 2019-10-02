package com.unilever.go.walls.Controller.Retrofit.jsonClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class uploadImageJson {
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

        @SerializedName("create_date")
        @Expose
        private String createDate;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("ref_menu_category_id")
        @Expose
        private String refMenuCategoryId;
        @SerializedName("auth_user_id_create")
        @Expose
        private String authUserIdCreate;
        @SerializedName("author_id")
        @Expose
        private String authorId;
        @SerializedName("author")
        @Expose
        private String author;

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getRefMenuCategoryId() {
            return refMenuCategoryId;
        }

        public void setRefMenuCategoryId(String refMenuCategoryId) {
            this.refMenuCategoryId = refMenuCategoryId;
        }

        public String getAuthUserIdCreate() {
            return authUserIdCreate;
        }

        public void setAuthUserIdCreate(String authUserIdCreate) {
            this.authUserIdCreate = authUserIdCreate;
        }

        public String getAuthorId() {
            return authorId;
        }

        public void setAuthorId(String authorId) {
            this.authorId = authorId;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

    }
}
