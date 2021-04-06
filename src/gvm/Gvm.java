package gvm;

import gvm.hardware.MotherBoard;

import java.util.HashMap;

public class Gvm {
    public static void main(String [] args){
        //BIOS System
        HashMap<String, String> program = new HashMap<>();
        program.put("0", "Hello");
        program.put("1", "World");
        program.put("2", "!!!");
        //Gvm get ready
        MotherBoard mb = new MotherBoard();
        mb.setUp();
        mb.getDisk().setProgram(program);
        mb.powerOn();
        GvmUser user = new GvmUser();
        user.setInput(mb.getInputList().get("input01"));
        new Thread(user).start();
    }
}
