package it.italiandudes.idl.javafx.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@SuppressWarnings("unused")
public final class ConfirmationAlert extends Alert {

    //Attributes
    public final boolean result;

    //Constructors
    public ConfirmationAlert(@NotNull final Stage owner, String title, String header, String content, String cssThemeExternalForm) {
        super(AlertType.CONFIRMATION);
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
        Optional<ButtonType> result = showAndWait();
        this.result = result.isPresent() && result.get().equals(ButtonType.OK);
    }
    public ConfirmationAlert(@NotNull final Stage owner, String header, String content, String cssThemeExternalForm){
        this(owner, null, header, content, cssThemeExternalForm);
    }
    public ConfirmationAlert(@NotNull final Stage owner, String cssThemeExternalForm){
        this(owner, null,null,null, cssThemeExternalForm);
    }
    public ConfirmationAlert(@NotNull final Stage owner) {
        this(owner, null, null, null, null);
    }
}