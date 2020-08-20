package com.example.a8_puzzle.solver;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class PuzzleSolver {
    
    private static final String TAG = "PuzzleSolver.java";
    private List<Puzzle> steps;
    private Node solvedNode;
    private boolean isSolvable;
    
    // find a solution using the A* algorithm
    public PuzzleSolver(Puzzle initial) {
        Node initialNode = new Node(initial, 0, null);
        
        // Java's PriorityQueue is a minimum one by default (ordered in ascending order)
        PriorityQueue<Node> solver = new PriorityQueue<>();
        
        // insert initial nodes
        solver.add(initialNode);
        solve(solver);
    }
    
    private void solve(PriorityQueue<Node> solver) {
        
        while (true) {
            // delete min from priority queue
            Node delMin = solver.poll();
    
            // if deleted node is the solved puzzle, break
            if (delMin.puzzle.isSolved()) {
                solvedNode = delMin;
                isSolvable = true;
                return;
            }
            // insert all the neighbouring nodes to the deleted one
            for (Puzzle p : delMin.puzzle.neighbors()) {
                // do not insert the neighbour identical to deleted board's previous one
                if (delMin.prevNode == null || !p.equals(delMin.prevNode.puzzle)) {
                    solver.add(new Node(p, delMin.numOfMoves + 1, delMin));
                }
            }
        }
    }
    
    // min number of moves to solve initial board
    public int moves() {
        if (steps == null) {
            // if solution hasn't been called yet
            solution();
        }
        // then get size of steps
        if (isSolvable) {
            return steps.size() - 1;
            
        } else {
            return -1;
        }
    }
    
    // sequence of boards in a shortest solution
    public List<Puzzle> solution() {
        if (steps == null) {
            // if solution() not called yet
            steps = new ArrayList<>();
            if (isSolvable) {
                Node current = solvedNode;
                while (current != null) {
                    if (current.movement != null) {
                        current.puzzle.setMovement(current.movement);
                    } else {
                        // first puzzle (with no previous node, so no movement)
                        current.puzzle.setMovement("Starting puzzle: ");
                    }
                    steps.add(current.puzzle);
                    current = current.prevNode;
                }
                // steps were placed in reverse order (starting with the solved node)
                Collections.reverse(steps);
                return steps;
                
            } else {
                // if unsolvable return null
                return null;
            }
            
        } else {
            // else solution() has already been called; return the stored solution
            return steps;
        }
    }
    
    // create immutable object Node to represent each state of the puzzle tiles
    private static class Node implements Comparable<Node> {
        
        private final Puzzle puzzle;
        private final int numOfMoves;
        private final Node prevNode;
        private final int priority;
        private String movement;
    
    
        public Node(Puzzle puzzle, int numOfMoves, @Nullable Node prevNode) {
            this.puzzle = puzzle;
            this.numOfMoves = numOfMoves;
            this.prevNode = prevNode;
            // define the priority function as its manhattan distance plus the number of moves so far.
            priority = puzzle.manhattanDistance() + this.numOfMoves;
            if (prevNode != null) {
                movement = prevNode.puzzle.movement(this.puzzle);
            }
        }
        
        // implement Comparable methods for PriorityQueue
        @Override
        public int compareTo(Node that) {
            if (this.priority > that.priority) return +1;
            else if (this.priority < that.priority) return -1;
            else return Integer.compare(this.numOfMoves, that.numOfMoves);
        }
    }
}
