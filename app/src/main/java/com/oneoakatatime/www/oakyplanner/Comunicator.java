package com.oneoakatatime.www.oakyplanner;

/**
 * Created by User on 5/17/2017.
 */

public interface Comunicator {
    public void dateTransfer(int year,int month,int day);
    public void createInputEdit(long rowId);
    void populateActivity();

}
