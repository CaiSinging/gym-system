package cn.edu.nwafu.cie.se2019.gym.payload.physicalTest;

public class NewPhysicalTestRequest {
    private int height;//身高
    private float weight;//体重
    private float fat;//体脂
    private int vitalCapacity;//肺活量
    private float sitAndReach;//坐位体前屈
    private int sitUp;//仰卧起坐
    private float gripPower;//握力

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public int getVitalCapacity() {
        return vitalCapacity;
    }

    public void setVitalCapacity(int vitalCapacity) {
        this.vitalCapacity = vitalCapacity;
    }

    public float getSitAndReach() {
        return sitAndReach;
    }

    public void setSitAndReach(float sitAndReach) {
        this.sitAndReach = sitAndReach;
    }

    public int getSitUp() {
        return sitUp;
    }

    public void setSitUp(int sitUp) {
        this.sitUp = sitUp;
    }

    public float getGripPower() {
        return gripPower;
    }

    public void setGripPower(float gripPower) {
        this.gripPower = gripPower;
    }
}
