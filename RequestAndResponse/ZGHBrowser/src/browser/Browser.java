package browser;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Browser {

    private Scanner input  = new Scanner(System.in);

    //打开浏览器窗口
    public void openBrowser(){
        //输入url
        //  ip:port/资源名?key=value&key=value
        System.out.print("URL:");
        String url = input.nextLine();
        this.parseUrl(url);
    }

    //解析url
    private void parseUrl(String url){
        int colonIndex = url.indexOf(":");
        int slashIndex = url.indexOf("/");
        String ip = url.substring(0,colonIndex);
        int port = Integer.parseInt(url.substring(colonIndex+1,slashIndex));
        String contentAndParams = url.substring(slashIndex+1);
        this.request(ip,port,contentAndParams);
    }

    //创建socket，并把contentAndParams发送出去
    private void request(String ip,int port,String contentAndParams){
        try {
            Socket socket = new Socket(ip,port);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(contentAndParams);
            out.flush();
            //浏览器等待响应信息

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
