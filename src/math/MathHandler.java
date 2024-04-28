package math;

import echo.*;

import java.net.Socket;
import java.util.Arrays;

public class MathHandler extends RequestHandler {
    public MathHandler(Socket s) {
        super(s);
        active = true;
    }
    public MathHandler() {
        super();
        active = true;
    }
    protected String response(String msg) throws Exception {
        String[] split = msg.split(" ");
        if(split.length <= 2){
            return "error, at least 2 inputs needed";
        }
        String command = split[0];
        double[] inputs = new double[split.length-1];

        for(int i=1; i<split.length;i++){
            try{
                inputs[i-1] = Double.parseDouble(split[i]);
            }
            catch(NumberFormatException e){
                return "error inputs must be numeric";
            }
        }

        if(command.equalsIgnoreCase("add")){
            return String.valueOf(Arrays.stream(inputs).sum());
        }else if(command.equalsIgnoreCase("mul")){
            return String.valueOf(Arrays.stream(inputs).reduce(1, (x, y) -> x * y));
        }else if(command.equalsIgnoreCase("sub")){
            return String.valueOf(Arrays.stream(inputs).reduce((x, y) -> x - y).getAsDouble());
        }else if(command.equalsIgnoreCase("div")){
            double result = inputs[0];
            for(int i=1; i<inputs.length;i++){
                if(Math.abs(inputs[i])<0.00001){
                    return "bad input: cannot divide by 0";
                }
                result/= inputs[i];
            }
            return String.valueOf(result);
        }else{
            return "error: unrecognized operator";
        }

    }
}
