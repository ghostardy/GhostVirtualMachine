package gvm.hardware;

import gvm.hardware.cpu.Register;

public class MotherBoard {
    private Bus<Integer> addressBus;
    private Bus<String> dataBus;
    private Register<String> ramAddressRegister;
    private CentralProcessingUnit cpu;
    private RandomAccessMemory ram;
    private InputDevice input;
    private OutputDevice output;

    public MotherBoard(){
        addressBus = new Bus<>();
        dataBus = new Bus<>();
    }

    public void plugIn(){
        this.cpu = new CentralProcessingUnit(addressBus, dataBus);
        this.ram = new RandomAccessMemory(addressBus, dataBus);
        this.input = new InputDevice();
        this.output = new OutputDevice();
    }

    public void start(){
        cpu.execute(ram, input, output);
    }

    //BIOS System
    public static void main(String [] args){
        MotherBoard mb = new MotherBoard();
        mb.plugIn();
        mb.start();
    }
}
