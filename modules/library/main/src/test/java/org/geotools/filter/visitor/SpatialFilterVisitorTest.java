package org.geotools.filter.visitor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.geotools.api.filter.Filter;
import org.geotools.api.filter.FilterFactory;
import org.geotools.factory.CommonFactoryFinder;
import org.junit.Before;
import org.junit.Test;

public class SpatialFilterVisitorTest {

    FilterFactory ff = CommonFactoryFinder.getFilterFactory(null);
    private SpatialFilterVisitor visitor;

    @Before
    public void setUp() throws Exception {
        visitor = new SpatialFilterVisitor();
    }

    @Test
    public void testInclude() {
        Filter.INCLUDE.accept(visitor, null);
        assertFalse(visitor.hasSpatialFilter);
    }

    @Test
    public void testExclude() {
        Filter.EXCLUDE.accept(visitor, null);
        assertFalse(visitor.hasSpatialFilter);
    }

    @Test
    public void testBBOX() {
        ff.bbox("geom", 0, 0, 10, 10, "EPSG:4326").accept(visitor, null);
        assertTrue(visitor.hasSpatialFilter);
    }

    @Test
    public void testIntersects() {
        ff.intersects(ff.property("geom"), ff.literal(null)).accept(visitor, null);
        assertTrue(visitor.hasSpatialFilter);
    }

    @Test
    public void testOverlaps() {
        ff.overlaps(ff.property("geom"), ff.literal(null)).accept(visitor, null);
        assertTrue(visitor.hasSpatialFilter);
    }
}
