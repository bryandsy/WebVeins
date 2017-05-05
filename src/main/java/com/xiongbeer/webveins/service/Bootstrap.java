package com.xiongbeer.webveins.service;

import com.xiongbeer.webveins.Configuration;

/**
 * 提供给用户的使用接口
 *
 * Created by shaoxiong on 17-4-26.
 */
public class Bootstrap {
    private Action action;
    private Client client;

    public Bootstrap(Action action){
        this.action = action;
        Configuration.getInstance();
    }

    public Bootstrap setAction(Action action){
        this.action = action;
        return this;
    }

    public Bootstrap runClient(){
        if(client == null) {
            client = new Client();
        }
        client.setAction(action);
        new Thread("wvLocalClient") {
            @Override
            public void run() {
                client.connect(Configuration.LOCAL_HOST, Configuration.LOCAL_PORT);
            }
        }.start();
        return this;
    }

    public Bootstrap ready(ProcessDataProto.ProcessData data){
        client.sentData(data);
        return this;
    }

    public Bootstrap stopClient(){
        client.disconnect();
        return this;
    }
}