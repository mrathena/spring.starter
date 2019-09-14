package com.mrathena.common.toolkit;

public final class StringKit {

	public static void main(String[] args) {

		System.out.println(StringKit.取文本左边("123456789", 5));
		System.out.println(StringKit.取文本右边("123456789", 5));
		System.out.println(StringKit.取文本中间("123456789", 3, 3));
		System.out.println(StringKit.寻找文本("abcdefgABCDEFGabcdefg", "D", true));
		System.out.println(StringKit.寻找文本("abcdefgABCDEFGabcdefg", "D"));
		System.out.println(StringKit.倒找文本("abcdefgABCDEFGabcdefg", "D", true));
		System.out.println(StringKit.倒找文本("abcdefgABCDEFGabcdefg", "D"));
		System.out.println(StringKit.文本替换("abcdefgABCDEFGabcdefg", 7, 7, " "));
//		System.out.println(StringKit.子文本替换("abcdefgABCDEFGabcdefg", "ABC", " ", 0, 3, true));

	}

	private StringKit() {}

	public static final String EMPTY = "";

	public static boolean isNull(String string) {
		return string == null;
	}

	public static String nullToEmpty(String string) {
		return string == null ? EMPTY : string;
	}

	public static boolean isNotNull(String string) {
		return !isNull(string);
	}

	public static boolean isEmpty(String string) {
		return string == null || string.isEmpty();
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isTrimEmpty(String string) {
		return string == null || string.trim().isEmpty();
	}

	public static boolean isNotTrimEmpty(String string) {
		return !isTrimEmpty(string);
	}

	public static boolean equals(String string1, String string2) {
		if (string1 == null) {
			return string2 == null;
		}
		return string1.equals(string2);
	}

	public static boolean equalsIgnoreCase(String string1, String string2) {
		if (string1 == null) {
			return string2 == null;
		}
		return string1.equalsIgnoreCase(string2);
	}

	/**
	 * 把 str 里面的 oldStr 替换成 newStr
	 * */
	public static String replace(String str, String oldStr, String newStr) {
		if (str == null) {
			return "";
		}
		int i = 0;
		if ((i = str.indexOf(oldStr, i)) >= 0) {
			char[] str2 = str.toCharArray();
			char[] newStr2 = newStr.toCharArray();
			StringBuilder buf = new StringBuilder(str2.length);
			buf.append(str2, 0, i).append(newStr2);
			int oldStrLength = oldStr.length();
			i += oldStrLength;
			int j = i;
			while ((i = str.indexOf(oldStr, i)) > 0) {
				buf.append(str2, j, i - j).append(newStr2);
				i += oldStrLength;
				j = i;
			}
			return buf.append(str2, j, str2.length - j).toString();
		}
		return str;
	}

	/**
	 * 把 str 里面的 oldStr 替换成 newStr(忽略大小写)
	 * */
	public static String replaceIgnoreCase(String str, String oldStr, String newStr) {
		if (str == null) {
			return "";
		}
		String lcString = str.toLowerCase();
		String lcOldString = oldStr.toLowerCase();
		int i = 0;
		if ((i = lcString.indexOf(lcOldString, i)) >= 0) {
			char[] str2 = str.toCharArray();
			char[] newStr2 = newStr.toCharArray();
			StringBuilder buf = new StringBuilder(str2.length);
			buf.append(str2, 0, i).append(newStr2);
			int oldStrLength = oldStr.length();
			i += oldStrLength;
			int j = i;
			while ((i = lcString.indexOf(lcOldString, i)) > 0) {
				buf.append(str2, j, i - j).append(newStr2);
				i += oldStrLength;
				j = i;
			}
			return buf.append(str2, j, str2.length - j).toString();
		}
		return str;
	}

	/**[返回一个文本，该文本中指定的子文本已被替换成另一子文本，并且替换发生的次数也是被指定的。]*/
	public static String sonTextReplace(String 欲被替换的文本, String 欲被替换的子文本, String 用作替换的子文本, int 进行替换的起始位置, int 替换进行的次数, boolean 是否区分大小写) {

		return null;
	}

	/*
	
	*/
//	public static String 子文本替换(String 欲被替换的文本, String 欲被替换的子文本, String 用作替换的子文本, int 起始替换位置, int 替换次数, boolean 是否区分大小写) {
//		if (欲被替换的文本 == null) {
//			return null;
//		}
//		if (欲被替换的子文本 == null) {
//			return null;
//		}
//		if (用作替换的子文本 == null) {
//			return null;
//		}
//
//		return null;
//	}
//	public static String 子文本替换(String 欲被替换的文本, String 欲被替换的子文本, String 用作替换的子文本, int 起始替换位置, int 替换次数, boolean 是否区分大小写) {
//		return 子文本替换(欲被替换的文本, 欲被替换的子文本, 用作替换的子文本, 起始替换位置, 替换次数, 是否区分大小写);
//	}
//	public static String 子文本替换(String 欲被替换的文本, String 欲被替换的子文本, String 用作替换的子文本, int 起始替换位置, int 替换次数, boolean 是否区分大小写) {
//		return 子文本替换(欲被替换的文本, 欲被替换的子文本, 用作替换的子文本, 起始替换位置, 替换次数, 是否区分大小写);
//	}
	public static String 文本替换(String 欲被替换的文本, int 起始替换位置, int 替换长度, String 用作替换的文本) {
		if (欲被替换的文本 == null) {
			return null;
		}
		if (起始替换位置 < 0) {
			起始替换位置 = 0;
		}
		int length = 欲被替换的文本.length();
		if (起始替换位置 >= length) {
			return 欲被替换的文本;
		}
		if (替换长度 <= 0) {
			return 欲被替换的文本;
		}
		if (替换长度 > length - 起始替换位置) {
			替换长度 = length - 起始替换位置;
		}
		if (用作替换的文本 == null) {
			return null;
		}
		String prefix = 欲被替换的文本.substring(0, 起始替换位置);
		String suffix = 欲被替换的文本.substring(起始替换位置 + 替换长度);
		return prefix + 用作替换的文本 + suffix;
	}

	public static int 倒找文本(String 被搜寻的文本, String 欲寻找的文本, boolean 是否不区分大小写) {
		if (被搜寻的文本 == null) {
			return -1;
		}
		if (欲寻找的文本 == null) {
			return -1;
		}
		if (是否不区分大小写) {
			被搜寻的文本 = 被搜寻的文本.toUpperCase();
			欲寻找的文本 = 欲寻找的文本.toUpperCase();
		}
		int index = 被搜寻的文本.lastIndexOf(欲寻找的文本);
		return index;
	}

	public static int 倒找文本(String 被搜寻的文本, String 欲寻找的文本) {
		return 倒找文本(被搜寻的文本, 欲寻找的文本, false);
	}

	public static int 寻找文本(String 被搜寻的文本, String 欲寻找的文本, boolean 是否不区分大小写) {
		if (被搜寻的文本 == null) {
			return -1;
		}
		if (欲寻找的文本 == null) {
			return -1;
		}
		if (是否不区分大小写) {
			被搜寻的文本 = 被搜寻的文本.toUpperCase();
			欲寻找的文本 = 欲寻找的文本.toUpperCase();
		}
		return 被搜寻的文本.indexOf(欲寻找的文本);
	}

	public static int 寻找文本(String 被搜寻的文本, String 欲寻找的文本) {
		return 寻找文本(被搜寻的文本, 欲寻找的文本, false);
	}

	public static String 取文本中间(String 欲取其部分的文本, int 起始取出位置, int 欲取出字符的数目) {
		if (欲取其部分的文本 == null) {
			return null;
		}
		int length = 欲取其部分的文本.length();
		if (起始取出位置 < 0 || 起始取出位置 >= length) {
			return EMPTY;
		}
		if (欲取出字符的数目 <= 0) {
			return EMPTY;
		}
		int end = 起始取出位置 + 欲取出字符的数目;
		end = end > length ? length : end;
		return 欲取其部分的文本.substring(起始取出位置, end);
	}

	public static String 取文本右边(String 欲取其部分的文本, int 欲取出字符的数目) {
		if (欲取其部分的文本 == null) {
			return null;
		}
		if (欲取出字符的数目 <= 0) {
			return EMPTY;
		}
		int length = 欲取其部分的文本.length();
		int count = 欲取出字符的数目 > length ? length : 欲取出字符的数目;
		int start = length - count;
		return 欲取其部分的文本.substring(start);
	}

	public static String 取文本左边(String 欲取其部分的文本, int 欲取出字符的数目) {
		if (欲取其部分的文本 == null) {
			return null;
		}
		if (欲取出字符的数目 <= 0) {
			return EMPTY;
		}
		int length = 欲取其部分的文本.length();
		int count = 欲取出字符的数目 > length ? length : 欲取出字符的数目;
		return 欲取其部分的文本.substring(0, count);
	}

	public static int 取文本长度(String 文本数据) {
		if (文本数据 == null) {
			return 0;
		}
		return 文本数据.length();
	}

}