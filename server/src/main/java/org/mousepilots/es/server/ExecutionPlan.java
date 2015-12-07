package org.mousepilots.es.server;

import java.util.ArrayList;
import java.util.List;
import org.mousepilots.es.change.Change;

public class ExecutionPlan {

    private final List<Change> changes;

    ExecutionPlan(List<Change> changes) {
        this.changes = new ArrayList<>(changes);
    }

    public void execute(ServerChangeVisitor executor) {
        for (Change change : changes) {
            change.accept(executor);
        }
    }
}