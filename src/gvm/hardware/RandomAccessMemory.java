package gvm.hardware;

import gvm.hardware.cpu.Register;

import java.awt.*;
import java.util.HashMap;

public class RandomAccessMemory {
    private Bus<String> addressBus;
    private Bus<String> dataBus;
    private Register<String> memoryAddressRegister;
    private HashMap<String, String> ram = new HashMap<>();
    private final int MAX_MEMORY_ADDRESS = 1024;

    public RandomAccessMemory(){
    }
    public void insert(Bus<String> addressBus, Bus<String> dataBus){
        this.addressBus = addressBus;
        this.dataBus = dataBus;
    }
    //Controlled by cpu via control bus
    public void set(){
        memoryAddressRegister.enable();
        String address = addressBus.getData();
        if (Integer.parseInt(address) > 0 || Integer.parseInt(address) <= MAX_MEMORY_ADDRESS){
            ram.put(address, dataBus.getData());
        }else {
            System.out.println("Set failure, Out of Memory!");
        }
    }
    //Controlled by cpu via control bus
    public void enable(){
        memoryAddressRegister.enable();
        String address = addressBus.getData();
        if (Integer.parseInt(address)>=0 && Integer.parseInt(address)<=MAX_MEMORY_ADDRESS){
            dataBus.setData(ram.get(address));
        }else {
            System.out.println("Get failure, Out of Memory!");
        }
    }

    public void setMemoryAddressRegister(Register<String> memoryAddressRegister) {
        this.memoryAddressRegister = memoryAddressRegister;
    }

    public void trace(){
        for (String key : ram.keySet()) {
            System.out.print(key);
            System.out.print("\t");
            System.out.println(ram.get(key));
        }
    }
}
