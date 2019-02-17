/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2008, Open Source Geospatial Foundation (OSGeo)
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
package org.geotools.graph.build.line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.geotools.graph.build.GraphBuilder;
import org.geotools.graph.build.GraphGenerator;
import org.geotools.graph.structure.Edge;
import org.geotools.graph.structure.Graph;
import org.geotools.graph.structure.Graphable;
import org.geotools.graph.structure.Node;
import org.geotools.graph.structure.line.OptXYNode;
import org.geotools.graph.structure.opt.OptEdge;
import org.geotools.graph.structure.opt.OptNode;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineSegment;

/**
 * An implementation of GraphGenerator used to generate an optimized graph representing a line
 * network. Graphs are generated by supplying the generator with objects of type LineSegment via the
 * add(Object) method. <br>
 * <br>
 * For each line segment added, an edge in the graph is created. The builder records the end
 * coordinates of each line added, and maintains a map of coordinates to nodes, creating nodes when
 * neccessary.<br>
 * <br>
 * Edges created by the generator are of type OptBasicEdge.<br>
 * Nodes created by the generator are of type OptXYNode. <br>
 * <br>
 * Since building optimized graphs requires knowing the degree of nodes before creating them, the
 * physical construction of the graph is delayed until a call to generate() is made. No component is
 * created with a call to add(Object), only information about the object is recorded.
 *
 * @see org.geotools.graph.structure.opt.OptEdge
 * @see org.geotools.graph.structure.line.OptXYNode
 * @author Justin Deoliveira, Refractions Research Inc, jdeolive@refractions.net
 */
public class OptLineGraphGenerator implements LineGraphGenerator {

    /** coordinate to node / count * */
    private HashMap m_coord2count;

    /** lines added to the network * */
    private ArrayList m_lines;

    /** underlying builder * */
    private GraphBuilder m_builder;

    /** Constructs a new OptLineGraphGenerator. */
    public OptLineGraphGenerator() {
        m_coord2count = new HashMap();
        m_lines = new ArrayList();
        setGraphBuilder(new OptLineGraphBuilder());
    }

    /**
     * Adds a line to the graph. Note that this method returns null since actual building of the
     * graph components is delayed until generate() is called.
     *
     * @param obj A LineSegment object.
     * @return null because the actual building of the graph components is delayed until generate()
     *     is called.
     */
    public Graphable add(Object obj) {
        LineSegment line = (LineSegment) obj;
        Integer count;

        // update count of first coordinate
        if ((count = (Integer) m_coord2count.get(line.p0)) == null) {
            m_coord2count.put(line.p0, Integer.valueOf(1));
        } else m_coord2count.put(line.p0, Integer.valueOf(count.intValue() + 1));

        // update count of second coordinate
        if ((count = (Integer) m_coord2count.get(line.p1)) == null) {
            m_coord2count.put(line.p1, Integer.valueOf(1));
        } else m_coord2count.put(line.p1, Integer.valueOf(count.intValue() + 1));

        // hold onto a reference to the line
        m_lines.add(line);

        // return null, no componenets created
        return (null);
    }

    /**
     * Returns the edge which represents a line. This method must be called after the call to
     * generate(). Note that if the exact same line has been added to the graph multiple times, then
     * only one of the edges that represents it will be returned. It is undefined which edge will be
     * returned.
     *
     * @param obj An instance of LineSegment.
     * @return Edge that represents the line.
     * @see GraphGenerator#get(Object)
     */
    public Graphable get(Object obj) {
        LineSegment line = (LineSegment) obj;

        Node n1 = (Node) m_coord2count.get(line.p0);
        Node n2 = (Node) m_coord2count.get(line.p0);

        return (n1.getEdge(n2));

        // note: if there are identical lines in the graph then it is undefined
        // which of them will be returned
    }

    /**
     * Unsupported operation.
     *
     * @throws UnsupportedOperationException
     */
    public Graphable remove(Object obj) {
        throw new UnsupportedOperationException(getClass().getName() + "#remove(Object)");
    }

    /** @see GraphGenerator#setGraphBuilder(GraphBuilder) */
    public void setGraphBuilder(GraphBuilder builder) {
        m_builder = builder;
    }

    /** @see GraphGenerator#getGraphBuilder() */
    public GraphBuilder getGraphBuilder() {
        return (m_builder);
    }

    /** @see GraphGenerator#getGraph() */
    public Graph getGraph() {
        return (m_builder.getGraph());
    }

    /** Performs the actual generation of the graph. */
    public void generate() {
        generateNodes();
        generateEdges();
    }

    /**
     * Returns the coordinate to node map. Note that before the call to generate the map does not
     * contain any nodes.
     *
     * @return Coordinate to node map.
     */
    public Map getNodeMap() {
        return (m_coord2count);
    }

    /**
     * Returns the lines added to the graph.
     *
     * @return A list of LineSegment objects.
     */
    protected List getLines() {
        return (m_lines);
    }

    protected void generateNodes() {
        // create nodes from coordiante counts
        for (Iterator itr = m_coord2count.entrySet().iterator(); itr.hasNext(); ) {
            Map.Entry entry = (Map.Entry) itr.next();
            Coordinate coord = (Coordinate) entry.getKey();
            Integer count = (Integer) entry.getValue();

            OptXYNode node = (OptXYNode) m_builder.buildNode();
            node.setDegree(count.intValue());
            node.setCoordinate(coord);

            m_builder.addNode(node);

            entry.setValue(node);
        }
    }

    protected void generateEdges() {
        // relate nodes
        for (Iterator itr = m_lines.iterator(); itr.hasNext(); ) {
            LineSegment line = (LineSegment) itr.next();
            generateEdge(line);
        }
    }

    protected Edge generateEdge(LineSegment line) {
        OptNode n1 = (OptNode) m_coord2count.get(line.p0);
        OptNode n2 = (OptNode) m_coord2count.get(line.p1);

        OptEdge edge = (OptEdge) m_builder.buildEdge(n1, n2);
        m_builder.addEdge(edge);

        return (edge);
    }

    public Node getNode(Coordinate c) {
        return ((Node) m_coord2count.get(c));
    }

    public Edge getEdge(Coordinate c1, Coordinate c2) {
        Node n1 = (Node) m_coord2count.get(c1);
        Node n2 = (Node) m_coord2count.get(c2);

        return (n1.getEdge(n2));
    }
}
