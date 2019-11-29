package com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class galleryClassJson {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("code")
    @Expose
    private Integer code;
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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class ListDatum {

        @SerializedName("month")
        @Expose
        private String month;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;
        @SerializedName("nomor")
        @Expose
        private Integer nomor;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

        public Integer getNomor() {
            return nomor;
        }

        public void setNomor(Integer nomor) {
            this.nomor = nomor;
        }
    }

        public class Datum {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("category_id")
            @Expose
            private String categoryId;
            @SerializedName("author_id")
            @Expose
            private String authorId;
            @SerializedName("author")
            @Expose
            private String author;
            @SerializedName("category_name")
            @Expose
            private String categoryName;
            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("is_public")
            @Expose
            private String isPublic;
            @SerializedName("date_created")
            @Expose
            private String dateCreated;
            @SerializedName("month")
            @Expose
            private String month;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
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

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getIsPublic() {
                return isPublic;
            }

            public void setIsPublic(String isPublic) {
                this.isPublic = isPublic;
            }

            public String getDateCreated() {
                return dateCreated;
            }

            public void setDateCreated(String dateCreated) {
                this.dateCreated = dateCreated;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }
        }

            public class Paging {

                @SerializedName("total_data")
                @Expose
                private Integer totalData;
                @SerializedName("remark")
                @Expose
                private String remark;

                public Integer getTotalData() {
                    return totalData;
                }

                public void setTotalData(Integer totalData) {
                    this.totalData = totalData;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }

            }
    public class Result {

        @SerializedName("list_data")
        @Expose
        private List<ListDatum> listData = null;
        @SerializedName("paging")
        @Expose
        private Paging paging;

        public List<ListDatum> getListData() {
            return listData;
        }

        public void setListData(List<ListDatum> listData) {
            this.listData = listData;
        }

        public Paging getPaging() {
            return paging;
        }

        public void setPaging(Paging paging) {
            this.paging = paging;
        }
    }

}
