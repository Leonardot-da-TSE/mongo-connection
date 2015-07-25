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
public class ConnectionGenerator {

    protected int waitCount = 300;
    protected int maxConnection = 10;
    protected int timeout = 5000;
    protected int defaultPort = 27017;

    public ConnectionGenerator() {
       super();
    }

    public ConnectionGenerator(int waitCount, int maxConnection, int timeout) {
        super();
        this.waitCount = waitCount;
        this.maxConnection = maxConnection;
        this.timeout = timeout;
    }

    //�u�{���ݼƶq  * �D���̤j�s�u��  = pool �ƶq
    private MongoClientOptions options(){
        return new MongoClientOptions.Builder()
                .threadsAllowedToBlockForConnectionMultiplier(waitCount)    //�u�{���ݪ��ƶq
                .connectionsPerHost(maxConnection)						    //�D���̤j�s�u��
                .connectTimeout(timeout)
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

    /**���ҫ��s�u�إ�*/
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
