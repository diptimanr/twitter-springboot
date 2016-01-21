package com.diptiman.controller;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.diptiman.exception.ResourceNotFoundException;
import com.diptiman.service.TwitterService;

@RestController
@RequestMapping("/twitterspringboot")
public class TweetTrendController {
	
	@Inject
	private TwitterService twitterService;
	
	@RequestMapping(value="/location/{location}", method=RequestMethod.GET)
	public ResponseEntity<?> getTrends(@PathVariable String location){
		if((null == location) || "".equals(location)){
			System.out.println("--------");
			throw new ResourceNotFoundException("Location not provided"); 
		}
		Iterable<String> trends = twitterService.getTrendsForLocation(location);
		if(twitterService.getTrendsForLocation(location).isEmpty()){
			return(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		}
		return(new ResponseEntity<>(trends, HttpStatus.OK));
	}
}
