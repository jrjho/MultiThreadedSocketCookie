package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientHandler implements Runnable {
    // private final Socket clientSocket;
    final Socket s;

    String dirPath = "CookieFolder/";
    String fileName = "cookie.txt";

    List<String> cookieItems = null;

    // Constructor
    public ClientHandler(Socket socket) {
        this.s = socket;
    }

    public void run() {

        // try {
        // cookieItems = new ArrayList<String>();
        // File file = new File(dirPath + fileName);

        // BufferedReader br = new BufferedReader(new FileReader(file));

        // String readString;

        // while ((readString = br.readLine()) != null) {
        // cookieItems.add(readString);
        // }
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        PrintWriter out = null;
        BufferedReader in = null;
        // cookieItems = new ArrayList<String>();
        // File file = new File(dirPath + fileName);
        // BufferedReader br = new BufferedReader(new FileReader(file));
        // String readString;
        // try{
        //     while((readString = br.readLine()) != null){
        //         cookieItems.add(readString);
        //     }
        // }catch(IOException e){
        //     e.printStackTrace();
        // }

        try {

            // get the outputstream of client
            out = new PrintWriter(
                    s.getOutputStream(), true);

            // get the inputstream of client
            in = new BufferedReader(
                    new InputStreamReader(
                            s.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {

                // writing the received message from
                // client
                if ("cookie".equalsIgnoreCase(line)) {
                    System.out.println("Client requested for cookie!");
                    String cookieValue = returnCookie();
                    out.println(cookieValue);

                }
                else{
                    System.out.println("Input <cookie> to get cookie");
                    out.println("Input <cookie> to get cookie");

                }
                // System.out.printf(
                //         " Sent from the client: %s\n",
                //         line);
                // out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                    s.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String returnCookie() throws IOException {
        Random rand = new Random();
        cookieItems = new ArrayList<String>();
        File file = new File(dirPath + fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String readString;
        try{
            while((readString = br.readLine()) != null){
                cookieItems.add(readString);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        if (cookieItems != null)
            return cookieItems.get(rand.nextInt(cookieItems.size()));
        else
            return "No cookie found";

    }

    public void showCookies() {
        if (cookieItems != null) {
            cookieItems.forEach(ci -> System.out.println(ci));
        }
    }

}
