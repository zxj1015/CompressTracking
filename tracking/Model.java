package tracking;

public class Model {
	public Model(int featureDimension, double learnRate) {
		this.featureDimension = featureDimension;
		this.learnRate = learnRate;
		mean = new double[featureDimension];
		variance = new double[featureDimension];
	}
	
	public void updateModel(double [] newMean, double [] newVariance) {
		for (int i = 0; i < featureDimension; ++i) {
			variance[i] = Math.sqrt(learnRate * variance[i] * variance[i] + (1 - learnRate) *
					      newVariance[i] * newVariance[i] + learnRate * (1 - learnRate) * 
					      (mean[i] - newMean[i]) * (mean[i] - newMean[i]));
			mean[i] = (1 - learnRate) * mean[i] + learnRate * newMean[i];
		}
	}
	
	public double calculateScore(double[] features) {
		double score = 0;
		for (int i = 0; i < featureDimension; ++i) {
			score += -Math.log(Math.sqrt(2 * Math.PI) * variance[i]) - 
					 (features[i] - mean[i]) * (features[i] - mean[i]) / 2 / variance[i] / variance[i];
		}
		return score;
	}
	
	public void printModel() {
		System.out.println();
		for (int i = 0; i < mean.length; ++i) {
			System.out.print(mean[i] + " ");
		}
		System.out.println();
	}
	
	private int featureDimension;
	private double learnRate;
	private double [] mean;
	private double [] variance;
}
