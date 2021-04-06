package gvm.hardware.cpu;

import gvm.hardware.Bus;

public class Register<T> {
    private Bus<T> inputBus;
    private Bus<T> outputBus;
    private T registedData;

    public Register(Bus<T> inputBus, Bus<T> outputBus) {
        this.inputBus = inputBus;
        this.outputBus = outputBus;
    }
    public void set(){
        registedData = inputBus.getData();
    }
    //Simplify data transfer, which is irrelevant to data bus.
    public void set(T data){
        registedData = data;
    }
    public T enable(){
        outputBus.setData(registedData);
        return registedData;
    }
    public void turnOff(){
        registedData = null;
        outputBus.clear();
    }
}
