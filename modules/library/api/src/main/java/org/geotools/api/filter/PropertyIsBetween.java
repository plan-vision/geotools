/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2011, Open Source Geospatial Foundation (OSGeo)
 *    (C) 2005 Open Geospatial Consortium Inc.
 *
 *    All Rights Reserved. http://www.opengis.org/legal/
 */
package org.geotools.api.filter;

// OpenGIS direct dependencies

import org.geotools.api.filter.expression.Expression;

/**
 * A compact way of encoding a range check.
 *
 * <p>The lower and upper boundary values are inclusive.
 *
 * @version <A HREF="http://www.opengis.org/docs/02-059.pdf">Implementation specification 1.0</A>
 * @author Chris Dillard (SYS Technologies)
 * @since GeoAPI 2.0
 */
public interface PropertyIsBetween extends MultiValuedFilter {
    /** Operator name used to check FilterCapabilities */
    public static String NAME = "Between";

    /** Returns the expression to be compared by this operator. */
    Expression getExpression();

    /** Returns the lower bounds (inclusive) an an expression. */
    Expression getLowerBoundary();

    /** Returns the upper bounds (inclusive) as an expression. */
    Expression getUpperBoundary();
}
