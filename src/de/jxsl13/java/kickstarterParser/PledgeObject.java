package de.jxsl13.java.kickstarterParser;

import org.json.JSONObject;


public class PledgeObject {

    private int rewardId = -1;
    private double pledgeAmount = -1;
    private String reward = "";
    private String description = "";
    private int backersCount = -1;
    private int remaining =-1;
    private int limit =-1;
    private boolean allGone = true;




    PledgeObject(JSONObject object){




        if (!object.isNull("id"))rewardId = object.getInt("id");
        if (!object.isNull("minimum"))pledgeAmount = object.getDouble("minimum");
        if (!object.isNull("reward"))reward = object.getString("reward").replace("\\\\x", "\\\\\"");
        if (!object.isNull("description"))description = object.getString("description").replace("\\\\x", "\\\\\"");
        if (!object.isNull("backers_count"))backersCount = object.getInt("backers_count");
        if (!object.isNull("remaining"))remaining = object.getInt("remaining");
        if (!object.isNull("limit"))limit = object.getInt("limit");
        allGone = remaining == 0;



    }


    public String getBasicInfo(){
        return "ID: " + rewardId + " - Pledge amount: " + (int) pledgeAmount + " $ / € - Remaining: " + (remaining >= 0 ? remaining : "infinite");
    }

    public String getGeneralInfo() {
        return "ID: "+rewardId +" - Pledge amount: "+(int) pledgeAmount+" $ / € - Remaining: "+(remaining >=0?remaining:"infinite")+" - Reward: " +reward;

    }
    public int getRemaining(){
        return remaining;
    }

    public boolean equals(PledgeObject pledgeObject){
        return this.rewardId == pledgeObject.rewardId &&
                this.pledgeAmount == pledgeObject.pledgeAmount &&
                this.backersCount == pledgeObject.backersCount &&
                this.remaining == pledgeObject.remaining;
    }

    public String compareRemaining(PledgeObject pledgeObject){
        if (this.remaining == pledgeObject.remaining){
            return "Still the same amount of remaining pledges: "+remaining;
        }
        else{
            return "The remaining pledges amount has changed from "+ this.remaining + " to "+ pledgeObject.remaining;
        }

    }

    public void updatePledgeObject(JSONObject object){
        if (!object.isNull("id"))rewardId = object.getInt("id");
        if (!object.isNull("minimum"))pledgeAmount = object.getDouble("minimum");
        if (!object.isNull("reward"))reward = object.getString("reward").replace("\\\\x", "\\\\\"");
        if (!object.isNull("description"))description = object.getString("description").replace("\\\\x", "\\\\\"");
        if (!object.isNull("backers_count"))backersCount = object.getInt("backers_count");
        if (!object.isNull("remaining"))remaining = object.getInt("remaining");
        if (!object.isNull("limit"))limit = object.getInt("limit");
        allGone = remaining == 0;
    }









}
