/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2007-2008, Open Source Geospatial Foundation (OSGeo)
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
package org.geotools.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.geotools.api.geometry.Position;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.junit.Test;

/**
 * Tests the {@link GeneralPosition} and {@link Position2D} classes.
 *
 * @version $Id$
 * @author Martin Desruisseaux
 */
public final class PositionTest {
    /**
     * Tests {@link GeneralPosition#equals} method between different implementations. The purpose of
     * this test is also to run the assertion in the direct position implementations.
     */
    @Test
    public void testEquals() {
        assertTrue(GeneralPosition.class.desiredAssertionStatus());
        assertTrue(Position2D.class.desiredAssertionStatus());

        CoordinateReferenceSystem WGS84 = DefaultGeographicCRS.WGS84;
        Position p1 = new Position2D(WGS84, 48.543261561072285, -123.47009555832284);
        GeneralPosition p2 = new GeneralPosition(48.543261561072285, -123.47009555832284);
        assertNotEquals(p1, p2);
        assertNotEquals(p2, p1);

        p2.setCoordinateReferenceSystem(WGS84);
        assertEquals(p1, p2);
        assertEquals(p2, p1);
    }
}
