package echo;

import java.util.HashMap;

public class SafeTable extends HashMap<String,String>{

    private static final SafeTable instance = new SafeTable();
    public SafeTable() {
        super();
    }
    public static synchronized SafeTable getInstance() {
        return instance;
    }

    @Override
    public synchronized String put(String request, String response) {
        return super.put(request, response);
    }

    @Override
    public synchronized String get(Object request) {
        return super.get(request);
    }
}