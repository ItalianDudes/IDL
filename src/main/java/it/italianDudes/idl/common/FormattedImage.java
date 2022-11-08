package it.italianDudes.idl.common;

import it.italianDudes.idl.common.exceptions.IO.socket.*;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;

@SuppressWarnings("unused")
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
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {

        DataOutputStream outStream = new DataOutputStream(out);
        byte[] formatBytes = formatName.getBytes();
        outStream.writeInt(formatBytes.length);
        outStream.flush();
        outStream.write(formatBytes);
        outStream.flush();

        ByteArrayOutputStream imgByteStream = new ByteArrayOutputStream();
        MemoryCacheImageOutputStream imageStream = new MemoryCacheImageOutputStream(imgByteStream);
        ImageIO.write(image,formatName,imageStream);
        outStream.writeInt(imgByteStream.toByteArray().length);
        outStream.flush();
        outStream.write(imgByteStream.toByteArray());
        outStream.flush();
    }
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {

        DataInputStream inStream = new DataInputStream(in);

        int formatLength = inStream.readInt();
        byte[] formatBytes = new byte[formatLength];
        inStream.readFully(formatBytes,0,formatLength);
        formatName = new String(formatBytes);

        int imgLength = inStream.readInt();
        byte[] imgBytes = new byte[imgLength];
        inStream.readFully(imgBytes,0,imgLength);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imgBytes);
        image = ImageIO.read(byteArrayInputStream);

    }
    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("Stream data required");
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormattedImage)) return false;

        FormattedImage that = (FormattedImage) o;

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
