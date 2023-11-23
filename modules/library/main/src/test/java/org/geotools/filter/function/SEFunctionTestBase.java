/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2008-2010, Open Source Geospatial Foundation (OSGeo)
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

import java.util.List;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.api.filter.FilterFactory;
import org.geotools.api.filter.expression.Expression;
import org.geotools.data.DataUtilities;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.filter.FunctionFinder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

/**
 * Fields and helper method for unit test classes
 *
 * @author Michael Bedward
 */
public class SEFunctionTestBase {

    protected final FilterFactory ff2 = CommonFactoryFinder.getFilterFactory(null);
    protected final FunctionFinder finder = new FunctionFinder(null);
    protected final GeometryFactory gf = JTSFactoryFinder.getGeometryFactory(null);
    protected List<Expression> parameters;

    protected SimpleFeature feature(Object value) throws Exception {
        String typeSpec = "geom:Point,value:" + value.getClass().getSimpleName();
        SimpleFeatureType type = DataUtilities.createType("Feature", typeSpec);
        SimpleFeatureBuilder builder = new SimpleFeatureBuilder(type);
        Coordinate coord = new Coordinate(0, 0);
        builder.add(gf.createPoint(coord));
        builder.add(value);
        return builder.buildFeature(null);
    }
}
