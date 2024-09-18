public enum MatchType{
	DEATHMATCH("Deathmatch"),
	FLAGCAPTURE("Flag Capture"),
	TEAMBATTLE("Team Battle");
	
	private String type;
	MatchType(String type){
		this.type = type;
	}
	
	public String getType(){
		return this.type;
	}
	}
}