package it.italiandudes.idl.javafx.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@SuppressWarnings("unused")
public final class YesNoAlert extends Alert {

    //Attributes
    public final boolean result;

    //Constructors
    public YesNoAlert(@NotNull final Stage owner, String title, String header, String content) {
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
        getButtonTypes().clear();
        ButtonType yes = new ButtonType("Si", ButtonBar.ButtonData.YES);
        getButtonTypes().addAll(yes, new ButtonType("No", ButtonBar.ButtonData.NO));
        if (!owner.getScene().getStylesheets().isEmpty()) {
            this.getDialogPane().getScene().getStylesheets().add(owner.getScene().getStylesheets().getFirst());
        }
        Optional<ButtonType> result = showAndWait();
        this.result = result.isPresent() && result.get().equals(yes);
    }
    public YesNoAlert(@NotNull final Stage owner, String header, String content){
        this(owner, null, header, content);
    }
    public YesNoAlert(@NotNull final Stage owner){
        this(owner, null,null,null);
    }
}
