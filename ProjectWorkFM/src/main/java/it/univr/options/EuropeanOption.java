package it.univr.options;

import java.util.function.DoubleUnaryOperator;
import it.univr.binomialmodeltrees.BinomialModel;

public class EuropeanOption implements Options {
	
	// time discretization
	private int numberOfStepsOption;
	
	// option parameters
	private DoubleUnaryOperator transformFunction;
	private double[][] values;
	
	// model used to simulate the underlying asset
	BinomialModel model;
	
	
	
	public EuropeanOption (DoubleUnaryOperator transformFunction, int numberOfStepsOption,
			int  numberOfStepsStock, BinomialModel model) throws Exception {
		if (numberOfStepsOption > numberOfStepsStock) {
			throw new IllegalArgumentException ("The time discretization is less than the discretization of the option");
		}
		this.transformFunction=transformFunction;
		this.numberOfStepsOption=numberOfStepsOption;
		this.model=model;
	}
	
	
	// This method computes the European option values using backward induction and conditional expectations
	private void computeValues () {
		
		// matrix to store the option values at each time step
		values = new double [numberOfStepsOption][numberOfStepsOption];
		
		// computation of the payoff
		values[numberOfStepsOption-1] = model.getTransformedValuesAtGivenTimeIndex(numberOfStepsOption-1, transformFunction);
		
		/*
		 * Backward loop to compute option values from maturity to time zero.
		 * Index t represents the current time step 
		 */
		for (int t = numberOfStepsOption - 2; t >= 0; t--) {
			
			// generation of the conditional expected values, such as option values at previous time instant
			double[] expectations = model.getConditionalExpectation(values[t+1], t);
			
			// store the option values at the current time step
			values[t] = expectations;
	    }
	}
	
	
	@Override
	public double[][] getOptionValues () {
		if (values == null) {
			this.computeValues();
		}
		return  values;
	}
	
}