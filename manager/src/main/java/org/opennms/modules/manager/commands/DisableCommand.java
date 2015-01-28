package org.opennms.modules.manager.commands;

import com.google.common.collect.ImmutableSet;
import org.opennms.modules.manager.enablers.*;

import java.nio.file.Path;
import java.util.Set;

public class DisableCommand extends LocalCommand {

    private final static Set<Enabler.Factory> ENABLERS = ImmutableSet.<Enabler.Factory>builder()
                                                                     .add(new EventsEnabler.Factory())
                                                                     .add(new DatacollectionEnabler.Factory())
                                                                     .add(new GraphEnabler.Factory())
                                                                     .build();

    @Override
    protected void execute(final Path modulePath) throws Exception {
        Enablers.enable(modulePath);
    }
}
