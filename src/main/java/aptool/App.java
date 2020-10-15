package aptool;
import aptool.common.*;
public class App {
    public static void main(String[] args) {
        execute(args);
    }
    public static void execute(String[] args) {
        if (args.length == 0) {
            shortHelp("");
            System.exit(1);
        }
        switch (args[0]) {
            case "help":
                longHelp();
                break;
            case "new":
                Make(args);
                break;
            case "run":
                Run(args);
                break;
            default:
                shortHelp("Invalid subcommand (" + args[0] + ")");
                break;
        }
    }
    public static void shortHelp(String error) {
        System.err.println("Aptool © 2020 Mukul Agarwal (licensed under the GNU GPLv3) v 0.1.0");
        if (error != "") {
            System.err.println("Error: " + error);
        }
        System.err.println("");
        System.err.println("Usage: aptool (new|run|help) [ARGS...]");
        System.err.println("For a more comprehensive help message, run aptool help");
    }
    public static void longHelp() {
        System.out.println("Aptool v0.1.0 - a simple tool to manage AP Computer Science A Files and Directories.");
        System.out.println(" © 2020 Mukul Agarwal (licensed under the GNU GPLv3)");
        System.out.println("");
        System.out.println("");
        System.out.println("Subcommands:");
        System.out.println("    new  - create entity");
        System.out.println("        [identifier] - Tuple of UNIT.SUBUNIT, V, and VIDEO (e.g. 1.1V3)");
        System.out.println("");
        System.out.println("");
        System.out.println("    run   - run source code from a video");
        System.out.println("        [identifer] - Tuple of UNIT.SUBUNIT, V, and VIDEO (e.g. 1.1V3)");
        System.out.println("");
        System.out.println("");
        System.out.println("    help  - show this help message");
    }
    public static void Make(String[] args){
        if (args.length == 1) {
            shortHelp("Not enough arguments for new subcommand.");
            System.exit(1);
        }
        try {
            Identifier value = Identifier.parse(args[1]);
            Interact interactor = new Interact();
            interactor.create(value);
            
        } catch (NumberFormatException e) {
            shortHelp(e.getMessage());
            System.exit(1);
        }
    }
    public static void Run(String[] args){
        if (args.length == 1) {
            shortHelp("Not enough arguments for new subcommand.");
            System.exit(1);
        }
        try {
            Identifier value = Identifier.parse(args[1]);
            Interact interactor = new Interact();
            interactor.run(value);
            
        } catch (NumberFormatException e) {
            shortHelp(e.getMessage());
            System.exit(1);
        }
    }
}
