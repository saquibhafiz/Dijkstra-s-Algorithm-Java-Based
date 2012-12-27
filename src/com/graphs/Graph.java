package com.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

public class Graph {
	
	private List<Vertex> vertices = new ArrayList<Vertex>();
	
	public Graph(String jsonString) {
		try {
			JSONArray arrayOfVertices = new JSONArray(jsonString);
			
			for (int i = 0; i < arrayOfVertices.length(); i++) {
				Vertex vertex = new Vertex(arrayOfVertices.getJSONObject(i).toString());
				vertices.add(vertex);
			}
			
			for (Vertex v : vertices) {
				v.setNeighbours(vertices);
			}
		} catch (JSONException e) {
			System.err.println("NOT PROPER JSON ARRAY : " + jsonString);
			e.printStackTrace();
		}
	}
	
	public void printVerticesInformation() {
		for (Vertex v : vertices)
			v.printVertexInformation();
	}
	
	public Path findShortestPathBetween(Vertex startingPoint, Vertex destination) {
		if (!vertices.contains(startingPoint) || !vertices.contains(destination))
			throw new IllegalArgumentException();
		
		return findShortestPathBetween(new Path(startingPoint), startingPoint, destination);
	}

	private Path findShortestPathBetween(Path path, Vertex currentPosition, Vertex destination) {
		Path bestPath = null;
		
		// if we have reached the destination return this as a proper path
		if (currentPosition.equals(destination))
			return path;
		
		// go through every neighbour and see which ones reaches the destination faster
		for (Map.Entry<Vertex, Integer> neighbourEntry : currentPosition.getNeighbours().entrySet()){
			Vertex neighbour = neighbourEntry.getKey();
			int distance = neighbourEntry.getValue();
			
			// this is so that we do not revisit the same vertex
			if (path.contains(neighbour))
				continue;
			
			// recursively look for the shortest path
			Path currentPath = findShortestPathBetween(new Path(path, neighbour, distance), neighbour, destination);
			
			// if you can't reach the destination going through this vertex then skip it
			if (currentPath == null)
				continue;
			
			// update the distance used to reach the destination
			if (bestPath == null || currentPath.distance < bestPath.distance)
				bestPath = currentPath;
		}
		
		return bestPath;
	}
	
	public List<Vertex> getAllVertices() {
		return vertices;
	}
	
	public class Path {
		private ArrayList<Vertex> path = new ArrayList<Vertex>();
		private int distance = 0;
		
		public Path(Vertex statingPpoint){
			this.path.add(statingPpoint);
		}
		
		@SuppressWarnings("unchecked")
		public Path(Path path, Vertex pointToAdd, int distanceToAdd) {
			this.path = (ArrayList<Vertex>) path.path.clone();
			this.path.add(pointToAdd);
			this.distance = path.distance + distanceToAdd;
		}
		
		public boolean contains(Vertex v) {
			return path.contains(v);
		}
		
		public void printPath() {
			for (Vertex v : path)
				System.out.print(v.getLabel() + " - ");
			
			System.out.println(distance);
		}
	}
}
