package gvm.hardware;

public class Bus<T> {
    T data = null;

    public void clear(){
        data = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
