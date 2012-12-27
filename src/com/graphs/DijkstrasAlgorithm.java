package com.graphs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.graphs.Graph.Path;

public class DijkstrasAlgorithm {
	public static void main(String [] args) {
		try {
			BufferedReader in = new BufferedReader (new FileReader("graph.json"));
			Graph g = new Graph(in.readLine());
			
			g.printVerticesInformation();
			
			List<Vertex> vertices = g.getAllVertices();
			Path p = g.findShortestPathBetween(vertices.get(0), vertices.get(3));
			
			p.printPath();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

