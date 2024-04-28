package echo;

import java.net.Socket;

public class CacheProxy extends ProxyHandler{

    SafeTable cache = SafeTable.getInstance();
    public CacheProxy(Socket s) { super(s); }
    public CacheProxy() { super(); }


    protected String response(String msg) throws Exception {
        if (msg.equals("quit")) { super.shutDown(); }
        if(cache.get(msg) != null){
            System.out.println("gotten from cache");
            return cache.get(msg);
        }
        String response = super.response(msg);
        cache.put(msg,response);
        return response;
    }
}
