package it.italiandudes.idl.javafx.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public final class WarningAlert extends Alert {

    // Constructors
    public WarningAlert(@NotNull final Stage owner, String title, String header, String content, String cssThemeExternalForm) {
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
        if (cssThemeExternalForm != null) this.getDialogPane().getScene().getStylesheets().add(cssThemeExternalForm);
        showAndWait();
    }
    public WarningAlert(@NotNull final Stage owner, String header, String content, String cssThemeExternalForm){
        this(owner, null, header, content, cssThemeExternalForm);
    }
    public WarningAlert(@NotNull final Stage owner, String cssThemeExternalForm){
        this(owner, null,null,null, cssThemeExternalForm);
    }
    public WarningAlert(@NotNull final Stage owner) {
        this(owner, null, null, null, null);
    }
}
