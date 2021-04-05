package gvm.hardware;

import gvm.hardware.cpu.*;

public class CentralProcessingUnit {
    //Other Devices
    private Bus<Integer> addressBus;
    private Bus<String> dataBus;

    //Contorl unit of cpu
    private ControlUnit cu;
    //Arithmetic Logic Unit of cpu
    private ArithmeticLogicUnit alu;

    public CentralProcessingUnit(Bus<Integer>addressBus, Bus<String> dataBus) {
        //Busses
        this.addressBus = addressBus;
        this.dataBus = dataBus;
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
    }
    public void execute(RandomAccessMemory ram, InputDevice input, OutputDevice output){
        cu.setRam(ram);
        cu.setInput(input);
        cu.setOutput(output);
        cu.execute();
    }

    public void setMemoryAddressRegister(Register<String> memoryAddressRegister) {
        cu.setMemoryAddressRegister(memoryAddressRegister);
    }
}
