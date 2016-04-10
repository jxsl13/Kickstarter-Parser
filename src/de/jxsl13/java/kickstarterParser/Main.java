package de.jxsl13.java.kickstarterParser;

import AlgoTools.IO;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class Main {
    static int exit =0;
    static String projectUrl ="";
    static int pledgeNumber =3;

    public static void main(String[] args) {


        projectUrl = IO.readString("Please enter a kickstarter procect page URL!").trim();
        ProjectPage first = new ProjectPage(projectUrl);

        ArrayList<PledgeObject> pledgeOptions = first.getPledgeObjects();
        for (int i =0; i < pledgeOptions.size();i++) {
            System.out.println(i+1 +". "+ pledgeOptions.get(i).getBasicInfo());
        }
        do {
           pledgeNumber = IO.readInt("Please enter one of the pledges from above to watch.") -1;
        }while (pledgeNumber < 0 || pledgeNumber > pledgeOptions.size());


        while (exit != -1){
          ProjectPage second = new ProjectPage(projectUrl);
            if(!first.getPledgeObjects().get(pledgeNumber).equals(second.getPledgeObjects().get(pledgeNumber))){
                first = second;
                for (PledgeObject pledgeObject:pledgeOptions) {
                    System.out.println(pledgeObject.getBasicInfo());

                }
                System.out.println("Changes occurred on your watched pledge object!!!!!!!!");
                System.out.println(first.getPledgeObjects().get(pledgeNumber));

                //// TODO: 10.04.16 maybe adding a sound notification on changed pledges alert.
                try {
                    InputStream in = new FileInputStream("de/jxsl13/java/kickstarterParser/resources/audio/airplane_ding_sound.wav");
                    AudioStream audioStream = new AudioStream(in);
                    AudioPlayer.player.start(audioStream);
                }
                catch (Exception ex){

                }
                break;
            }else {
                System.out.println("No changes on your watched pledge object.");
                System.out.println(first.getPledgeObjects().get(pledgeNumber).getBasicInfo());

            }

            first.getPledgeObjects().get(pledgeNumber).compareRemaining(second.getPledgeObjects().get(pledgeNumber));
            try {
                System.out.print("Before sleeping 30 seconds...");
                Thread.sleep(30000);
                System.out.print(" After sleeping for 30 seconds.\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //ProjectPage bottlecutter = new ProjectPage("https://www.kickstarter.com/projects/910418035/plastic-bottle-cutter/description");

    }
}
