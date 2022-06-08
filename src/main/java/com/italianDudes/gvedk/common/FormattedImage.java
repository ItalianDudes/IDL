package com.italianDudes.gvedk.common;

import com.italianDudes.gvedk.common.exceptions.socketIO.*;

import java.awt.image.BufferedImage;
import java.io.Serializable;

@SuppressWarnings("unused")
public class FormattedImage implements Serializable {

    //Attributes
    private transient BufferedImage image;
    private String formatName;

    //Constructors
    public FormattedImage(BufferedImage image, String formatName){
        this.image = image;
        this.formatName = formatName;
    }

    //Methods
    public void sendImage(Peer peer) throws OutputStreamWriteException, SpecializedStreamInstancingException, ValidatingStreamException {
        Serializer.sendImage(peer,this);
    }
    public static FormattedImage receiveImage(Peer peer) throws SpecializedStreamInstancingException, CorruptedImageException, InputStreamReadException, ValidatingStreamException {
        return Serializer.receiveImage(peer);
    }
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
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof FormattedImage))
            return false;
        FormattedImage formattedImage = (FormattedImage) obj;
        return formattedImage.image.equals(this.image) && formattedImage.formatName.equals(this.formatName);
    }
    @Override
    public String toString() {
        return "Image: "+image.toString()+"\n"+
                "Format: "+formatName;
    }
}
