package org.opennms.modules.manager;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.spi.SubCommand;
import org.kohsuke.args4j.spi.SubCommandHandler;
import org.kohsuke.args4j.spi.SubCommands;
import org.opennms.modules.manager.commands.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public final static String GITHUB_ORGANISATION_NAME = System.getProperty("opennms.modules.orga", "opennms-config-modules");

    public final static Path MODULES_STORE_DIR = Paths.get(System.getProperty("opennms.modules.store", "/etc/opennms/modules"));
    public final static Path MODULES_TARGET_DIR = Paths.get(System.getProperty("opennms.modules.target", "/etc/opennms"));

    @Argument(handler = SubCommandHandler.class,
              required = true,
              metaVar = "command")
    @SubCommands({@SubCommand(name = "list", impl = ListCommand.class),
                  @SubCommand(name = "install", impl = InstallCommand.class),
                  @SubCommand(name = "uninstall", impl = UninstallCommand.class),
                  @SubCommand(name = "update", impl = UpdateCommand.class),
                  @SubCommand(name = "enable", impl = EnableCommand.class),
                  @SubCommand(name = "disable", impl = DisableCommand.class),
                 })
    private Command command;

    public static void main(final String... args) {
        final Main main = new Main();

        final CmdLineParser parser = new CmdLineParser(main);

        try {
            parser.parseArgument(args);

        } catch (final CmdLineException ex) {
            System.err.println("Error: " + ex.getMessage());
            System.exit(127);
        }

        try {
            main.command.execute();

        } catch (final CommandError ex) {
            System.err.println("Error: " + ex.getMessage());
            System.exit(1);

        } catch (final Exception ex) {
            ex.printStackTrace(System.err);
            System.exit(126);
        }
    }
}
