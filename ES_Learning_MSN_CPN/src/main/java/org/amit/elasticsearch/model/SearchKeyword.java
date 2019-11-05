package org.amit.elasticsearch.model;

public class SearchKeyword {

	String keyword;
	double total_Unique_Searches;
	double results_Pageviews_Search;
	double search_Exits;
	double search_Refinements;
	double time_After_Search;
	double avg_Search_Depth;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public double getTotal_Unique_Searches() {
		return total_Unique_Searches;
	}

	public void setTotal_Unique_Searches(double total_Unique_Searches) {
		this.total_Unique_Searches = total_Unique_Searches;
	}

	public double getResults_Pageviews_Search() {
		return results_Pageviews_Search;
	}

	public void setResults_Pageviews_Search(double results_Pageviews_Search) {
		this.results_Pageviews_Search = results_Pageviews_Search;
	}

	public double getSearch_Exits() {
		return search_Exits;
	}

	public void setSearch_Exits(double search_Exits) {
		this.search_Exits = search_Exits;
	}

	public double getSearch_Refinements() {
		return search_Refinements;
	}

	public void setSearch_Refinements(double search_Refinements) {
		this.search_Refinements = search_Refinements;
	}

	public double getTime_After_Search() {
		return time_After_Search;
	}

	public void setTime_After_Search(double time_After_Search) {
		this.time_After_Search = time_After_Search;
	}

	public double getAvg_Search_Depth() {
		return avg_Search_Depth;
	}

	public void setAvg_Search_Depth(double avg_Search_Depth) {
		this.avg_Search_Depth = avg_Search_Depth;
	}

}
