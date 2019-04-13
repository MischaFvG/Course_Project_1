package com.cat;

import com.google.gson.annotations.Expose;

import java.util.List;

public class SaveObjects {
    @Expose
    private int activeObjectNumber;
    @Expose
    private List<Shape> shapeList;

    public SaveObjects(int activeObjectNumber, List<Shape> shapeList) {
        this.activeObjectNumber = activeObjectNumber;
        this.shapeList = shapeList;
    }

    public int getActiveObjectNumber() {
        return activeObjectNumber;
    }

    public List<Shape> getShapeList() {
        return shapeList;
    }

    @Override
    public String toString() {
        return "SaveObjects{" +
                "activeObjectNumber=" + activeObjectNumber +
                ", shapeList=" + shapeList +
                '}';
    }
}
