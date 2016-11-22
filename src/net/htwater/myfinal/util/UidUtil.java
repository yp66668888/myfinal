package net.htwater.myfinal.util;

import java.util.Random;

import org.apache.log4j.Logger;

public class UidUtil {
	private static final int RECOMMENDCODE_LENGTH = 5;
	private static final int RANDOMCODE_LENGTH = 5;
	private static Logger logger = Logger.getLogger(UidUtil.class.getName());
	
	public static synchronized String getUid() {
		String uniqueNumber = Long.toString(System.currentTimeMillis()) + getRandomNumber();
		return uniqueNumber;
	}

	private static char[] numSequence = "0123456789".toCharArray();

	public static String getRandomNumber() {

		String temp = "";
		for (int i = 0; i < RANDOMCODE_LENGTH; i++) {
			int index = random.nextInt(numSequence.length);
			temp += String.valueOf(numSequence[index]);
		}
		return temp;
	}

	private static char[] charSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
	private static Random random = new Random();

	public static String getRecommendCode() {

		String temp = "";
		for (int i = 0; i < RECOMMENDCODE_LENGTH; i++) {
			int index = random.nextInt(charSequence.length);

			temp += String.valueOf(charSequence[index]);
		}
		return temp;
	}
	
	public static void main(String[] args) {
		logger.info(getUid());
	}

}
