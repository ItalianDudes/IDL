package it.italiandudes.idl.javafx.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public final class WarningAlert extends Alert {

    // Constructors
    public WarningAlert(@NotNull final Stage owner, String title, String header, String content) {
        super(AlertType.WARNING);
        this.setResizable(true);
        if (!owner.getIcons().isEmpty()) {
            ((Stage) getDialogPane().getScene().getWindow()).getIcons().add(owner.getIcons().getFirst());
        }
        if(title!=null) setTitle(title);
        if(header!=null) setHeaderText(header);
        if(content!=null) {
            TextArea area = new TextArea(content);
            area.setWrapText(true);
            area.setEditable(false);
            getDialogPane().setContent(area);
        }
        if (!owner.getScene().getStylesheets().isEmpty()) {
            this.getDialogPane().getScene().getStylesheets().add(owner.getScene().getStylesheets().getFirst());
        }
        showAndWait();
    }
    public WarningAlert(@NotNull final Stage owner, String header, String content){
        this(owner, null, header, content);
    }
    public WarningAlert(@NotNull final Stage owner) {
        this(owner, null, null, null);
    }
}
