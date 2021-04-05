package gvm.hardware;

public class InputDevice {
    String inputData;
    Bus<String> dataBus;
    CentralProcessingUnit cpu;

    public InputDevice(Bus<String> dataBus, CentralProcessingUnit cpu) {
        this.dataBus = dataBus;
        this.cpu = cpu;
    }

    public void input(String data){
        inputData = data;
        cpu.interrupt(this);
    }

    public void enable(){
        dataBus.setData(inputData);
    }
}
