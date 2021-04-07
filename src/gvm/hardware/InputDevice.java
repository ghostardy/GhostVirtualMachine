package gvm.hardware;

import gvm.hardware.cpu.InputContorlCmd;

public class InputDevice {
    private String inputData;
    private Bus<String> dataBus;
    private Bus<InputContorlCmd> inputContorlBus;

    public InputDevice() {
    }

    public void insert(Bus<String> dataBus, Bus<InputContorlCmd> inputContorlBus) {
        this.dataBus = dataBus;
        this.inputContorlBus = inputContorlBus;
    }

    //Called by Computer User
    public void input(String data){
        inputData = data;
        inputContorlBus.setData(InputContorlCmd.INTERRUPT);
    }

    public void enable(){
        dataBus.setData(inputData);
    }
}
