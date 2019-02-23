package Common;

import java.io.Serializable;

public class Message implements Serializable {

    private String text;
     public byte[] textMsg;
    private String fileName;

    public Message(String text, String fileName){
        this.fileName = fileName;
        this.text = text;
    }

    public Message(byte[] text, String fileName) {
        this.textMsg = text;
        this.fileName = fileName;
    }

    public Message(String text){
        this.text = text;
    }


    public String getText(){return text;}
    public byte[] getTextMsg(){   return textMsg; }

    public String getFileName(){return fileName;}

}
