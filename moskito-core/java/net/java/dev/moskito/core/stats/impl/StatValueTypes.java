/*
 * $Id$
 * $Author$
 *
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.java.net.
 * 
 * Copyright (c) 2006 by MoSKito Project
 *
 * All MoSKito files are under MIT License: 
 * http://www.opensource.org/licenses/mit-license.php
 */
package net.java.dev.moskito.core.stats.impl;

/**
 * The internal type representation for StatValues.
 *
 * @author miros
 */
enum StatValueTypes {
	/**
	 * A long value is required
	 */
	LONG, 
	/**
	 * An int value is required
	 */
	INT,
	/**
	 * A double value is required
	 */
	DOUBLE
}