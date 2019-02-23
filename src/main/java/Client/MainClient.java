package Client;

import Common.Message;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainClient {

    private static final int PORT = 8189;
    static byte[] byteArray;
    static String filePath = "C:\\Users\\user1\\Desktop\\clientdoc";
    static String fileName = "\\1.txt";


    public static void main(String[] args) {


        Path path = Paths.get(filePath+fileName);
        File file = new File(filePath+fileName);

        try{
            file.createNewFile();
            System.out.println("File was made");
        }catch (IOException e){
            System.out.println("File make exception");
        }


       try{
           byteArray = Files.readAllBytes(path);
           System.out.println("Yes");
       }catch (IOException e){
           System.out.println("No");
       }


        ObjectEncoderOutputStream oeos = null;
        ObjectDecoderInputStream odis = null;

    try (Socket socket = new Socket("localhost", PORT)){
        oeos = new ObjectEncoderOutputStream(socket.getOutputStream());
        Message myMsg = new Message(byteArray, fileName);
        oeos.write(myMsg.getTextMsg());
        oeos.flush();


        odis = new ObjectDecoderInputStream(socket.getInputStream());
        Message serverMsg = (Message) odis.readObject();
        System.out.println("Answer "+serverMsg.getText());
    }
     catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            oeos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }try {
           odis.close();
       } catch (IOException e) { e.printStackTrace(); }
   }

}
}
