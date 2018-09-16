package commands;

public class NumericCommand extends Command{
    protected Integer value;

    public NumericCommand(CommandNames cmdName) {
        super(cmdName);
        addParameter(value);
    }

    @Override
    public void parse(String commandStr) {
        parseName(commandStr);
        this.value =  scanner.nextInt();
    }
}
