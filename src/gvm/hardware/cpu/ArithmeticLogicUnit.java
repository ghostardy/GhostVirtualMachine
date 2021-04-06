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
        String A;
        String B;
        switch (operation) {
            case ADD:
                A = temporaryRegister.enable();
                B = dataBus.getData();
                String out = new Integer(Integer.getInteger(A) + Integer.getInteger(B)).toString();
                outputRegister.set(out);
                break;
            case COMPARE:
                A = temporaryRegister.enable();
                B = dataBus.getData();
                if (Integer.getInteger(A) > Integer.getInteger(B)){
                    flagsRegister.set(Flags.LARGER);
                }else if (Integer.getInteger(A) == Integer.getInteger(B)) {
                    flagsRegister.set(Flags.EQUAL);
                }else{
                    flagsRegister.set(Flags.LESS);
                }
                break;
            case INC:
                A = dataBus.getData();
                outputRegister.set(new Integer(Integer.getInteger(A).intValue()+1).toString());
        }
    }

    //Set bu cu via operation bus directly to alu
    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
