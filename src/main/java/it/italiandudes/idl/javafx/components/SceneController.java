package it.italiandudes.idl.javafx.components;

import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;

/**
 * @param parent Attributes
 */
@SuppressWarnings("unused")
public record SceneController(@NotNull Parent parent, @NotNull Object controller) {

    // Constructors
    @NotNull public Parent getParent() {
        return parent;
    }
    @NotNull public Object getController() {
        return controller;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SceneController(Parent parent1, Object controller1))) return false;
        if (!parent().equals(parent1)) return false;
        return controller().equals(controller1);
    }

    @Override
    public int hashCode() {
        int result = parent().hashCode();
        result = 31 * result + controller().hashCode();
        return result;
    }
}
