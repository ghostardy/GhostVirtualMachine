package gvm.hardware;

import gvm.hardware.cpu.InputContorlCmd;
import gvm.hardware.cpu.Register;

import java.util.HashMap;

public class MotherBoard {
    private Bus<String> addressBus;
    private Bus<String> dataBus;
    private HashMap<String, Bus<InputContorlCmd>> inputContorlBuses;
    private CentralProcessingUnit cpu;
    private RandomAccessMemory ram;
    private Disk disk;
    private Register<String> ramAddressRegister;
    private Register<String> diskRegister;
    private HashMap<String, InputDevice> inputList;
    private HashMap<String, OutputDevice> outputList;

    public MotherBoard(){
        addressBus = new Bus<>();
        dataBus = new Bus<>();
        inputContorlBuses = new HashMap<>();
        inputList = new HashMap<>();
        outputList = new HashMap<>();
        inputContorlBuses.put("input01", new Bus<>());
        ramAddressRegister = new Register<>(dataBus, addressBus);
        diskRegister = new Register<>(new Bus<>(), dataBus);
    }

    public void setUp(){
        this.cpu = new CentralProcessingUnit(addressBus, dataBus, inputContorlBuses);
        this.ram = new RandomAccessMemory();
        this.disk = new Disk();
        InputDevice input = new InputDevice();
        OutputDevice output = new OutputDevice();

        ram.insert(addressBus, dataBus);
        ram.setMemoryAddressRegister(ramAddressRegister);
        disk.setDataBus(dataBus);
        disk.setDiskRegister(diskRegister);
        input.insert(dataBus, inputContorlBuses.get("input01"));
        output.insert(dataBus);
        inputList.put("input01", input);
        outputList.put("output01", output);
        output.setDeviceAddress("output01");
        cpu.insert(ram, inputList, outputList);
        cpu.setMemoryAddressRegister(ramAddressRegister);
        cpu.setDisk(disk);
    }

    public void powerOn(){
        cpu.load();
        cpu.execute();
    }

    public HashMap<String, InputDevice> getInputList() {
        return inputList;
    }

    public Disk getDisk() {
        return disk;
    }
}
