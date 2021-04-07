package gvm;

import gvm.hardware.InputDevice;

public class GvmUser implements Runnable {
    private InputDevice input;
    @Override
    public void run() {
        for(int i=0;i<100;i++) {
            try {
                input.input("1"); //Green light on
                Thread.sleep(2000);
                input.input("2");//Yellow light on
                Thread.sleep(2000);
                input.input("3");//Error light on
                Thread.sleep(2000);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setInput(InputDevice input) {
        this.input = input;
    }
}
