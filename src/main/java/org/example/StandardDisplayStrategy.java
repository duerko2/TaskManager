package org.example;

import org.example.IO.Output;
import org.example.model.Task;

public class StandardDisplayStrategy implements DisplayStrategy {
    private Output output;

    public StandardDisplayStrategy(Output output) {
        this.output = output;
    }

    @Override
    public void execute(Task task) {
        output.print(task.toString());
    }
}
