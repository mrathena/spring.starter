package com.mrathena.common.toolkit;

/**
 * @author mrathena
 */
public final class Kit {

	public static void main(String[] args) {
		String a = "";
		System.out.println(areNull(null, null));
		System.out.println(areNotNull(a, a));
	}

	private Kit() {}

	/** 都是null */
	public static boolean areNull(Object... objects) {
		for (Object object : objects) {
			if (object != null) {
				return false;
			}
		}
		return true;
	}

	/** 都不是null */
	public static boolean areNotNull(Object... objects) {
		for (Object object : objects) {
			if (object == null) {
				return false;
			}
		}
		return true;
	}

	/** 都是null或empty */
	public static boolean areNullOrEmpty(String... strings) {
		for (String string : strings) {
			if (string == null || string.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/** 都不是null和empty */
	public static boolean areNotNullOrEmpty(String... strings) {
		for (String string : strings) {
			if (string == null || string.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/** 至少有一个是null */
	public static boolean isAnyNull(Object... objects) {
		for (Object object : objects) {
			if (object == null) {
				return true;
			}
		}
		return false;
	}

	/** 至少有一个不是null */
	public static boolean isAnyNotNull(Object... objects) {
		for (Object object : objects) {
			if (object != null) {
				return true;
			}
		}
		return false;
	}

	/** 至少有一个是null或empty */
	public static boolean isAnyNullOrEmpty(String... strings) {
		for (String string : strings) {
			if (string == null || string.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/** 至少有一个既不是null也不是empty */
	public static boolean isAnyNotNullOrEmpty(String... strings) {
		for (String string : strings) {
			if (string != null && !string.isEmpty()) {
				return true;
			}
		}
		return false;
	}

}