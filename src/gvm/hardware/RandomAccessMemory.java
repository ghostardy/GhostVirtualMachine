package gvm.hardware;

import java.util.HashMap;

public class RandomAccessMemory {
    private Bus<Integer> addressBus;
    private Bus<String> dataBus;
    private HashMap<Integer, String> ram = new HashMap<>();

    public RandomAccessMemory(Bus<Integer> addressBus, Bus<String> dataBus){
        this.addressBus = addressBus;
        this.dataBus = dataBus;
    }
    //Controlled by cpu via control bus
    public void set(){
        Integer address = addressBus.getData();
        if (address.intValue() > 0 || address.intValue() <= 1024){
            ram.put(address, dataBus.getData());
        }
    }
    //Controlled by cpu via control bus
    public void enable(){
        Integer address = addressBus.getData();
        if (address.intValue() > 0 || address.intValue() <= 1024){
            dataBus.setData(ram.get(address));
        }
    }
}
