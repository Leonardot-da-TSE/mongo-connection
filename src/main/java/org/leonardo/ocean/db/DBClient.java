package org.leonardo.ocean.db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Created by hua on 2015/7/26.
 */
public class DBClient {

    private MongoClient client;

    public DBClient(MongoClient client){
        this.client = client;
    }

    public MongoDatabase use(String dbName){
        synchronized (this) {
            MongoDatabase db = null;
            if (client != null){
                db = client.getDatabase(dbName);
            }
            return db;
        }
    }

    public void close(){
        if(client != null)
            this.client.close();
    }
}
