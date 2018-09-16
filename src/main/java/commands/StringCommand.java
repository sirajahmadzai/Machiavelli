package commands;

import java.util.StringJoiner;

public class StringCommand extends Command {
    protected String value = "";

    public StringCommand(CommandNames cmdName) {
        super(cmdName);
        addParameter(value);
    }

    public String getParameterValue() {
        return value;
    }

    public void setParameterValue(String value) {
        this.value = value;
    }

    @Override
    public void parse(String commandStr) {
        parseName(commandStr);
        this.value = scanner.next();
    }

    @Override
    public String serialize() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(name.toString());
        joiner.add(value);

        return joiner.toString();
    }
}
