/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.WeightedGraph;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.AssociationTypeES;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;

/**
 *
 * @author jgeenen
 */
public class ScenarioGraph extends HasSeal implements DirectedGraph<Vertex, Arc> {
    
    private static final AssociationTargetTypeResolver ASSOCIATION_TARGET_TYPE_RESOLVER = new AssociationTargetTypeResolver();

    /**
     * A globally unique identifier for {@code this}
     */
    public final String scenario;
    private Map<Class, Vertex> class2Vertex = new HashMap<>();
    private Map<ManagedTypeES, Vertex> type2Vertex = new HashMap<>();
    private Set<Vertex> vertices = new HashSet<>();
    private Set<Arc> arcs = new HashSet<>();
    private Map<AssociationES, Arc> association2Arc = new HashMap<>();

    protected ScenarioGraph(String scenario) {
        this.scenario = scenario;
    }

    /**
     * Seals {@code this} against modifications
     */
    @Override
    public void seal() {
        if (!isSealed()) {
            class2Vertex = Collections.unmodifiableMap(class2Vertex);
            type2Vertex = Collections.unmodifiableMap(type2Vertex);
            vertices = Collections.unmodifiableSet(vertices);
            arcs = Collections.unmodifiableSet(arcs);
            association2Arc = Collections.unmodifiableMap(association2Arc);
            for (Set<? extends Element> elements : Arrays.asList(vertices, arcs)) {
                for (Element element : elements) {
                    element.seal();
                }
            }
            super.seal();
        }
    }

    /**
     * Gets (or creates if absent) the arc {@code this} &rarr the association's
     * target {@link Vertex}
     *
     * @param sourceVertex the {@link Arc#getSource()}
     * @param attribute the {@link Arc#getAssociation()}'s {@link AttributeES}
     * @param associationType
     * @return
     */
    
    protected Arc getOrCreate(Vertex sourceVertex, AttributeES attribute, AssociationTypeES associationType)  throws IllegalArgumentException, IllegalStateException{
        final AssociationES association = attribute.getAssociation(associationType);
        if (association == null) {
            throw new IllegalArgumentException(attribute + " is no association");
        }
        if (sourceVertex.getScenarioGraph() != this) {
            throw new IllegalArgumentException(sourceVertex + " is not contained in " + this);
        }
        Arc retval = getArc(association);
        if (retval == null) {
            assertNotSealed();
            
            final ManagedTypeES targetType = (ManagedTypeES) attribute.accept(ASSOCIATION_TARGET_TYPE_RESOLVER,associationType);
            final Vertex targetVertex = getOrCreate(targetType);
            retval = new Arc(this, association, sourceVertex, targetVertex);

            sourceVertex.getSuccessors().add(targetVertex);
            targetVertex.getPredecessors().add(sourceVertex);
            sourceVertex.getTouching().add(retval);
            targetVertex.getTouching().add(retval);

            association2Arc.put(association, retval);
            arcs.add(retval);
        }
        return retval;

    }
    
    public Vertex getVertex(Class javaType){
        return class2Vertex.get(javaType);
    }
    
    public Vertex getVertex(ManagedTypeES managedType){
        return class2Vertex.get(managedType.getJavaType());
    }
    
    public Arc getArc(AssociationES association){
        return association2Arc.get(association);
    }
    
    

    /**
     * <p>
     * <strong>The one and only way of creating a {@link Vertex}.</strong></p>
     * Gets the {@link Vertex} corresponding to the {@code managedType} if
     * existent, otherwise creating it.
     *
     * @param managedType
     * @return the {@link Vertex} corresponding to the {@code managedType}
     * @throws IllegalStateException if {@code && isSealed()} and the
     * {@link Vertex} did not yet exist
     */
    protected Vertex getOrCreate(ManagedTypeES managedType) throws IllegalStateException {
        Vertex vertex = type2Vertex.get(managedType);
        if (vertex == null) {
            assertNotSealed();
            vertex = new Vertex(this, managedType);
            class2Vertex.put(managedType.getJavaType(), vertex);
            type2Vertex.put(managedType, vertex);
            vertices.add(vertex);
        }
        return vertex;
    }

    public Set<Arc> getArcs() {
        return arcs;
    }


    @Override
    public int inDegreeOf(Vertex vertex) {
        return vertex.getIn().size();
    }

    @Override
    public Set<Arc> incomingEdgesOf(Vertex vertex) {
        return vertex.getIn();
    }

    @Override
    public int outDegreeOf(Vertex vertex) {
        return vertex.getOut().size();
    }

    @Override
    public Set<Arc> outgoingEdgesOf(Vertex vertex) {
        return vertex.getOut();
    }

    @Override
    public Set<Arc> getAllEdges(Vertex sourceVertex, Vertex targetVertex) {
        if (sourceVertex == null || targetVertex == null) {
            return Collections.EMPTY_SET;
        } else {
            final Set<Arc> retval = new HashSet<>();
            for (Arc a : sourceVertex.getOut()) {
                if (a.getTarget().equals(targetVertex)) {
                    retval.add(a);
                }
            }
            return retval;

        }
    }

    @Override
    public Arc getEdge(Vertex sourceVertex, Vertex targetVertex) {
        for (Arc a : sourceVertex.getOut()) {
            if (a.getTarget().equals(targetVertex)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public EdgeFactory<Vertex, Arc> getEdgeFactory() {
        return (Vertex sourceVertex, Vertex targetVertex) -> {
            throw new UnsupportedOperationException("structural modification is unsupported yet.");
        };

    }

    @Override
    public Arc addEdge(Vertex sourceVertex, Vertex targetVertex) {
        throw new UnsupportedOperationException("structural modification is unsupported");
    }

    @Override
    public boolean addEdge(Vertex sourceVertex, Vertex targetVertex, Arc e) {
        throw new UnsupportedOperationException("structural modification is unsupported");
    }

    @Override
    public boolean addVertex(Vertex v) {
        throw new UnsupportedOperationException("structural modification is unsupported");
    }

    @Override
    public boolean containsEdge(Vertex sourceVertex, Vertex targetVertex) {
        return sourceVertex != null
                && targetVertex != null
                && sourceVertex.getSuccessors().contains(targetVertex);
    }

    @Override
    public boolean containsEdge(Arc e) {
        return e.getScenarioGraph() == this;
    }

    @Override
    public boolean containsVertex(Vertex v) {
        return v.getScenarioGraph() == this;
    }

    @Override
    public Set<Arc> edgeSet() {
        return arcs;
    }

    @Override
    public Set<Arc> edgesOf(Vertex vertex) {
        return vertex.getTouching();
    }

    @Override
    public boolean removeAllEdges(Collection<? extends Arc> edges) throws UnsupportedOperationException{
        throw new UnsupportedOperationException("structural modification is unsupported.");
    }

    @Override
    public Set<Arc> removeAllEdges(Vertex sourceVertex, Vertex targetVertex) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("structural modification is unsupported.");
    }

    @Override
    public boolean removeAllVertices(Collection<? extends Vertex> vertices) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("structural modification is unsupported.");
    }

    @Override
    public Arc removeEdge(Vertex sourceVertex, Vertex targetVertex) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("structural modification is unsupported.");
    }

    @Override
    public boolean removeEdge(Arc e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("structural modification is unsupported.");
    }

    @Override
    public boolean removeVertex(Vertex v) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("structural modification is unsupported.");
    }

    @Override
    public Set<Vertex> vertexSet() {
        return vertices;
    }

    @Override
    public Vertex getEdgeSource(Arc e) {
        return e.getSource();
    }

    @Override
    public Vertex getEdgeTarget(Arc e) {
        return e.getTarget();
    }

    @Override
    public double getEdgeWeight(Arc e) {
        return WeightedGraph.DEFAULT_EDGE_WEIGHT;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.scenario);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ScenarioGraph other = (ScenarioGraph) obj;
        return Objects.equals(this.scenario, other.scenario);
    }

}
