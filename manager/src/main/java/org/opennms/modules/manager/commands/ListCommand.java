package org.opennms.modules.manager.commands;

import org.kohsuke.github.GHRepository;

import java.util.Map;

public class ListCommand extends Command {

    @Override
    public void execute() throws Exception {
        for (final Map.Entry<String, GHRepository> entry : this.getOrganization().getRepositories().entrySet()) {
            final String key = entry.getKey();
            final GHRepository repository = entry.getValue();

            System.out.println(key);
        }
    }
}
