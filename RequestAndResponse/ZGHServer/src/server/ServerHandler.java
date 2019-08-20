package server;

import controller.IndexController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

public class ServerHandler extends Thread{

    private String content;
    private HashMap<String,String> paramsMap;

    private Socket socket;
    public ServerHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        //1.读取消息
        //2.解析
        //3.找人做事
        //4.响应回去
        this.receiveRequest();
    }

    //读取消息
    private void receiveRequest(){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String contentAndParams = reader.readLine();
            this.parseContentAndParams(contentAndParams);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //解析
    private void parseContentAndParams(String contentAndParams){
        int questionMarkIndex = contentAndParams.indexOf("?");
        if(questionMarkIndex != -1){//携带了参数
            content = contentAndParams.substring(0,questionMarkIndex);
            paramsMap = new HashMap<String,String>();
            String params = contentAndParams.substring(questionMarkIndex+1);
            String[] keyAndValues = params.split("&");
            for(String keyAndValue:keyAndValues){
                String[] KV = keyAndValue.split("=");
                paramsMap.put(KV[0],KV[1]);
            }
        }else{//没有携带参数
            content = contentAndParams;
        }
        //-------------自此，将请求发送过来的字符串解析完毕了----------------------
        //资源--content
        //参数--paramsMap
        this.findController();
    }

    //找--控制层(controller或者action或者servlet)--干活
    private void findController(){
        if("index".equals(content)){
            IndexController ic = new IndexController();
            ic.test();
        }else{
            System.out.println("不相等");
        }
    }

    //响应回去
    private void responseToBrowser(){

    }


}
