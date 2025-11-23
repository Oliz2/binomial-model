This project implements a financial derivatives pricing engine based on the Cox-Ross-Rubinstein (CRR) binomial model. It supports pricing for both European and American options with a flexible and robust framework.
The core classes are designed to handle any derivative whose payoff depends on the underlying asset’s terminal value. American option pricing is performed using backward induction, 
incorporating optimal early exercise decisions through the Snell envelope.
The engine is structured around a common Options interface and has been validated against theoretical properties, including convergence to Black-Scholes prices.
