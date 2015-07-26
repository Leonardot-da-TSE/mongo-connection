package org.leonardo.ocean.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.util.Arrays;

/**
 * Created by Leonardo - Ocean  on 2015/7/25.
 * 用來建立 mongodb 連線
 */
public class ConnectionGenerator {

    public ConnectionGenerator() {
        super();
    }

    /**本地端連線*/
    public MongoClient connection(){
        return new MongoClient( "localhost" , 27017 );
    }

    public MongoClient connection(String host){
        return new MongoClient( host , 27017 );
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

    /**驗證型連線建立*/
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
