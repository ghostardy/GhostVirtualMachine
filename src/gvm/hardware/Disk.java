package gvm.hardware;

import gvm.hardware.cpu.Register;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Disk {
    private Register<String> diskRegister;
    private HashMap<String, String> program;
    private Bus<String> dataBus;
    private Iterator<Map.Entry<String, String>> position;
    private Map.Entry<String, String> next;

    public void setDiskRegister(Register<String> diskRegister) {
        this.diskRegister = diskRegister;
    }

    public void setDataBus(Bus<String> dataBus) {
        this.dataBus = dataBus;
    }

    public void setProgram(HashMap<String, String> program) {
        this.program = program;
        position = this.program.entrySet().iterator();
    }

    public void setNextAddress(){
        dataBus.setData(next.getKey());
    }
    public void setNextData(){
        dataBus.setData(next.getValue());
    }
    public boolean hasNext(){
        if(position.hasNext()) {
            next = position.next();
            return true;
        }
        return false;
    }
}
