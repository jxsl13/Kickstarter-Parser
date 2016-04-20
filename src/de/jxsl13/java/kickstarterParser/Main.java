package de.jxsl13.java.kickstarterParser;

import AlgoTools.IO;

import java.util.ArrayList;

public class Main {
    static boolean exit = false;
    static String projectUrl ="";
    static int pledgeNumber = -1;
    static ArrayList<Integer> pledgeNumbers = new ArrayList<>();
    static int sleepInterval = 30000;


    public static void main(String[] args) {

        playSound();
        System.out.println("Just now you heard the notification sound that will be heard again on any changes that occur on your watched pledges. ");

        sleepInterval = IO.readInt("Please enter an interval of seconds(integer value) which is waited before checking for changes: ") * 1000;
        if (sleepInterval < 5000) {
            sleepInterval = 5000;
            System.out.println("Sleep interval was set to 5 seconds, due to flooding restrictions. I recommend to use 30 seconds.");
        }
        projectUrl = IO.readString("Please enter a kickstarter procect page URL: ").trim();
        ProjectPage first = new ProjectPage(projectUrl);

        ArrayList<PledgeObject> pledgeOptions = first.getPledgeObjects();

        for (int i =0; i < pledgeOptions.size();i++) {
            System.out.println(i + ". " + pledgeOptions.get(i).getBasicInfo());
        }

        do {
            String[] words = IO.readWords("Please enter the pledges you want to watch, separated by a whitespace. ");
            String currentWord;
            for (int i = 0; i < words.length; i++) {
                currentWord = words[i];
                if (pledgeNumbers.contains(Integer.parseInt(currentWord)) || Integer.parseInt(currentWord) < 0 || Integer.parseInt(currentWord) >= pledgeOptions.size()) {
                    continue;
                } else {
                    pledgeNumbers.add(Integer.parseInt(currentWord));
                }
            }

        } while (pledgeNumbers.size() <= 0);

        boolean changes = false;
        while (!exit) {
            ProjectPage second = new ProjectPage(projectUrl);
            for (int i = 0; i < pledgeNumbers.size(); i++) {

                if (!first.getPledgeObjects().get(pledgeNumbers.get(i)).equals(second.getPledgeObjects().get(pledgeNumbers.get(i)))) {
                    changes = true;
                    System.out.println("Changes occurred on your watched pledge object!!!!!!!! ");
                    System.out.println(second.getPledgeObjects().get(pledgeNumbers.get(i)).getBasicInfo());
                    first.getPledgeObjects().get(pledgeNumbers.get(i)).compareRemaining(second.getPledgeObjects().get(pledgeNumbers.get(i)));

                    playSound();

                } else {
                    System.out.println("No changes on your watched pledge object: ");
                    System.out.println(first.getPledgeObjects().get(pledgeNumbers.get(i)).getBasicInfo());

                }


            }
            if (changes) {
                first = second;
                changes = false;
                exit = !exit;
            }

            if (!exit) {
                try {
                    System.out.print("Before sleeping for " + sleepInterval / 1000 + " seconds...");
                    Thread.sleep(sleepInterval);
                    System.out.print(" After sleeping for " + sleepInterval / 1000 + " seconds.\n");
                    for (int i = 0; i < pledgeNumbers.size() * 2 + 3; i++) {
                        System.out.println();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    static void playSound() {
        new AePlayWave("src/de/jxsl13/java/kickstarterParser/resources/audio/ding_sound.wav").start();

    }


}
