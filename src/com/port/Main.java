package com.port;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.lang.String;

public class Main {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String httpURL = "http://port.lapela.dk/updateBerth";


    public static void sendJson(String json) throws MalformedURLException, IOException {
        URL myurl = new URL(httpURL);
        HttpURLConnection con = (HttpURLConnection)myurl.openConnection();
        con.setRequestMethod("PUT");

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

    public static void updateBerthDB(String jsonString){
        try {
            Main.sendJson(jsonString);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ArrayList<com.port.Berth> berths = new ArrayList<com.port.Berth>();
        Random randomGenerator = new Random();
        for (int i = 0; i < 10; i++) {
            int randId = randomGenerator.nextInt(1000000);
            berths.add(new com.port.Berth(randId, 1));
        }
        for (int i = 0; i < berths.size(); i++) {
            String jsonString = berths.get(i).json();
            updateBerthDB(jsonString);
        }
        int j = 0;
       while(true){
           j++;
           try{
               Thread.sleep(2000);
           } catch (Exception e){}

           int randId = randomGenerator.nextInt(berths.size());
           int randOcupied = randomGenerator.nextInt(2);
           berths.get(randId).ocupied = randOcupied;
           System.out.println("-------");
           String jsonString = berths.get(randId).json();
           updateBerthDB(jsonString);
       }
    }
}
