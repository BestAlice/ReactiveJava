public class Generator{
	private final int MAX_GENERATED_INT = 150;
	private final int MIN_TEAM_NAME_LEN = 5;
	private final int MAX_TEAM_NAME_LEN = 10;
	
	public static int generateIntInRange(int min, int max){
		return new Random().nextInt(max-min+1)+min;
	}
	
	public static int generateInt(){
		return new Random().nextInt(MAX_GENERATED_INT);
	}
	
	public static String generateString(int len){
		byte[] array = new byte[len];
		new Random().nextBytes(array);
		return new String(array, Charset.forName("UTF-8"));
	}
	
	public static Date generateDate(){
		return new Date(ThreadLocalRandom.current().nextInt() *1000L);
	}
	
	public static DateTime generateDateTime(){
		int start_seconds = LocalTime.MIN.toSecondOfDay();
		int end_seconds = LocalTime.MAX.toSecondOfDay();
		LocalTime time = LocalTime.ofSecondOfDay(generateIntInRange(start_seconds, end_seconds));
		return new DateTime(generateDate()).withTime(time);
	}
	
	public static MatchType generateMatchType(){
		MatchType[] types = MatchType.values();
		return types[ generateIntInRange(0, types.lenght)];
	}
	
	public static Team generateTeam(){
		String name = generateString(generateIntInRange(MIN_TEAM_NAME_LEN, MAX_TEAM_NAME_LEN));
		List<>
	}
}