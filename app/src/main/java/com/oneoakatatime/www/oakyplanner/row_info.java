package com.oneoakatatime.www.oakyplanner;

/**
 * Created by AIGARS on 16/07/2017.
 */

public class row_info {
  private String description;
    private String from;
    private String until;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public row_info(String from, String until, String description, int id){
        this.id = id;
        this.setDescription(description);
        this.setFrom(from);
        this.setUntil(until);
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUntil() {
        return until;
    }

    public void setUntil(String until) {
        this.until = until;
    }
}
