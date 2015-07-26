package org.leonardo.ocean.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.util.Arrays;

/**
 * Created by Leonardo - Ocean  on 2015/7/25.
 * �Ψӫإ� mongodb �s�u
 */
public class ClientGenerator {

    static final private ClientGenerator generator = new ClientGenerator();

    static public MongoClientOptions defaultOptions(){
            return new MongoClientOptions.Builder()
                .threadsAllowedToBlockForConnectionMultiplier(300)      //�u�{���ݪ��ƶq
                .connectionsPerHost(10)                                   //�D���̤j�s�u��
                .connectTimeout(5000)
                .build();
    }

    static public MongoClientOptions options(int waitCount, int maxConnection, int timeOut) {
        return new MongoClientOptions.Builder()
                .threadsAllowedToBlockForConnectionMultiplier(waitCount)      //�u�{���ݪ��ƶq
                .connectionsPerHost(maxConnection)                                 //�D���̤j�s�u��
                .connectTimeout(timeOut)
                .build();
    }

    private ClientGenerator() {
        super();
    }

    static public ClientGenerator generator(){
        return  generator;
    }

    /**���a�ݳs�u*/
    public MongoClient connection(){
        return new MongoClient( "localhost" , 27017 );
    }

    public MongoClient connection(String host){
        return new MongoClient( host , 27017 );
    }

    public MongoClient connection(String host, MongoClientOptions options){
        return connection( host , 27017 , options);
    }

    public MongoClient connection(String host, int port, MongoClientOptions options){
        MongoClient client = null;
        try{
            ServerAddress address = new ServerAddress(host, port);
            client = new MongoClient(address, options);
        }catch(Exception error) {
            if(client != null){
                client.close();
            }
            throw error;
        }
        return client;
    }

    /**���ҫ��s�u�إ�*/
    public MongoClient connection(String host, int port , String dbName, String user, String password, MongoClientOptions options ){
        MongoClient client = null;
        try {
            MongoCredential credential = MongoCredential.createMongoCRCredential(user, dbName, password.toCharArray());
            ServerAddress address = new ServerAddress(host, port);
            client = new MongoClient(address, Arrays.asList(credential), options);

        }catch (Exception error){
            if(client != null){
                client.close();
            }
            throw error;
        }
        return client;
    }
}
