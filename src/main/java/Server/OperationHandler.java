package Server;

import Common.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class OperationHandler extends ChannelInboundHandlerAdapter {

    String filePath = "C:\\Users\\user1\\Desktop\\serverdoc";
    byte[] arr1;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try{
            if(msg==null){
                return;
            }

            if(msg instanceof Message){

                Path path = Paths.get(filePath+((Message) msg).getFileName());
                File file = new File(filePath+((Message) msg).getFileName());

                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                arr1 = Arrays.copyOf(((Message) msg).getTextMsg(), ((Message) msg).getTextMsg().length);

                oos.write(arr1);

                try{
                    file.createNewFile();
                    System.out.println("Yes");
                }catch (IOException e){
                    System.out.println("No");
                }

                Message answer = new Message("Hi!");
                ctx.write(answer);
            }
        }
        finally {
            ReferenceCountUtil.release(msg);

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }


}
