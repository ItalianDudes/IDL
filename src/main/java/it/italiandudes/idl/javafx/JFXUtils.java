package it.italiandudes.idl.javafx;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class JFXUtils {

    // Service Starter
    public static void startVoidServiceTask(@NotNull final Runnable runnable) {
        new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        runnable.run();
                        return null;
                    }
                };
            }
        }.start();
    }
}
