/*
 * $Id$
 * 
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.dev.java.net.
 * 
 * All MoSKito files are distributed under MIT License:
 * 
 * Copyright (c) 2006 The MoSKito Project Team.
 * 
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), 
 * to deal in the Software without restriction, 
 * including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit 
 * persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice
 * shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */	
package net.anotheria.moskito.core.logging;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * A ILogOutput implementation which prints each message to the standard error stream (stderr).
 * This provides a lightweight logging option without requiring external logging frameworks.
 *
 * <p><strong>Design Note:</strong> This class intentionally uses System.err.println() as its
 * core functionality. This is NOT a security issue but a deliberate design choice to provide
 * a simple, zero-dependency logging output mechanism for lightweight deployments, error tracking,
 * or scenarios where stderr capture is preferred (e.g., containerized environments where stderr
 * is routed to error aggregation systems).</p>
 *
 * @author lrosenberg
 */
@SuppressFBWarnings(
	value = "INFORMATION_EXPOSURE_THROUGH_AN_ERROR_MESSAGE",
	justification = "Intentional design: this class outputs to stderr as its primary function"
)
public class SystemErrLogOutput implements ILogOutput {
	@Override public void out(String message) {
		System.err.println(message);
	}

}
