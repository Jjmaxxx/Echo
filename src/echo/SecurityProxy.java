package echo;
import java.net.Socket;
public class SecurityProxy extends ProxyHandler{
    SafeTable userTable = SafeTable.getInstance();
    private boolean loggedIn = false;
    public SecurityProxy(Socket s) { super(s); }
    public SecurityProxy() { super(); }

    protected String response(String msg) throws Exception {
        if (msg.equals("quit")) { super.shutDown(); }
        String[] cmmd = msg.split("\\s+");
        if(cmmd.length != 3 && (cmmd[0].equalsIgnoreCase("new") || cmmd[0].equalsIgnoreCase("login"))){
            return "error, 2 inputs needed. One for username and one for password";
        }
        String answer = "";
        if(cmmd[0].equalsIgnoreCase("new")) {
            if (userTable.get(cmmd[1]) == null ) {
                userTable.put(cmmd[1], cmmd[2]);
                answer = "Account created";

            } else {
                answer = "Account already Exists";
            }

        } else if (cmmd[0].equalsIgnoreCase("login")) {
            try{
                if(userTable.get(cmmd[1]).equals(cmmd[2])){
                    loggedIn = true;
                    answer = "Login Successful";
                }else{
                    super.shutDown();
                    answer = "Password Incorrect. Session Terminated";
                }
            }catch(Exception e){
                answer = "Could not Log In";
            }
        } else {
            if (loggedIn) {
                answer = super.response(msg);
            } else {
                answer = "You aren't logged in!";
            }
        }
        return answer;
    }
}
