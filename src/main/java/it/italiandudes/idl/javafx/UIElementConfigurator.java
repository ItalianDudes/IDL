package it.italiandudes.idl.javafx;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
public final class UIElementConfigurator {

    public static TextFormatter<Integer> configureNewIntegerTextFormatter() {
        NumberFormat format = NumberFormat.getIntegerInstance();
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.isContentChange()) {
                ParsePosition parsePosition = new ParsePosition(0);
                format.parse(c.getControlNewText(), parsePosition);
                if (parsePosition.getIndex() == 0 || parsePosition.getIndex() < c.getControlNewText().length()) {
                    return null;
                }
            }
            return c;
        };
        return new TextFormatter<>(new IntegerStringConverter(), 0, filter);
    }
}
