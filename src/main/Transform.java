package main;

import States.StartUpState;

public class Transform {
	public static String translate(String pos, double x, double y) {
		String[] values = pos.substring(2, pos.length()).split(" ");
		int[] xyz = new int[values.length];
		
		try {
			for(int i = 0; i < values.length; i++) {
				xyz[i] = Integer.parseInt(values[i].substring(0, values[i].length() - 7));
			}
		} catch(Exception e) {
			System.out.println("Not a Valid Loc");
		}
		
		xyz[0] += x;
		xyz[1] += y;
		
		return "v " + xyz[0] + " " + (xyz[1] + StartUpState.cat.getHeight())+ " 0.000000";
	}
}
