package com.mrathena.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 行政区划代码 省
 *
 * @author mrathena on 2018/6/11 9:39
 */
@Getter
@AllArgsConstructor
public enum Province {

	/**
	 * 行政区划代码 省
	 */
	P440000("440000", "广东省"),
	P450000("450000", "广西壮族自治区"),
	P460000("460000", "海南省"),
	P500000("500000", "重庆市"),
	P510000("510000", "四川省"),
	P520000("520000", "贵州省"),
	P530000("530000", "云南省"),
	P540000("540000", "西藏自治区"),
	P610000("610000", "陕西省"),
	P620000("620000", "甘肃省"),
	P630000("630000", "青海省"),
	P640000("640000", "宁夏回族自治区"),
	P650000("650000", "新疆维吾尔自治区"),
	P710000("710000", "台湾省"),
	P810000("810000", "香港特别行政区"),
	P910000("910000", "澳门特别行政区"),
	P999999("999999", "缺省地区"),
	P999900("999900", "全国"),
	P110000("110000", "北京市"),
	P120000("120000", "天津市"),
	P130000("130000", "河北省"),
	P140000("140000", "山西省"),
	P150000("150000", "内蒙古自治区"),
	P210000("210000", "辽宁省"),
	P220000("220000", "吉林省"),
	P230000("230000", "黑龙江省"),
	P310000("310000", "上海市"),
	P320000("320000", "江苏省"),
	P330000("330000", "浙江省"),
	P340000("340000", "安徽省"),
	P350000("350000", "福建省"),
	P360000("360000", "江西省"),
	P370000("370000", "山东省"),
	P410000("410000", "河南省"),
	P420000("420000", "湖北省"),
	P430000("430000", "湖南省"),
	P666600("666600", "银商合作"),
	P777700("777700", "通联合作"),
	P888800("888800", "商旅地区"),
	P560000("560000", "天翼地区"),
	P570000("570000", "快付通地区");

	private String code;
	private String desc;

	public static Set<String> getAllCodes() {
		return Arrays.stream(Province.values()).map(Province::getCode).collect(Collectors.toSet());
	}

    public static void main(String[] args) {
        String code = "430000";
        System.out.println(getNamesByCodes(code));
    }

	public static String getNamesByCodes(String... codes) {
		if (codes == null || codes.length == 0) {
			return null;
		}
		Set<String> names = new HashSet<>();
		Set<String> codeSet = new HashSet<>(Arrays.asList(codes));
		Arrays.stream(Province.values()).forEach(item -> {
			if (codeSet.contains(item.getCode())) {
				names.add(item.getDesc());
			}
		});
		return names.stream().collect(Collectors.joining(","));
	}

	public static String getOrderedNamesByCodes(String... codes) {
		if (codes == null || codes.length == 0) {
			return null;
		}
		List<String> names = new LinkedList<>();
		Arrays.stream(codes).forEach(item -> {
			Arrays.stream(Province.values()).forEach(province -> {
				if (province.getCode().equals(item)) {
					names.add(province.getDesc());
				}
			});
		});
		return names.stream().collect(Collectors.joining(","));
	}

}
