package mmt.source.com.babitafuels.Model;

public class Pump {
    private int bunkId;
    private String pumpId;
    private String pumpNum;
    private String fuelType;
    private String action;

    public int getBunkId() {
        return bunkId;
    }

    public void setBunkId(int bunkId) {
        this.bunkId = bunkId;
    }

    public String getPumpNum() {
        return pumpNum;
    }

    public void setPumpNum(String pumpNum) {
        this.pumpNum = pumpNum;
    }

    public String getPumpId() {
        return pumpId;
    }

    public void setPumpId(String pumpId) {
        this.pumpId = pumpId;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
