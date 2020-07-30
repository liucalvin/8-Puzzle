package com.example.a8_puzzle.solver;

import java.util.PriorityQueue;
import java.util.Stack;

public class PuzzleSolver {
    
    private Stack<Puzzle> steps;
    private Node goalNode;
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
            
            // if deleted node is the goal, break
            if (delMin.puzzle.isGoal()) {
                goalNode = delMin;
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
    public Iterable<Puzzle> solution() {
        if (steps == null) {
            // if solution() not called yet
            steps = new Stack<>();
            if (isSolvable) {
                Node current = goalNode;
                while (current != null) {
                    steps.push(current.puzzle);
                    current = current.prevNode;
                }
                return steps;
                
            } else {
                // if unsolvable return null
                return null;
            }
            
        } else {
            // else solution() has already been called; return the solution without iterating again
            return steps;
        }
    }
    
    // create immutable object Node to represent each state of the puzzle tiles
    private static class Node implements Comparable<Node> {
        
        private final Puzzle puzzle;
        private final int numOfMoves;
        private final Node prevNode;
        private final int priority;
        
        
        public Node(Puzzle puzzle, int numOfMoves, Node prevNode) {
            this.puzzle = puzzle;
            this.numOfMoves = numOfMoves;
            this.prevNode = prevNode;
            // define the priority function as its manhattan distance plus the number of moves so far.
            priority = puzzle.manhattanDistance() + this.numOfMoves;
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
