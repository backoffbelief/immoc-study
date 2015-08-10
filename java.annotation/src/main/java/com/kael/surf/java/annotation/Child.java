package com.kael.surf.java.annotation;

public class Child implements Person {

	@Override
	public int age() {
		return 0;
	}

	@Override
	@Deprecated
	public void sing() {
		
	}

	@Override
	@Desc(desc="i am desc",age=1000,author="hyl")
	public String eyeColor() {
		return "###############";
	}

}
