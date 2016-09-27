package de.jxsl13.java.kickstarterParser;

import org.apache.commons.validator.routines.UrlValidator;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by john on 10.04.16.
 */
public class ProjectPage {
    private String url;
    private String projectName;
    private int backerCountTotal;
    private long pledgeAmountTotal;
    private String JSONinfoString;
    private ArrayList<PledgeObject> pledgeObjectArrayList = new ArrayList<PledgeObject>();


    ProjectPage(String URL) {
        String[] schemes = {"http", "https"};
        UrlValidator validator = new UrlValidator(schemes);
        Document website = null;
        Elements allElements;
        String scriptString = "";
        String unescapedJSONstring;
        String escapedJSONstring;
        String escapedGeneralInfo;
        String escapedRewards;
        String[] rewardStrings;

        if (validator.isValid(URL)) {
            if (URL.contains("kickstarter.com/projects/")) {

                try {
                    website = Jsoup.connect(URL).maxBodySize(0).get();
                    System.out.println("Website retrieved!...");

                    allElements = new Elements(website.getElementsByTag("script"));
                    //System.out.println("All " + allElements.size() + " Elements retrieved...");

                    for (int i = 0; i < allElements.size(); i++) {
                        if (allElements.get(i).toString().contains("window.current_project")) {
                            scriptString = allElements.get(i).toString();
                            break;
                        }
                    }
                    int beginIndex = scriptString.lastIndexOf("window.current_project = ") + 19;
                    int endIndex = scriptString.indexOf("window.current_location = ") - 14;
                    unescapedJSONstring = scriptString.substring(beginIndex, endIndex);
                    //System.out.println(unescapedJSONstring);
                    //System.out.println("Test 1");
                    escapedJSONstring = Parser.unescapeEntities(unescapedJSONstring, false);
                    int rewardsBeginIndex = escapedJSONstring.indexOf("\"rewards\":[") + 10;
                    escapedGeneralInfo = escapedJSONstring.substring(0, rewardsBeginIndex);

                    escapedRewards = escapedJSONstring.substring(rewardsBeginIndex, escapedJSONstring.length());
                    //escapedRewards = escapedRewards.replace("\\\\n", "");
                    //escapedRewards = escapedRewards.replace("\\\\r", "");
                    //escapedRewards = escapedRewards.replace("\\\\","");
                    escapedRewards = escapedRewards.replace("\\\\\"", "\\\\x");
                    rewardStrings = escapedRewards.split("(?=,\\{)");



                    for (int i = 0; i < rewardStrings.length; i++) {
                        rewardStrings[i] = rewardStrings[i].substring(1);
                       // System.out.println(rewardStrings[i]);
                        PledgeObject pledge = new PledgeObject((new JSONObject(rewardStrings[i])));
                        pledgeObjectArrayList.add(pledge);
                    }


                } catch (Exception ex) {
                    // Exception no connectivity or could not get website data.
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                    System.out.println("Cannot retrieve website data, please try again or check your internet connection.");
                    System.exit(3);
                }


            } else {
                // Exception wrong website
                System.out.println("Invalid URL, please enter a kickstarter.com project page URL.");
                System.exit(2);

            }

        } else {
            //Exception invalid URL
            System.out.print("Invalid URL, please enter a valid URL");
            System.exit(1);

        }
    }

    public ArrayList<PledgeObject> getPledgeObjects(){
        return pledgeObjectArrayList;
    }
}
