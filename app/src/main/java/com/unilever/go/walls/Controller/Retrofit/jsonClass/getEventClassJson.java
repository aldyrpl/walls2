package com.unilever.go.walls.Controller.Retrofit.jsonClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class getEventClassJson {
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

    public class ListDatum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("day")
        @Expose
        private String day;
        @SerializedName("day_name")
        @Expose
        private String dayName;
        @SerializedName("due_date")
        @Expose
        private String dueDate;
        @SerializedName("due_time")
        @Expose
        private String dueTime;
        @SerializedName("background_color")
        @Expose
        private String backgroundColor;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("is_alarm")
        @Expose
        private String isAlarm;
        @SerializedName("nomor")
        @Expose
        private Integer nomor;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getDayName() {
            return dayName;
        }

        public void setDayName(String dayName) {
            this.dayName = dayName;
        }

        public String getDueDate() {
            return dueDate;
        }

        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }

        public String getDueTime() {
            return dueTime;
        }

        public void setDueTime(String dueTime) {
            this.dueTime = dueTime;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIsAlarm() {
            return isAlarm;
        }

        public void setIsAlarm(String isAlarm) {
            this.isAlarm = isAlarm;
        }

        public Integer getNomor() {
            return nomor;
        }

        public void setNomor(Integer nomor) {
            this.nomor = nomor;
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
