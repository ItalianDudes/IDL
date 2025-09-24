/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.common;

import it.italiandudes.idl.serialization.RawSerializer;

import java.awt.image.BufferedImage;
import java.io.*;

@SuppressWarnings("unused")
@Deprecated
public class FormattedImage implements Serializable {

    //Attributes
    private BufferedImage image;
    private String formatName;

    //Constructors
    public FormattedImage(BufferedImage image, String formatName){
        this.image = image;
        this.formatName = formatName;
    }

    //Methods
    public BufferedImage getImage() {
        return image;
    }
    public void setImage(BufferedImage image) {
        this.image = image;
    }
    public String getFormatName() {
        return formatName;
    }
    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }
    @Serial
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        RawSerializer.sendFormattedImage(out,this);
    }
    @Serial
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        FormattedImage temp = RawSerializer.receiveFormattedImage(in);
        this.formatName = temp.formatName;
        this.image = temp.image;
    }
    @Serial
    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("Stream data required");
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormattedImage that)) return false;
        if (!getImage().equals(that.getImage())) return false;
        return getFormatName().equals(that.getFormatName());
    }
    @Override
    public int hashCode() {
        int result = getImage().hashCode();
        result = 31 * result + getFormatName().hashCode();
        return result;
    }
    @Override
    public String toString() {
        return "Image: "+image.toString()+"\n"+
                "Format: "+formatName;
    }
}
