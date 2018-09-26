package com.example.muhammad.easytour.PojoClass;

public class AddMomentPojo {

    private int cameraImage;
    private String momentDescription;
    private String key;

    public AddMomentPojo() {
    }

    public AddMomentPojo(int cameraImage, String momentDescription) {
        this.cameraImage = cameraImage;
        this.momentDescription = momentDescription;
    }

    public AddMomentPojo(int cameraImage, String momentDescription, String key) {
        this.cameraImage = cameraImage;
        this.momentDescription = momentDescription;
        this.key = key;
    }

    public int getCameraImage() {
        return cameraImage;
    }

    public String getMomentDescription() {
        return momentDescription;
    }

    public String getKey() {
        return key;
    }
}
