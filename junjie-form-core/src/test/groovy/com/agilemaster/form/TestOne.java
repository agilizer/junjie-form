package com.agilemaster.form;

public class TestOne {
	public  static void main(String[] args){
		String str="CREATE TABLE IF NOT EXISTS simplex.songs (" +
				"id uuid PRIMARY KEY," +
				"title text," +
				"album text," +
				"artist text," +
				"tags set<text>," +
				"data blob" +
				");";
		System.out.println(str);
	}

}
