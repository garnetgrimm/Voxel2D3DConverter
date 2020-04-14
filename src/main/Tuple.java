package main;

public class Tuple implements Comparable<Tuple> {
	
	public final Integer x;
	public final Integer y;
	
	public int sortType = 0;
	
	public Tuple(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getSortType() {
		return sortType;
	}
	
	public void setSortType(int type) {
		sortType = type;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	@Override
	public int compareTo(Tuple o) {
		int p = this.x.compareTo(o.x);
		if(p == 0) p = this.y.compareTo(o.y);
		return p;
	}
}
