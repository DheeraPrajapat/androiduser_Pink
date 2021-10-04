package com.marius.valeyou.data.beans;

public class BookingModel  {

    /**
     * id : 278
     * endTime : 1605119400
     * startTime : 1605033000
     * jobType : 0
     */

    private int id;
    private String endTime;
    private String startTime;
    private int jobType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }
}
