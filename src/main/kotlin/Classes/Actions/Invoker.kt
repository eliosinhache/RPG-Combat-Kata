package Classes.Actions

class Invoker {
    private var commands : MutableList<Command> = mutableListOf()

    fun addCommand(command: Command) {
        commands.add(command)
    }

    fun executeCommands() {
        commands.forEach{command -> command.execute() }
        commands.clear()
    }
}