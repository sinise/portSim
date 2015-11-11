/**
 * Created by sebastian on 11/11/15.
 */
package com.company;
public class Berth {
    String id;
    String ocupied;

    public Berth(int id, String ocupied){
        this.id = "" + id;
        this.ocupied = ocupied;
    }
    public String json(){
        String jsonString = "{\"_id\": \"" + this.id + "\", \"occupied\": \"" + this.ocupied +"\"}";
        System.out.println(jsonString);
        return jsonString;
    }
}
