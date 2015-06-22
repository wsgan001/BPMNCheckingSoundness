/***********************************************************
 *      This software is part of the ProM package          *
 *             http://www.processmining.org/               *
 *                                                         *
 *            Copyright (c) 2003-2006 TU/e Eindhoven       *
 *                and is licensed under the                *
 *            Common Public License, Version 1.0           *
 *        by Eindhoven University of Technology            *
 *           Department of Information Systems             *
 *                 http://is.tm.tue.nl                     *
 *                                                         *
 **********************************************************/

package org.processmining.mining.geneticmining;

import org.processmining.framework.log.LogReader;
import org.processmining.framework.models.heuristics.HeuristicsNet;
import org.processmining.mining.geneticmining.duplicates.DTGeneticMinerResult;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Ana Karla A. de Medeiros.
 * @version 1.0
 */

public class GeneticMinerResult extends DTGeneticMinerResult {

	public GeneticMinerResult(HeuristicsNet[] pop, LogReader log) {
		super(pop, log);
	}
}
