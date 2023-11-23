/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2010, Open Source Geospatial Foundation (OSGeo)
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
package org.geotools.geojson;

import java.io.IOException;
import org.geotools.api.feature.Feature;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.feature.FeatureCollection;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geojson.geom.GeometryJSON;
import org.locationtech.jts.geom.Geometry;

public class GeoJSON {
    static GeometryJSON gjson = new GeometryJSON();
    static FeatureJSON fjson = new FeatureJSON();

    public static Object read(Object input) throws IOException {
        throw new UnsupportedOperationException();
    }

    public static void write(Object obj, Object output) throws IOException {
        if (obj instanceof Geometry) {
            gjson.write((Geometry) obj, output);
        } else if (obj instanceof Feature
                || obj instanceof FeatureCollection
                || obj instanceof CoordinateReferenceSystem) {

            if (obj instanceof SimpleFeature) {
                fjson.writeFeature((SimpleFeature) obj, output);
            } else if (obj instanceof FeatureCollection) {
                fjson.writeFeatureCollection((FeatureCollection) obj, output);
            } else if (obj instanceof CoordinateReferenceSystem) {
                fjson.writeCRS((CoordinateReferenceSystem) obj, output);
            } else {
                throw new IllegalArgumentException(
                        "Unable able to encode object of type " + obj.getClass());
            }
        }
    }
}
