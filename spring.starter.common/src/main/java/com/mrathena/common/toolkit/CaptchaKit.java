package com.mrathena.common.toolkit;

/**
 * 验证码
 *
 * @author mrathena
 */
public final class CaptchaKit {

	public static void main(String[] args) {
		System.out.println(CaptchaKit.generateCaptcha(6));
		System.out.println(generateCaptcha(CaptchaKit.CaptchaType.NUMBER, "***-****"));
		System.out.println(generateCaptcha(CaptchaKit.CaptchaType.LETTER, "***-****"));
		System.out.println(generateCaptcha(CaptchaKit.CaptchaType.MIXTURE, "***-****"));
	}

	private CaptchaKit() {}

	/**
	 * 验证码类型
	 */
	public enum CaptchaType {
		/**
		 * 数字,字母,数字字母混合
		 */
		NUMBER, LETTER, MIXTURE
	}

	private static final String PLACEHOLDER = "*";
	private static final String STRING0 = "0";

	/**
	 * 生成验证码的基础
	 */
	private static final String NUMBER = "0123456789";
	private static final String LETTER = "abcdefghijklmnopqrstuvwxyz";
	private static final String MIXTURE = "abcdefghijklmnopqrstuvwxyz0123456789";

	/**
	 * 生成验证码
	 *
	 * @param type    .
	 * @param pattern 用'*'来占位替换,其他字符原样输出
	 * @return .
	 */
	public static String generateCaptcha(CaptchaType type, String pattern) {
		if (pattern == null || pattern.isEmpty()) {
			return null;
		}
		String base;
		switch (type) {
			case NUMBER:
				base = NUMBER;
				break;
			case LETTER:
				base = LETTER;
				break;
			case MIXTURE:
				base = MIXTURE;
				break;
			default:
				return null;
		}
		char[] charArray = pattern.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < charArray.length; i++) {
			String charStr = String.valueOf(charArray[i]);
			if (PLACEHOLDER.equals(charStr)) {
				String randomStr = String.valueOf(base.charAt((int) (Math.random() * base.length())));
				while (i == 0 && STRING0.equals(randomStr)) {
					randomStr = String.valueOf(base.charAt((int) (Math.random() * base.length())));
				}
				sb.append(randomStr);
			} else {
				sb.append(charStr);
			}
		}
		return sb.toString();
	}

	/**
	 * 生成短信验证码
	 */
	public static String generateCaptcha(int length) {
		return String.valueOf((long) ((Math.random() * 9 + 1) * Math.pow(10, length - 1)));
	}

}