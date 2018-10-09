package commands;

public class BasicCommand extends Command {
    public BasicCommand(CommandNames name) {
        this.name = name;
    }

    public BasicCommand(String commandStr) {
        super(commandStr);
    }

    @Override
    public void doParse(String commandStr) {
        System.out.println("Command parsing itself.");
    }

    @Override
    protected void doExecute() {
        System.out.println("Command executing itself.");
    }
}
