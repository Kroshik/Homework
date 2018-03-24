package ru.sberbank.homework.dergun.task_02;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

class Collect {
    private String mainColor;
    private Map<Integer, String> mapColor = new HashMap<>();

    public void setMainColor(String mainColor) {
        this.mainColor = mainColor;
        mapColor.put(mapColor.size(), mainColor);
    }

    public void setMapColor(Map<Integer, String> mapColor) {
        this.mapColor = mapColor;
    }

    public Map<Integer, String> getMapColor() {
        return mapColor;
    }

    public void setMapColor(Integer integer) {

    }

    public String getMainColor() {
        return mainColor;
    }

    public void setMainColor(Integer index) {
        if (mapColor.size() < index) {
            this.mainColor = mapColor.get(index);
        }
    }

    public Integer getCrash(Integer a) {
        return 5;
    }

    public void setCrash(Integer a) {

    }
}

class Collector {
    private int countKey;
    private String mainColor;
    private Map<Integer, String> mapColor = new HashMap<>();

    public void setMainColor(String mainColor) {
        this.mainColor = mainColor;
        countKey++;
        mapColor.put(countKey, mainColor);
    }

    public void setMapColor(Map<Integer, String> mapColor) {
        this.mapColor = mapColor;
    }

    public int getCountKey() {
        return countKey;
    }

    public String getMainColor() {
        return mainColor;
    }

    public void setMainColor(Integer index) {
        if (mapColor.size() < index) {
            this.mainColor = mapColor.get(index);
        }
    }

    public Map<Integer, String> getMapColor() {
        return mapColor;
    }

    public void setMapColor(Integer integer) {

    }

    public void setNothing() {

    }

    public Integer getCrash(Integer a) {
        return 5;
    }

    public void setCrash(Integer a) {

    }
}

public class ReflectionFieldCopierTest {
    @Test
    public void copy() throws Exception {
        Collect from = new Collect();
        Collector to = new Collector();
        from.setMainColor("blue");
        to.setMainColor("red");
        Assert.assertEquals("blue", from.getMainColor());
        Assert.assertEquals("red", to.getMainColor());
        ReflectionFieldCopier reflectionFieldCopier = new ReflectionFieldCopier();
        reflectionFieldCopier.copy(from, to);
        System.out.println(from.getMainColor());
        System.out.println(to.getMainColor());
        Assert.assertEquals(from.getMainColor(), to.getMainColor());
        Map<Integer, String> mapFrom = new HashMap<>();
        mapFrom.put(0, "blue");
        mapFrom.put(1, "white");
        mapFrom.put(2, "black");
        from.setMapColor(mapFrom);
        Map<Integer, String> mapTo = new HashMap<>();
        mapTo.put(0, "black");
        mapTo.put(1, "white");
        mapTo.put(2, "black");
        mapTo.put(3, "blue");
        mapTo.put(4, "bluerr");
        to.setMapColor(mapTo);
        System.out.println(from.getMapColor());
        System.out.println(to.getMapColor());
        reflectionFieldCopier.copy(to, from);
        Assert.assertEquals(from.getMapColor(), to.getMapColor());
        System.out.println(from.getMapColor());
        System.out.println(to.getMapColor());

    }


}