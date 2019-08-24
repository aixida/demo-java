package browser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Browser {

    private Scanner input  = new Scanner(System.in);
    private Socket socket = null;

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
            socket = new Socket(ip,port);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(contentAndParams);
            out.flush();
            //浏览器等待响应信息
            this.receiveResponseContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //接收服务器回写的响应消息
    private void receiveResponseContent(){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String responseContent = reader.readLine();
            //解析响应信息，并展示出来
            this.parseResponseContentAndShow(responseContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //解析响应信息，并展示出来
    private void parseResponseContentAndShow(String responseContent){
        System.out.println(responseContent);
    }

}
