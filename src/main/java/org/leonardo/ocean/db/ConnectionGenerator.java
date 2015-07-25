package org.leonardo.ocean.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.util.Arrays;

/**
 * Created by Leonardo - Ocean  on 2015/7/25.
 * ノㄓミ mongodb 硈絬
 */
public class ConnectionGenerator {

    protected int connectionMultiplier = 300;
    protected int connectionsPerHost = 10;
    protected int connectTimeout = 5000;
    protected int defaultPort = 27017;

    public ConnectionGenerator() {
       super();
    }



    //絬祘单计秖  * 诀程硈絬计  = pool 计秖
    private MongoClientOptions options(){
        return new MongoClientOptions.Builder()
                .threadsAllowedToBlockForConnectionMultiplier(connectionMultiplier)	//絬祘单计秖
                .connectionsPerHost(connectionsPerHost)								//诀程硈絬计
                .connectTimeout(connectTimeout)
                .build();
    }

    protected MongoClient connection(String host){
        return connection(host, defaultPort);
    }

    protected MongoClient connection(String host, int port){
        MongoClient client = null;
        try{
            ServerAddress address = new ServerAddress(host, port);
            client = new MongoClient(address, options());
        }catch(Exception error) {
            if(client != null){
                client.close();
            }
            throw error;
        }
        return client;
    }

    /**喷靡硈絬ミ*/
    protected MongoClient connection(String host, int port , String dbName, String user, String password){
        MongoClient client = null;
        try {
            MongoCredential credential = MongoCredential.createMongoCRCredential(user, dbName, password.toCharArray());
            ServerAddress address = new ServerAddress(host, port);
            client = new MongoClient(address, Arrays.asList(credential), options());

        }catch (Exception error){
            if(client != null){
                client.close();
            }
            throw error;
        }
        return client;
    }
}
