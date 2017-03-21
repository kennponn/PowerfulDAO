package com.svgteam.util;

import java.util.List;

public class ListUtil {
	public static void iterater(List<?> list){
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

}
