/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2011, Open Source Geospatial Foundation (OSGeo)
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

package org.geotools.data.complex.feature.type;

import java.util.Collection;
import java.util.List;
import org.geotools.api.feature.type.AttributeType;
import org.geotools.api.feature.type.FeatureType;
import org.geotools.api.feature.type.GeometryDescriptor;
import org.geotools.api.feature.type.Name;
import org.geotools.api.feature.type.PropertyDescriptor;
import org.geotools.api.filter.Filter;
import org.geotools.api.util.InternationalString;
import org.geotools.feature.type.FeatureTypeFactoryImpl;
import org.geotools.feature.type.FeatureTypeImpl;

/**
 * A specialisation of {@link FeatureTypeFactoryImpl} that returns {@link UniqueNameFeatureTypeImpl}
 * instead of {@link FeatureTypeImpl} to avoid equality tests on types with cyclic definitions.
 *
 * <p>Users of this factory must not use it to create multiple FeatureType instances with the same
 * name unless they represent the same type, because other parts of the implementation will assume
 * they are equal, and if they are not, Bad Things Will Happen.
 *
 * @author Ben Caradoc-Davies (CSIRO Earth Science and Resource Engineering)
 * @see GEOT-3354
 */
public class UniqueNameFeatureTypeFactoryImpl extends FeatureTypeFactoryImpl {

    /**
     * Override superclass to return {@link UniqueNameFeatureTypeImpl} instead of {@link
     * FeatureTypeImpl}.
     *
     * @see
     *     org.geotools.feature.type.FeatureTypeFactoryImpl#createFeatureType(org.geotools.api.feature.type.Name,
     *     java.util.Collection, org.geotools.api.feature.type.GeometryDescriptor, boolean,
     *     java.util.List, org.geotools.api.feature.type.AttributeType,
     *     org.geotools.api.util.InternationalString)
     */
    @Override
    public FeatureType createFeatureType(
            Name name,
            Collection<PropertyDescriptor> schema,
            GeometryDescriptor defaultGeometry,
            boolean isAbstract,
            List<Filter> restrictions,
            AttributeType superType,
            InternationalString description) {
        return new UniqueNameFeatureTypeImpl(
                name, schema, defaultGeometry, isAbstract, restrictions, superType, description);
    }
}
