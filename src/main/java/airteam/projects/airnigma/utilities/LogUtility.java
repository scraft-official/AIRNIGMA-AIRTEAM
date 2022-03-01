package airteam.projects.airnigma.utilities;

public class LogUtility {
	public static void logInfo(Object... messages) {
		for (Object msg : messages) {
			System.out.println("INFO : " + String.valueOf(msg));
		}
	}

	public static void logError(Object... messages) {
		for (Object msg : messages) {
			System.out.println("ERROR : " + String.valueOf(msg));
		}
	}

}
