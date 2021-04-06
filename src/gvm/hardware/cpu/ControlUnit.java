package gvm.hardware.cpu;

import gvm.hardware.*;

import java.util.HashMap;

public class ControlUnit {
    private HashMap<String, Bus<InputContorlCmd>> inputContorlBusList;

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
    private HashMap<String, OutputDevice> outputList;
    private HashMap<String, InputDevice> inputList;
    private Disk disk;

    public ControlUnit(){
    }

    public void execute(){
        instructionAddressRegister.set("0");
        instructionRegister.set("0");
        while(true){
            //If interrupted by input device
            for (String deviceAddress : inputContorlBusList.keySet()){
                if (inputContorlBusList.get(deviceAddress).getData() == InputContorlCmd.INTERRUPT) {
                    inputList.get(deviceAddress).enable();
                    //AX is used as data register
                    AXRegister.set();
                    inputContorlBusList.get(deviceAddress).setData(InputContorlCmd.CLEAR);
                }
            }

            try {
                loadInstruction();
                executeInstruction();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadInstruction() throws InterruptedException{
        instructionAddressRegister.enable();
        //DX use as point register
        DXRegister.set();
        memoryAddressRegister.set();
        ram.enable();
        instructionRegister.set();
        Thread.sleep(100);
    }
    private void executeInstruction(){
        Instruction instruction = Instruction.valueOf(instructionRegister.enable());
        switch (instruction) {
            case $GVM_CMD_LOAD$:
                //Set next instruction address
                setNextAddress();
                loadData();
                aluTemporaryRegister.set();
                setNextAddress();
                instructionAddressRegister.set();
                instructionRegister.turnOff();
                break;
            case $GVM_CMD_OUT$:
                setNextAddress();
                loadData();
                //CX use as address register
                CXRegister.set();
                for (String key : outputList.keySet()){
                    outputList.get(key).ready();
                }
                setNextAddress();
                loadData();
                for (String key : outputList.keySet()) {
                    outputList.get(key).output();
                    outputList.get(key).done();
                }
                setNextAddress();
                instructionAddressRegister.set();
                instructionRegister.turnOff();
                break;
            case $GVM_CMD_COMPARE$:
                setNextAddress();
                loadData();
                alu.setOperation(Operation.COMPARE);
                alu.calculate();
                setNextAddress();
                instructionAddressRegister.set();
                instructionRegister.turnOff();
                break;
            case $GVM_CMD_JUMPIF_EQUAL$:
                setNextAddress();
                if (Flags.EQUAL == flagsRegister.enable()){
                    loadData();
                    DXRegister.set();
                }else {
                    setNextAddress();
                    loadData();
                }
                instructionAddressRegister.set();
                instructionRegister.turnOff();
        }
    }
    private void loadData(){
        DXRegister.enable();
        memoryAddressRegister.set();
        ram.enable();
    }

    private void setNextAddress(){
        DXRegister.enable();
        alu.setOperation(Operation.INC);
        alu.calculate();
        aluOutputRegister.enable();
        DXRegister.set();
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

    public void setOutput(HashMap<String, OutputDevice> outputList) {
        this.outputList = outputList;
    }

    public void setInput(HashMap<String, InputDevice> inputList) {
        this.inputList = inputList;
    }

    public void setInputContorlBusList(HashMap<String, Bus<InputContorlCmd>> inputContorlBusList) {
        this.inputContorlBusList = inputContorlBusList;
    }

    public void setDisk(Disk disk) {
        this.disk = disk;
    }

    public void load(){
        while (disk.hasNext()) {
            disk.setNextAddress();
            memoryAddressRegister.set();
            disk.setNextData();
            ram.set();
        }
        ram.trace();
    }
}
