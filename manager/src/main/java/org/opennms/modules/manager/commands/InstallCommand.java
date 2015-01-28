package org.opennms.modules.manager.commands;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.kohsuke.args4j.Argument;
import org.kohsuke.github.GHRepository;
import org.opennms.modules.manager.CommandError;
import org.opennms.modules.manager.Main;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class InstallCommand extends Command {

    @Argument(metaVar = "module",
              required = true)
    private String module;

    @Override
    public void execute() throws Exception {
        final Path path = Main.MODULES_STORE_DIR
                .resolve(this.module);

        if (Files.exists(path)) {
            throw new CommandError("Module is already installed: " + this.module);
        }

        final GHRepository repository = this.getOrganization().getRepository(this.module);
        if (repository == null) {
            throw new CommandError("No such module: " + this.module);
        }

        final String url = repository.getUrl();

        Git.cloneRepository()
           .setURI(url)
           .setDirectory(path.toFile())
           .setProgressMonitor(new TextProgressMonitor(new PrintWriter(System.out)))
           .call();
    }
}
