import org.apollon.api.CommandHandler;
import org.apollon.command.Command;
import org.apollon.core.Parser;
import org.apollon.option.Option;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Parser parser = new Parser();

        parser.setIgnoreCase(true);

        parser.setHandler(new CommandHandler() {
            @Override
            public boolean canExecute(Command command) {
                return true; //create your logic access condition here
            }

            @Override
            public void commandNotFound(String input) {
                System.err.printf("Command '%s' was not found ! \n", input);
            }

            @Override
            public void notRecognizedOptions(List<String> options, Command command) {
                System.err.printf("Argument(s) '%s' are not recognized in the command '%s' \n", Arrays.toString(options.toArray()),
                        command.getName());
            }

        });

        Command check = Command.create("connect", "Start connection to ip & port");

        check.setMaxArguments(1);

        check.addOption(Option.create("f", "set port to default FTP port (21)", 21));
        check.addOption(Option.create("sf", "set port to default secure FTP port (22)", 22));
        check.addOption(Option.create("http", "set port to default HTTP port (80)", 80));

        check.onResult((declaredOptions, values) -> {
            if(values.size() == 0) return;

            String host = values.get(0);

            int port = declaredOptions.size() > 0 ? declaredOptions.get(0).value(Integer.class) : Integer.parseInt(values.get(1));

            System.out.printf("Going to connect to host[%s] & port[%d] \n", host, port);

        });

        parser.addCommand(check);

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            parser.analyse(scanner.nextLine());
        }

    }


}
