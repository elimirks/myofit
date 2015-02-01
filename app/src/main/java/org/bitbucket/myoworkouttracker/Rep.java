package org.bitbucket.myoworkouttracker;

import android.support.annotation.NonNull;
import android.util.Log;

import com.thalmic.myo.Vector3;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by Ryan on 2015-02-01.
 */
public class Rep implements Serializable{
    private long duration;
    private long startTime, endTime;
    private int setNum, repNum;
    private ArrayList<AbstractMap.SimpleEntry<Long,Double>> dataX;
    private ArrayList<AbstractMap.SimpleEntry<Long,Double>> dataZ;

    public Rep(int setNum, int repNum, long startTime){
        this.setNum = setNum;
        this.repNum = repNum;
        this.duration = 0;
        this.startTime = startTime;
        this.endTime = startTime;
        dataX = new ArrayList<AbstractMap.SimpleEntry<Long,Double>>();
        dataZ = new ArrayList<AbstractMap.SimpleEntry<Long,Double>>();
    }

    public int getSetNum(){
        return setNum;
    }

    public int getRepNum(){
        return repNum;
    }

    public void addX(long time, double x){
        AbstractMap.SimpleEntry<Long,Double> map = new AbstractMap.SimpleEntry<Long,Double>(time,x);
        dataX.add(map);
    }

    public void addZ(long time, double y){
        AbstractMap.SimpleEntry<Long,Double> map = new AbstractMap.SimpleEntry<Long,Double>(time,y);
        dataZ.add(map);
    }

    public long getDuration (){
        return duration;
    }

    public void setDuration(long duration){
        this.duration = duration;
    }

    public String getDurationString(){
        int min = (int) (duration/1000)/60;
        int sec = (int) (duration/1000 - min * 60);
        if (min > 0)
            return Integer.toString(min) + "m" + Integer.toString(sec) + "s";
        else
            return Integer.toString(sec) + "s";
    }

    public void setEndTime(long endTime){
        this.endTime = endTime;
    }

    public long getTime(){
        return endTime - startTime;
    }

    public String getTimeString(){
        int min = (int) (getTime()/1000)/60;
        int sec = (int) (getTime()/1000 - min * 60);
        if (min > 0)
            return Integer.toString(min) + "m" + Integer.toString(sec) + "s";
        else
            return Integer.toString(sec) + "s";
    }

    public ArrayList<AbstractMap.SimpleEntry<Long,Double>> getDataX(){
        return dataX;
    }

    public ArrayList<AbstractMap.SimpleEntry<Long,Double>> getDataZ(){
        return dataZ;
    }

    public long getStartTime(){
        return startTime;
    }

}
