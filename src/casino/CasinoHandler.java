package casino;

import java.net.*;
import java.util.Random;

import echo.*;

public class CasinoHandler extends RequestHandler {

    private static Random rng = new Random(System.currentTimeMillis());

    private int casinoTotal, playerTotal;
    private boolean gameOver;

    private static synchronized int hitMe(int max) {
        return rng.nextInt(max) + 1;
    }

    public CasinoHandler(Socket sock) {
        super(sock);
        init();
    }

    public CasinoHandler() {
        super();
        init();
    }

    private void init() {
        playerTotal = 0;
        casinoTotal = hitMe(21);
        gameOver = false;
    }

    protected String response(String request) throws Exception {
        String result = "";
        if (request.equalsIgnoreCase("new")) {
            init();
            result = "Want a card?";
        } else if (request.equalsIgnoreCase("help")) {
            result = "commands: hit, stay, new, and help";
        } else if (gameOver) {
            result = "Game over, enter new or quit.";
        } else if (request.equalsIgnoreCase("hit")) {
            playerTotal += hitMe(10);
            if (21 < playerTotal) {
                result = "total = " + playerTotal + ", you lose!";
                gameOver = true;
            } else {
                result = "total = " + playerTotal + ", again?";
            }
        } else if (request.equalsIgnoreCase("stay")) {
            result = "player total = " + playerTotal + ", casino total = " + casinoTotal;
            if (playerTotal < casinoTotal) {
                result = result + ", you lose!";
            } else if (playerTotal > casinoTotal) {
                result = result + ", you win!";
            } else {
                result = result + ", we tie!";
            }
            gameOver = true;
        } else {
            result = "Unrecognized command: " + request;
        }
        return result;
    }

}
