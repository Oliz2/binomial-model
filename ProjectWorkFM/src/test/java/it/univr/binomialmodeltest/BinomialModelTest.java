package it.univr.binomialmodeltest;

import java.util.function.DoubleUnaryOperator;
import it.univr.binomialmodeltrees.BinomialModel;
import it.univr.utilities.UsefulMethodsForArrays;
import net.finmath.functions.AnalyticFormulas;
import it.univr.options.*;

public class BinomialModelTest {

	public static void main(String[] args) throws Exception {
		
		// time discretization
		int numberOfStepsOnAPeriod = 15;
		double timeHorizonForDiscretization = 2.0;
		int numberOfStepsStock = (int) Math.ceil(numberOfStepsOnAPeriod * timeHorizonForDiscretization);
		
		// stock parameters
		double initialValue = 100;
		double riskFreeRate = 0.04;
		double sigma = 0.25;
		
		// option parameters
		double timeHorizonOption = 2.0;
		int numberOfStepsOption = (int) Math.ceil(numberOfStepsOnAPeriod * timeHorizonOption);
		double strike = 90.0;
		
		/*
		 * Using Math.ceil ensures that the discretization fully covers the option's time to maturity
		 */
		
		
		// creation of the binomial model
		BinomialModel model = new BinomialModel (initialValue, riskFreeRate, sigma, numberOfStepsStock, timeHorizonForDiscretization);
		
		
		System.out.println("All realizations for all times are:");
		double[][] StockValues = model.getValues();
		UsefulMethodsForArrays.printMatrix(StockValues); // print the stock values at each node of the tree
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("All probabilities for all times are:");
		double[][] probabilities = model.getProbabilities();
		UsefulMethodsForArrays.printMatrix(probabilities); // print the risk neutral probabilities at each node of the tree
		
		
		// creation of the call and put functions
		DoubleUnaryOperator callFunction = (x) -> Math.max(x-strike, 0);
		DoubleUnaryOperator putFunction = (x) -> Math.max(strike-x, 0);
		
		
		// creation of the European call option
		EuropeanOption callOptionEU = new EuropeanOption(callFunction, numberOfStepsOption, numberOfStepsStock, model);
		
		// print the European call option values at each time step
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("The European Call Option values at each times are:");
		double[][] callValuesEU = callOptionEU.getOptionValues();
		UsefulMethodsForArrays.printMatrix(callValuesEU);
		
		
		
		// creation of the European put option
		EuropeanOption putOptionEU = new EuropeanOption(putFunction, numberOfStepsOption, numberOfStepsStock, model);
		
		// print the European put option values at each time step
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("The European Put Option values at each times are:");
		double[][] putValuesEU = putOptionEU.getOptionValues();
		UsefulMethodsForArrays.printMatrix(putValuesEU);
		
		
		// creation of the American call option
		AmericanOption callOptionUSA = new AmericanOption(callFunction, numberOfStepsOption, numberOfStepsStock, model);
		
		// print the American call option values at each time step
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("The American Call Option values at each times are:");
		double[][] callValuesUSA = callOptionUSA.getOptionValues();
		UsefulMethodsForArrays.printMatrix(callValuesUSA);
		
		
		// creation of the American put option
		AmericanOption putOptionUSA = new AmericanOption(putFunction, numberOfStepsOption, numberOfStepsStock, model);
		
		// print the American put option values at each time step
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("The American Put Option values at each times are:");
		double[][] putValuesUSA = putOptionUSA.getOptionValues();
		UsefulMethodsForArrays.printMatrix(putValuesUSA);
		
		
		// comparison between the four option prices
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Comparison of the European and American Call prices");
		System.out.println("European Call Option price: " + callValuesEU[0][0]);
		System.out.println("American Call Option price: " + callValuesUSA[0][0]);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Comparison of the European and American Put prices");
		System.out.println("European Put Option price: " + putValuesEU[0][0]);
		System.out.println("American Put Option price: " + putValuesUSA[0][0]);
		
		// comparison of European and American call option prices with the price computed using the Black-Scholes continuous-time formula
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Price of the Call Option computed via the Black Scholes continuous time model:");
		double priceBlackScholes = AnalyticFormulas.blackScholesOptionValue(initialValue, riskFreeRate, sigma, timeHorizonOption, strike);
		System.out.println(priceBlackScholes);
		System.out.println("Compared to the European and American Call prices computed using the methods");
		System.out.println("European Call Option price: " + callValuesEU[0][0]);
		System.out.println("American Call Option price: " + callValuesUSA[0][0]);
		}

}