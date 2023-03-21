package com.example.exercise;

import java.util.List;

public class Exercise04 {
	public static void main(String[] args) {
		var x = 3;
		var y = 5;
		var z = x + y;
		System.out.println("z=%d".formatted(z));
		x++;
		y++;
		System.out.println("z=%d".formatted(z));
		var numbers = List.of(4,8,15,16,23,42);
		var total =
		numbers.stream()
		       .filter(u -> u%2 == 0)
		       .map(v -> v*v*v)
		       .reduce(0, (partialSum,r) -> partialSum + r);
		System.out.println("total=%d".formatted(total));
	}
}
