package util;

import java.sql.Connection;

public class ConnectionPool {

    private static final int BUSY_STATE = 1;
    private static final int FREE_STATE = 0;
    private static final int NULL_STATE = -1;//连接还未建立

    private static int minPoolSize = DBConfig.getIntegerValue("minPoolSize","1");
    private static int maxPoolSize = DBConfig.getIntegerValue("maxPoolSize","10");

    //设置一个容器，预先装一些连接
    private static Connection[] connectionList = new Connection[minPoolSize];

    //设置一个容器，存储每一个连接对应的状态：占用1，释放0，空置-1
    private static int[] connectionBitmap = new int[minPoolSize];//位图

    //用来存储连接的个数
    private static int total = 0;

    //块的目的是为了在连接数据池初始化的时候，让每一个连接的状态都空置
    static{
        for(int i=0;i<connectionBitmap.length;i++){
            connectionBitmap[i] = -1;
        }
    }

    //释放连接
    protected  static synchronized void giveBack(MyConnection myConnection){
        connectionBitmap[myConnection.getIndex()] = FREE_STATE;
    }

    //给用户提供一个从数据池中获取连接的方法
    public static synchronized Connection getConnection() {
        //1.获取一个状态为free的连接
        int freeIndex = getFreeIndex();
        if(freeIndex > -1){
            //给用户分配一个连接使用
            return distribute(freeIndex);
        }else if(total < maxPoolSize){
            int nullIndex = getNullIndex();
            if(nullIndex == -1){//容器中的每一个下标位置都创建了连接，要扩容
                nullIndex = grow();
            }
            return distribute(nullIndex);
        }
        return null;
    }

    //负责在连接池中找一个状态为free的
    private static int getFreeIndex(){
        for(int i = 0; i < connectionBitmap.length; i++){
            if(connectionBitmap[i] == FREE_STATE){
                return i;
            }
        }
        return -1;//没找到
    }

    //找寻连接池中null的
    private static int getNullIndex(){
        for(int i = 0; i < connectionBitmap.length; i++){
            if(connectionBitmap[i] == NULL_STATE){
                return i;
            }
        }
        return -1;//没找到
    }

    //负责分配连接
    private static Connection distribute(int index){
        if(connectionBitmap[index] == BUSY_STATE){//严谨的判断
            return null;
        }
        Connection connection = null;
        if(connectionBitmap[index] == NULL_STATE){
            connection = new MyConnection(index);
            connectionList[index] = connection;
            total++;
        }else if(connectionBitmap[index] == FREE_STATE){
            connection = connectionList[index];
        }
        connectionBitmap[index] = BUSY_STATE;
        return connection;
    }

    //扩容
    private static int grow(){
        Connection[] newConnectionList = new Connection[connectionList.length*2];
        int[] newConnectionBitmap = new int[connectionBitmap.length*2];
        System.arraycopy(connectionList,0,newConnectionList,0,connectionList.length);
        System.arraycopy(connectionBitmap,0,newConnectionBitmap,0,connectionBitmap.length);
        int firstNullIndex = connectionList.length;
        connectionBitmap = newConnectionBitmap;
        connectionList = newConnectionList;
        for(int i = firstNullIndex; i < connectionBitmap.length; i++){
            connectionBitmap[i] = -1;
        }
        return firstNullIndex;
    }

}
