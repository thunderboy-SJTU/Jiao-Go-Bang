package JiaoGoBang.Server.NewAI;

public class Ratio {
	private double ratio;
	private double winRatio;

	public Ratio(double ratio, double winRatio) {
		this.ratio = ratio;
		this.winRatio = winRatio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public void setWinRatio(double winRatio) {
		this.winRatio = winRatio;
	}
	

	public double getRatio() {
		return ratio;
	}

	public double getWinRatio() {
		return winRatio;
	}
}
