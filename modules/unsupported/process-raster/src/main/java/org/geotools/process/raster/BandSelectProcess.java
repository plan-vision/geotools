/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2014-2015, Open Source Geospatial Foundation (OSGeo)
 *    (C) 2014 TOPP - www.openplans.org.
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
package org.geotools.process.raster;

import org.geotools.api.coverage.SampleDimension;
import org.geotools.api.parameter.ParameterValueGroup;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.processing.CoverageProcessor;
import org.geotools.coverage.processing.operation.SelectSampleDimension;
import org.geotools.process.ProcessException;
import org.geotools.process.factory.DescribeParameter;
import org.geotools.process.factory.DescribeProcess;
import org.geotools.process.factory.DescribeResult;

/**
 * Process calling the {@link SelectSampleDimension} operation. This process requires:
 *
 * <ul>
 *   <li>a {@link GridCoverage2D} object.
 *   <li>an array of the Band Indexes to select.
 *   <li>an optional integer parameter associated to the visible sample dimension.
 * </ul>
 *
 * The output of this process is a {@link GridCoverage2D} object containing only the {@link
 * SampleDimension}s indicated by the input array.
 *
 * @author Nicola Lagomarsini, GeoSolutions S.A.S.
 */
@DescribeProcess(
        title = "Select Coverages",
        description =
                "Returns a raster generated by the selection of some bands from the input raster.")
public class BandSelectProcess implements RasterProcess {
    /** Processor to use for executing the {@link SelectSampleDimension} operation */
    private static final CoverageProcessor PROCESSOR = CoverageProcessor.getInstance();

    @DescribeResult(name = "result", description = "A selection on the input rasters")
    public GridCoverage2D execute(
            @DescribeParameter(name = "coverage", description = "Input GridCoverage", min = 1)
                    GridCoverage2D coverage,
            @DescribeParameter(
                            name = "SampleDimensions",
                            description = "Input sample dimension indexes",
                            min = 1)
                    int[] sampleDims,
            @DescribeParameter(
                            name = "VisibleSampleDimension",
                            description = "Input visible sample dimension index",
                            min = 0)
                    Integer visibleSampleDim)
            throws ProcessException {

        // //
        //
        // Parameters definition
        //
        // //
        final ParameterValueGroup param =
                PROCESSOR.getOperation("SelectSampleDimension").getParameters();
        // Setting of the source
        param.parameter("Source").setValue(coverage);
        // Setting of the Sample Dimension
        param.parameter("SampleDimensions").setValue(sampleDims);
        // Setting of the Sample Dimension
        param.parameter("VisibleSampleDimension").setValue(visibleSampleDim);

        // Call the "BandSelect" operation
        return (GridCoverage2D) PROCESSOR.doOperation(param);
    }
}
