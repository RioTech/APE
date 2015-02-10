/**
 * 
 */
package com.cognizant.telstra.bean;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Ravi Bhojani
 *
 */
public class Facts implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1736434529771002789L;

	@JsonProperty("title")
	public String factsTitle;

	@JsonProperty("rows")
	public List<FactsList> factsList;
	
	public static class FactsList implements Serializable
	{
		private static final long serialVersionUID = 1L;
		
		public String title;
		public String description;
		
		@JsonProperty("imageHref")
		public String imageUrl;
		
	}

}
