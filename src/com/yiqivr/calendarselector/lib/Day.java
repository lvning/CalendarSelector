package com.yiqivr.calendarselector.lib;

/**
 * @author lvning
 * @version create time:2014-10-29_下午3:29:43
 * @Description TODO
 */
public class Day {

	public Day(String name, DayType type, boolean isOrdered) {
		setName(name);
		setType(type);
		setOrdered(isOrdered);
	}

	public enum DayType {
		TODAY, TOMORROW, T_D_A_T, ENABLE, NOT_ENABLE
	}

	private String name;
	private DayType type;
	private boolean isOrdered;

	public boolean isOrdered() {
		return isOrdered;
	}

	public void setOrdered(boolean isOrdered) {
		this.isOrdered = isOrdered;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DayType getType() {
		return type;
	}

	public void setType(DayType type) {
		this.type = type;
	}
}
