package commands;

public class NumberOfPlayers extends NumericCommand{
    public NumberOfPlayers() {
        super(CommandNames.NUMBER_OF_PLAYERS);
    }

    public NumberOfPlayers(int number) {
        super(CommandNames.NUMBER_OF_PLAYERS);
        this.value = number;
    }

    public int getNumber(){
        return value;
    }
}
