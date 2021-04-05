package gvm.hardware.cpu;

import gvm.hardware.Bus;

public class Register<T> {
    Bus<T> inputBus;
    Bus<T> outputBus;
    T registedData;

    public Register(Bus<T> inputBus, Bus<T> outputBus) {
        this.inputBus = inputBus;
        this.outputBus = outputBus;
    }
    void set(){
        registedData = inputBus.getData();
    }
    //Simplify data transfer, which is irrelevant to data bus.
    void set(T data){
        registedData = data;
    }
    T enable(){
        outputBus.setData(registedData);
        return registedData;
    }
    void turnOff(){
        outputBus.clear();
    }
}
