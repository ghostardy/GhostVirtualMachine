package gvm.hardware;

import gvm.hardware.cpu.Register;

public class OutputDevice {
    private Bus<String> dataBus;
    private boolean isReady = false;
    //Stored by output register on motherboard
    private String deviceAddress;

    public void insert(Bus<String> dataBus) {
        this.dataBus = dataBus;
    }
    public void output(){
        System.out.print("{" + dataBus.getData() + "}");
    }
    public void ready(){
        if (dataBus.getData().equals(deviceAddress)) {
            isReady = true;
        }else {
            isReady = false;
        }
    }
    public void done() {
        isReady = false;
    }
    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }
}
