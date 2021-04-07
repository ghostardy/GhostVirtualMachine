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

    public void execute() throws InterruptedException{
        instructionAddressRegister.set("0");
        instructionRegister.set("0");
        AXRegister.set("0");
        BXRegister.set("0");
        CXRegister.set("0");
        DXRegister.set("0");
        while(true){
            Thread.sleep(20);
            //If interrupted by input device
            Boolean needWait = false;
            for (String deviceAddress : inputContorlBusList.keySet()){
                InputContorlCmd inputCmd = inputContorlBusList.get(deviceAddress).getData();
                if (inputCmd == InputContorlCmd.INTERRUPT) {
                    inputList.get(deviceAddress).enable();
                    //AX is used as data register
                    AXRegister.set();
                    inputContorlBusList.get(deviceAddress).setData(InputContorlCmd.CLEAR);
                    needWait = false;
                }else if (inputCmd == InputContorlCmd.INPUT) {
                    System.out.println("Waiting for inputting " + deviceAddress);
                    needWait = true;
                }
            }
            if (!needWait) {
                loadInstruction();
                executeInstruction();
            }
        }
    }

    private void loadInstruction(){
        instructionAddressRegister.enable();
        //DX use as point register
        DXRegister.set();
        memoryAddressRegister.set();
        ram.enable();
        instructionRegister.set();
    }
    private void executeInstruction(){
        String instructionString = instructionRegister.enable();
        //System.out.println("executing:"+instructionString);
        if (null == instructionString) {
            setNextAddress();
            instructionAddressRegister.set();
            return;
        }
        switch (instructionString) {
            case "$GVM_CMD_LOAD$":
                //Set next instruction address
                setNextAddress();
                loadData();
                //Ready for output
                BXRegister.set();
                //Ready for calculation
                aluTemporaryRegister.set();
                setNextAddress();
                instructionAddressRegister.set();
                instructionRegister.turnOff();
                break;
            case "$GVM_CMD_IN$":
                //Get Device address
                setNextAddress();
                loadData();
                //CX use as address register
                CXRegister.set();
                instructionRegister.set();
                String inputDeviceAddress = instructionRegister.enable();
                inputContorlBusList.get(inputDeviceAddress).setData(InputContorlCmd.INPUT);
                setNextAddress();
                instructionAddressRegister.set();
                instructionRegister.turnOff();
                break;
            case "$GVM_CMD_OUT$":
                //Get Device address
                setNextAddress();
                loadData();
                //CX use as address register
                CXRegister.set();
                for (String key : outputList.keySet()) {
                    outputList.get(key).ready();
                }
                //Output Data stored in BXRegister
                BXRegister.enable();
                for (String key : outputList.keySet()) {
                    outputList.get(key).output();
                    outputList.get(key).done();
                }
                BXRegister.set("0");
                //Execution complete, load next instruction
                setNextAddress();
                instructionAddressRegister.set();
                instructionRegister.turnOff();
                break;
            case "$GVM_CMD_COMPARE$":
                AXRegister.enable();
                alu.setOperation(Operation.COMPARE);
                alu.calculate();
                setNextAddress();
                instructionAddressRegister.set();
                instructionRegister.turnOff();
                break;
            case "$GVM_CMD_JUMPIF_EQUAL$":
                setNextAddress();
                if (Flags.EQUAL == flagsRegister.enable()) {
                    loadData();
                }else {
                    setNextAddress();
                    DXRegister.set();
                }
                instructionAddressRegister.set();
                flagsRegister.turnOff();
                instructionRegister.turnOff();
                break;
            case "$GVM_CMD_JUMPIF_GREATER$":
                setNextAddress();
                if (Flags.GREATER == flagsRegister.enable()) {
                    loadData();
                }else {
                    setNextAddress();
                    DXRegister.set();
                }
                instructionAddressRegister.set();
                flagsRegister.turnOff();
                instructionRegister.turnOff();
                break;
            case "$GVM_CMD_JUMPIF_LESS$":
                setNextAddress();
                if (Flags.LESS == flagsRegister.enable()) {
                    loadData();
                }else {
                    setNextAddress();
                    DXRegister.set();
                }
                instructionAddressRegister.set();
                flagsRegister.turnOff();
                instructionRegister.turnOff();
                break;
            case "$GVM_CMD_JUMP$":
                setNextAddress();
                loadData();
                instructionAddressRegister.set();
                //System.out.println("Jump");
                instructionRegister.turnOff();
                break;
            case "$GVM_CMD_SET$":
                setNextAddress();
                loadData();
                instructionRegister.set();
                String registerName = instructionRegister.enable();
                if ("AX" == registerName){
                    setNextAddress();
                    loadData();
                    AXRegister.set();
                }else if ("BX" == registerName) {
                    setNextAddress();
                    loadData();
                    BXRegister.set();
                }
                setNextAddress();
                instructionAddressRegister.set();
                instructionRegister.turnOff();
                break;

            default:
                System.out.println("Unknown instruction [" + instructionString + "]");
                //Next address handled as instruction
                setNextAddress();
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
        //ram.trace();
    }
}
