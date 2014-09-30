import java.net.*;

import java.io.*;



public class TwitterConnector {

	String status;

	public String getResults(String searchString) throws Exception {

    	status = "Connecting twitter.com";

    	URL urlTwitter = new URL("https://twitter.com/search?q="+URLEncoder.encode(searchString,"UTF-8"));

//    	URL urlTwitter = new URL("http://www.google.co.th");

        URLConnection yc = urlTwitter.openConnection();

        status = "Connected. Processing Results";

        yc.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(),"UTF-8"));

        String inputLine;

        String sout="";

        while ((inputLine = in.readLine()) != null) {

        	String tempOutput = getNameAndText(inputLine);

        	if(!tempOutput.equals(""))

        		sout += tempOutput+"\n";

        }

        in.close();

        status = "Done";

        return sout;

    }

	

	public String getStatus(){

		return status;

	}



    private String removeTag(String s) {

    	String out = "";

    	int    openTagCount = 0;

    	for(int i=0;i<s.length();i++) {

    		if(s.substring(i,i+1).equals("<")) openTagCount++;

    		else if(s.substring(i,i+1).equals(">")) {

    			openTagCount--;

    			continue;

    		}

    		if(openTagCount == 0) 

    			out += s.substring(i,i+1);

    	}

    	return out;

    }

    private String getNameAndText(String s) {

    	String s1 = "<strong class=\"fullname js-action-profile-name show-popup-with-id\">";

    	String s2 = "<p class=\"js-tweet-text tweet-text\">";

    	int p1=s.indexOf(s1);

    	if(p1>=0) 

    		return "Name: "+s.substring(p1+s1.length(),s.indexOf("</strong>",p1+1));

    	int p2=s.indexOf(s2);

    	if(p2>=0) {

    		String s3 = s.substring(p2+s2.length(),s.indexOf("</p>",p2+1));

    		return "Tweet: "+removeTag(s3)+"\n----------";

    	}

    	return "";

    }

    



}