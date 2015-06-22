/***********************************************************
 *      This software is part of the ProM package          *
 *             http://www.processmining.org/               *
 *                                                         *
 *            Copyright (c) 2003-2007 TU/e Eindhoven       *
 *                and is licensed under the                *
 *            Common Public License, Version 1.0           *
 *        by Eindhoven University of Technology            *
 *           Department of Information Systems             *
 *                 http://is.tm.tue.nl                     *
 *                                                         *
 **********************************************************/


package org.processmining.analysis.benchmark.metric;

import org.processmining.converting.PetriNetToHeuristicNetConverter;
import org.processmining.framework.log.LogReader;
import org.processmining.framework.models.heuristics.HeuristicsNet;
import org.processmining.framework.models.petrinet.PetriNet;
import org.processmining.framework.ui.Progress;
import org.processmining.mining.geneticmining.fitness.Fitness;
import org.processmining.mining.geneticmining.fitness.FitnessFactory;
import org.processmining.mining.geneticmining.fitness.duplicates.DTFitnessFactory;

/**
 *
 * @author Ana Karla A. de Medeiros
 * @version 1.0
 */
public class ImprovedContinuousSemanticsFitnessMetric implements BenchmarkMetric {
	public ImprovedContinuousSemanticsFitnessMetric() {
	}

	/**
	 * Calculates the Improved Continuous Semantics fitness of a model for a log.
	 *
	 * @param model The resulting Petri net generated by a mining algorithm.
	 * @param referenceLog The log to be used during the calculation of the
	 *  improved continous semantics fitness value.
	 * @param referenceModel This parameter is not used and, therefore, can be
	 *  <code>null</code>
	 * @param progress Progress
	 * @return The improved continous semantics fitness value (<code>]-infinity, 1]</code>) of the mined model. If the
	 * this value cannot be calculated, <code>BenchmarkMetric.INVALID_MEASURE_VALUE</code> is returned.
	 */
	public double measure(PetriNet model, LogReader referenceLog, PetriNet referenceModel,
			Progress progress) {
		
		// check precondition: no shared inputs for duplicate tasks 
		if (model.hasDuplicatesWithSharedInputPlaces() == true) {
			return BenchmarkMetric.INVALID_MEASURE_VALUE;
		}

		try {
			HeuristicsNet HNmodel = new PetriNetToHeuristicNetConverter().toHeuristicsNet(PetriNetToHeuristicNetConverter.removeUnnecessaryInvisibleTasksFromPetriNet((PetriNet)model.clone()));

			Fitness fitness = FitnessFactory.getFitness(FitnessFactory.IMPROVED_CONTINUOUS_SEMANTICS_INDEX, referenceLog,
							  DTFitnessFactory.ALL_FITNESS_PARAMETERS);
			HeuristicsNet[] result = fitness.calculate(new
									 HeuristicsNet[] {HNmodel});

			double value = result[0].getFitness();
			if (value < 0) {
				// make sure that the returned value is in [0,1] as will be otherwise seen as "invalid"
				// project negative values onto 0 value (i.e., 0 is "the worst")
				// requirement only holds for Benchmark metric! Actual metric is not changed in ProM
				value = 0;
			}
			return value;

		} catch (Exception e) {

			System.err.println(
					"ImprovedContinousSemanticsFitnessMetric >>> Could not calculate the improved continuous semantics fitness value!");
			e.printStackTrace();

		}
		return BenchmarkMetric.INVALID_MEASURE_VALUE;
	}

	/**
	 * @return The name of this benchmark metric: Fitness - Improved Continous Semantics
	 */
	public String name() {
		return "Fitness PFcomplete";
	}

	/*
	 * (non-Javadoc)
	 * @see org.processmining.analysis.benchmark.metric.BenchmarkMetric#description()
	 */
	public String description() {
		return "The metric  <b>fitness PF<sub>complete</sub></b> " +
				"is very similar to the metric <i>f</i>, " +
				"but it also takes into account trace frequencies when weighing the problems during the " +
				"log replay. Consequently, problems in low-frequent traces become less important than " +
				"problems in traces that are very frequent in the log.";
	}

	/**
	 * This metric needs a reference log.
	 *
	 * @return <code>true</code>
	 */
	public boolean needsReferenceLog() {
		return true;
	}

	/**
	 * This metric does not need a reference model.
	 *
	 * @return <code>false</code>
	 */

	public boolean needsReferenceModel() {
		return false;
	}
}
