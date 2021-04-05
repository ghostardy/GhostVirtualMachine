package gvm.hardware.cpu;

import gvm.hardware.Bus;
import gvm.hardware.InputDevice;
import gvm.hardware.OutputDevice;
import gvm.hardware.RandomAccessMemory;
import jdk.internal.util.xml.impl.Input;

public class ControlUnit {
   //Registers for cu
    private Register<String> instructionRegister;
    private Register<String> instructionAddressRegister;
    //Registers for calc
    private Register<String> AXRegister;
    private Register<String> BXRegister;
    private Register<String> CXRegister;
    private Register<String> DXRegister;

    //Arithmetic Logic Unit
    private ArithmeticLogicUnit alu;
    //Registers for ALU
    private Register<String> aluOutputRegister;
    private Register<String> aluTemporaryRegister;
    private Register<Flags> flagsRegister;

    //Register for RAM
    private Register<String> memoryAddressRegister;

    //Devices
    private RandomAccessMemory ram;
    private OutputDevice output;
    private InputDevice input;

    public ControlUnit(){
    }

    public void execute(){
        while(true){
            try {
                Thread.sleep(1000);
                System.out.println("CPU running");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setAlu(ArithmeticLogicUnit alu) {
        this.alu = alu;
    }

    public void setAluOutputRegister(Register<String> aluOutputRegister) {
        this.aluOutputRegister = aluOutputRegister;
    }

    public void setAluTemporaryRegister(Register<String> aluTemporaryRegister) {
        this.aluTemporaryRegister = aluTemporaryRegister;
    }

    public void setFlagsRegister(Register<Flags> flagsRegister) {
        this.flagsRegister = flagsRegister;
    }

    public void setMemoryAddressRegister(Register<String> memoryAddressRegister) {
        this.memoryAddressRegister = memoryAddressRegister;
    }

    public void setInstructionRegister(Register<String> instructionRegister) {
        this.instructionRegister = instructionRegister;
    }

    public void setInstructionAddressRegister(Register<String> instructionAddressRegister) {
        this.instructionAddressRegister = instructionAddressRegister;
    }

    public void setAXRegister(Register<String> AXRegister) {
        this.AXRegister = AXRegister;
    }

    public void setBXRegister(Register<String> BXRegister) {
        this.BXRegister = BXRegister;
    }

    public void setCXRegister(Register<String> CXRegister) {
        this.CXRegister = CXRegister;
    }

    public void setDXRegister(Register<String> DXRegister) {
        this.DXRegister = DXRegister;
    }

    public void setRam(RandomAccessMemory ram) {
        this.ram = ram;
    }

    public void setOutput(OutputDevice output) {
        this.output = output;
    }

    public void setInput(InputDevice input) {
        this.input = input;
    }
}
