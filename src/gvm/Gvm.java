package gvm;

import gvm.hardware.MotherBoard;

import java.util.HashMap;

public class Gvm {
    public HashMap<String, String> getBootloader1(){
        HashMap<String, String> program = new HashMap<>();
        program.put("0", "Hello");
        program.put("1", "World");
        program.put("2", "!!!");
        program.put("3", "$GVM_CMD_OUT$");
        program.put("4", "output01");
        program.put("5", "$GVM_CMD_LOAD$");
        program.put("6", "99887766");
        program.put("7", "$GVM_CMD_OUT$");
        program.put("8", "output01");
        program.put("20", "$GVM_CMD_IN$");
        program.put("21", "input01");
        program.put("22", "$GVM_CMD_OUT$");
        program.put("23", "output01");
        program.put("38", "$GVM_CMD_JUMP$");
        program.put("39", "0");
        return program;
    }
    public HashMap<String, String> getBootloader(){
        HashMap<String, String> program = new HashMap<>();
        program.put("0", "$GVM_CMD_LOAD$");
        program.put("1", "1");
        program.put("2", "$GVM_CMD_COMPARE$");
        program.put("3", "$GVM_CMD_JUMPIF_EQUAL$");
        program.put("4", "20");
        program.put("5", "$GVM_CMD_LOAD$");
        program.put("6", "2");
        program.put("7", "$GVM_CMD_COMPARE$");
        program.put("8", "$GVM_CMD_JUMPIF_EQUAL$");
        program.put("9", "30");
        program.put("10", "$GVM_CMD_COMPARE$");
        program.put("11", "$GVM_CMD_JUMPIF_LESS$");
        program.put("12", "40");
        program.put("13", "$GVM_CMD_JUMP$");
        program.put("14", "0");
        program.put("20", "$GVM_CMD_SET$");
        program.put("21", "BX");
        program.put("22", "Green");
        program.put("23", "$GVM_CMD_OUT$");
        program.put("24", "output01");
        program.put("25", "$GVM_CMD_SET$");
        program.put("26", "AX");
        program.put("27", "0");
        program.put("28", "$GVM_CMD_JUMP$");
        program.put("29", "0");
        program.put("30", "$GVM_CMD_SET$");
        program.put("31", "BX");
        program.put("32", "Yellow");
        program.put("33", "$GVM_CMD_OUT$");
        program.put("34", "output01");
        program.put("35", "$GVM_CMD_SET$");
        program.put("36", "AX");
        program.put("37", "0");
        program.put("38", "$GVM_CMD_JUMP$");
        program.put("39", "0");
        program.put("40", "$GVM_CMD_SET$");
        program.put("41", "BX");
        program.put("42", "Error");
        program.put("43", "$GVM_CMD_OUT$");
        program.put("44", "output01");
        program.put("45", "$GVM_CMD_SET$");
        program.put("46", "AX");
        program.put("47", "0");
        program.put("48", "$GVM_CMD_JUMP$");
        program.put("49", "0");
        return program;
    }
    public static void main(String [] args){
        //Bootloader
        Gvm gvm = new Gvm();
        HashMap<String, String> program = gvm.getBootloader();
        //Gvm get ready
        MotherBoard mb = new MotherBoard();
        mb.setUp();
        mb.getDisk().setProgram(program);
        //Simulate computer user
        GvmUser user = new GvmUser();
        user.setInput(mb.getInputList().get("input01"));
        new Thread(user).start();

        mb.powerOn();
    }
}
