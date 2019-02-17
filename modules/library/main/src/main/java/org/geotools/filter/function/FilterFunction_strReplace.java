/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2005-2008, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotools.filter.function;

// this code is autogenerated - you shouldnt be modifying it!

import static org.geotools.filter.capability.FunctionNameImpl.parameter;

import org.geotools.filter.FunctionExpressionImpl;
import org.geotools.filter.capability.FunctionNameImpl;
import org.opengis.filter.capability.FunctionName;

public class FilterFunction_strReplace extends FunctionExpressionImpl {

    public static FunctionName NAME =
            new FunctionNameImpl(
                    "strReplace",
                    parameter("string", String.class),
                    parameter("string", String.class),
                    parameter("search", String.class),
                    parameter("replace", String.class),
                    parameter("all", Boolean.class));

    public FilterFunction_strReplace() {
        super(NAME);
    }

    public Object evaluate(Object feature) {
        String arg0;
        String arg1;
        String arg2;
        Boolean arg3 = null;

        try { // attempt to get value and perform conversion
            arg0 = (String) getExpression(0).evaluate(feature, String.class); // extra
        } catch (Exception e) // probably a type error
        {
            throw new IllegalArgumentException(
                    "Filter Function problem for function strMatches argument #0 - expected type String");
        }

        try { // attempt to get value and perform conversion
            arg1 = (String) getExpression(1).evaluate(feature, String.class); // extra
        } catch (Exception e) // probably a type error
        {
            throw new IllegalArgumentException(
                    "Filter Function problem for function strMatches argument #1 - expected type String");
        }

        try { // attempt to get value and perform conversion
            arg2 = (String) getExpression(2).evaluate(feature, String.class); // extra
        } catch (Exception e) // probably a type error
        {
            throw new IllegalArgumentException(
                    "Filter Function problem for function strMatches argument #1 - expected type String");
        }

        try { // attempt to get value and perform conversion
            arg3 = (Boolean) getExpression(3).evaluate(feature, Boolean.class); // extra
        } catch (Exception e) // probably a type error
        {
        }

        if (arg3 == null) {
            arg3 = Boolean.FALSE;
        }
        return StaticGeometry.strReplace(arg0, arg1, arg2, arg3.booleanValue());
    }
}
