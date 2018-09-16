package commands;

import java.util.Scanner;

public class CommandParameter<T> {
    private T value;

    public CommandParameter(T value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
