package ru.pivovarov.TelegramBotJR.command;

public enum CommandName {
    START("/start"),
    STOP("/stop"),
    HELP("/help"),

    NO("nocommand");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return this.commandName;
    }
}
