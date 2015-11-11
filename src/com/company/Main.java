package com.company;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.lang.String;

public class Main {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String httpURL = "http://localhost/zigbee";


    public static void sendJson(String json) throws MalformedURLException, IOException {
        URL myurl = new URL(httpURL);
        HttpURLConnection con = (HttpURLConnection)myurl.openConnection();
        con.setRequestMethod("POST");

        con.setRequestProperty("Content-length", String.valueOf(json.length()));
        con.setRequestProperty("Content-Type","application/json; charset=UTF-8");
        con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
        con.setDoOutput(true);
        con.setDoInput(true);

        DataOutputStream output = new DataOutputStream(con.getOutputStream());

        output.writeBytes(json.replace('\\', ' '));

        output.close();

        DataInputStream input = new DataInputStream( con.getInputStream() );

        for( int c = input.read(); c != -1; c = input.read() )
            System.out.print( (char)c );
        input.close();

        System.out.println("Resp Code:"+con .getResponseCode());
        System.out.println("Resp Message:"+ con .getResponseMessage());
    }


    public static void main(String[] args) {
        ArrayList<com.company.Berth> berths = new ArrayList<com.company.Berth>();
        Random randomGenerator = new Random();
        for (int i = 0; i < 10; i++) {
            int randId = randomGenerator.nextInt(1000000);
            berths.add(new com.company.Berth(randId, 1));
        }
        for (int i = 0; i < berths.size(); i++) {
            try {
                String jsonString = berths.get(i).json();
                Main.sendJson(jsonString);
            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
       while(true){
           int randId = randomGenerator.nextInt(berths.size());
           berths.get(randId).ocupied = 0;
            System.out.println("-------");
           try {
               String jsonString = berths.get(randId).json();
               Main.sendJson(jsonString);
           }catch(MalformedURLException e){
               e.printStackTrace();
           }catch(IOException e){
               e.printStackTrace();
           }



       }

    }
}
