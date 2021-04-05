package gvm.hardware.cpu;

import gvm.hardware.Bus;

public class ArithmeticLogicUnit {
    private Bus<String> dataBus;
    private Register<String> outputRegister;
    private Register<String> temporaryRegister;
    private Register<Flags> flagsRegister;
    private Operation operation;

    public ArithmeticLogicUnit(Bus<String> dataBus,
                               Register<String> outputRegister,
                               Register<String> temporaryRegister,
                               Register<Flags> flagsRegister) {
        this.dataBus = dataBus;
        this.outputRegister = outputRegister;
        this.temporaryRegister = temporaryRegister;
        this.flagsRegister = flagsRegister;
    }

    public void calculate(){
        if (operation == Operation.ADD) {
            String A = temporaryRegister.enable();
            String B = dataBus.getData();
            String out = new Integer(Integer.getInteger(A) + Integer.getInteger(B)).toString();
            outputRegister.set(out);
        }else if (operation == Operation.COMPARE) {
            String A = temporaryRegister.enable();
            String B = dataBus.getData();
            if (Integer.getInteger(A) > Integer.getInteger(B)){
                flagsRegister.set(Flags.LARGER);
            }else if (Integer.getInteger(A) == Integer.getInteger(B)) {
                flagsRegister.set(Flags.EQUAL);
            }else{
                flagsRegister.set(Flags.LESS);
            }
        }
    }

    //Set bu cu via operation bus directly to alu
    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
