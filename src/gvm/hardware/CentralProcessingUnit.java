package gvm.hardware;

import gvm.hardware.cpu.*;
import jdk.internal.util.xml.impl.Input;

import java.util.HashMap;

public class CentralProcessingUnit {
    //Other Devices
    private Bus<String> addressBus;
    private Bus<String> dataBus;
    private Bus<InputContorlCmd> inputContorlBus;

    //Contorl unit of cpu
    private ControlUnit cu;
    //Arithmetic Logic Unit of cpu
    private ArithmeticLogicUnit alu;

    public CentralProcessingUnit(Bus<String>addressBus,
                                 Bus<String> dataBus,
                                 HashMap<String, Bus<InputContorlCmd>> inputContorlBusList) {
        //Busses
        this.addressBus = addressBus;
        this.dataBus = dataBus;
        this.inputContorlBus = inputContorlBus;
        //Main components
        Register<String> aluOutputRegister = new Register<>(new Bus<>(), dataBus);
        Register<String> aluTemporaryRegister = new Register<>(dataBus, new Bus<>());
        Register<Flags> flagsRegister = new Register<>(new Bus<>(), new Bus<>());
        this.alu = new ArithmeticLogicUnit(dataBus, aluOutputRegister, aluTemporaryRegister, flagsRegister);
        this.cu = new ControlUnit();
        cu.setAlu(alu);
        cu.setAluOutputRegister(aluOutputRegister);
        cu.setAluTemporaryRegister(aluTemporaryRegister);
        cu.setFlagsRegister(flagsRegister);
        cu.setAXRegister(new Register<>(dataBus, dataBus));
        cu.setBXRegister(new Register<>(dataBus, dataBus));
        cu.setCXRegister(new Register<>(dataBus, dataBus));
        cu.setDXRegister(new Register<>(dataBus, dataBus));
        cu.setInstructionRegister(new Register<>(dataBus, new Bus<>()));
        cu.setInstructionAddressRegister(new Register<>(dataBus, dataBus));
        cu.setInputContorlBusList(inputContorlBusList);
        cu.setFlagsRegister(flagsRegister);
    }
    public void insert(RandomAccessMemory ram, HashMap<String, InputDevice> inputList, HashMap<String, OutputDevice> outputList) {
        cu.setRam(ram);
        cu.setInput(inputList);
        cu.setOutput(outputList);
        cu.setRam(ram);
    }
    public void execute(){
        try {
            cu.execute();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMemoryAddressRegister(Register<String> memoryAddressRegister) {
        cu.setMemoryAddressRegister(memoryAddressRegister);
    }

    public void setDisk(Disk disk) {
        cu.setDisk(disk);
    }

    public void load() {
        cu.load();
    }
}
