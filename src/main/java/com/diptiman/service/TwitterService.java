package com.diptiman.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import twitter4j.Location;
import twitter4j.ResponseList;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Component
public class TwitterService {
	

	@Value("${twitter_consumer_key}")
	private String twitter_consumer_key;
	
	@Value("${twitter_consumer_key_secret}")
	private String twitter_consumer_key_secret;
	
	@Value("${twitter_access_token}")
	private String access_token;
	
	@Value("${twitter_access_token_secret}")
	private String access_token_secret;
	
	public List<String> getTrendsForLocation(String location){
		List<String> twitterTrends = new ArrayList<String>();
		Twitter twitter = getTwitterInstance();
		try{
			Integer idTrendLocation = getTrendLocation(location);
			if(idTrendLocation == null || idTrendLocation == 0){
				System.out.println("Trend Location Not Found");
				throw new TwitterException("Trend Location Not Found");
			}
			Trends trends = twitter.getPlaceTrends(idTrendLocation);
			for(int i = 0; i < trends.getTrends().length; i++){
				twitterTrends.add(trends.getTrends()[i].getName());
			}
		}catch(TwitterException te){
			//System.err.println(te.getMessage());
			twitterTrends.add(te.getMessage());
		}
		return(twitterTrends);
	}
	
	private Twitter getTwitterInstance(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
			.setOAuthConsumerKey(twitter_consumer_key)
			.setOAuthConsumerSecret(twitter_consumer_key_secret)
			.setOAuthAccessToken(access_token)
			.setOAuthAccessTokenSecret(access_token_secret);
		
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		return(twitter);
	}
	
	private Integer getTrendLocation(String locationName){
		int idTrendLocation = 0;
		try{
			Twitter twitter = getTwitterInstance();
			
			ResponseList<Location> locations;
			locations = twitter.getAvailableTrends();
			
			for(Location location : locations){
				if(location.getName().toLowerCase().equals(locationName.toLowerCase())){
					idTrendLocation = location.getWoeid();
					break;
				}
			}
			if(idTrendLocation > 0){
				return(new Integer(idTrendLocation));
			}
			return(0);
		}catch(TwitterException te){
			System.out.println("Failed to get trends :  " + te.getMessage());
			return(0);
		}
	}
}
