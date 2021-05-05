package com.example.cool8puzzle.solver

// find a solution using the A* algorithm

import com.example.cool8puzzle.entity.Puzzle
import java.util.PriorityQueue

class PuzzleSolver constructor(initial: Puzzle? = null) {

    private var steps: MutableList<Puzzle>? = null
    private var solvedNode: Node? = null
    private var isSolvable = false

    init {
        initial?.let { solve(it) }
    }

    fun solve(puzzle: Puzzle) {
        val initialNode = Node(puzzle, 0, null)

        // Java's PriorityQueue is a minimum one by default (ordered in ascending order)
        val solver = PriorityQueue<Node>()

        solver.add(initialNode)
        if (puzzle.isSolvable()) {
            solve(solver)
        }
    }

    private fun solve(solver: PriorityQueue<Node>) {
        while (true) {
            val delMin = solver.poll()

            if (delMin != null) {
                if (delMin.puzzle.isSolved()) {
                    solvedNode = delMin
                    isSolvable = true
                    return
                }

                for (p in delMin.puzzle.neighbors()) {
                    // do not insert the neighbour identical to deleted board's previous one
                    if (delMin.prevNode == null || p != delMin.prevNode.puzzle) {
                        solver.add(
                            Node(
                                p,
                                delMin.numOfMoves + 1,
                                delMin
                            )
                        )
                    }
                }
            }
        }
    }

    fun moves(): Int {
        if (steps == null) {
            solution()
        }

        return if (isSolvable) {
            steps!!.size - 1
        } else {
            -1
        }
    }

    fun solution(): MutableList<Puzzle>? {
        return if (steps == null) {
            steps = mutableListOf()
            if (isSolvable) {
                var current = solvedNode
                while (current != null) {
                    if (current.movement != null) {
                        // add the movement text
                        current.puzzle.movement = current.movement
                    } else {
                        // first puzzle (with no previous node, so no movement)
                        current.puzzle.movement = "Starting puzzle: "
                    }
                    steps!!.add(current.puzzle)
                    current = current.prevNode
                }
                steps!!.reverse() // steps were placed in reverse order
                steps
            } else {
                null
            }
        } else {
            // return the stored solution
            steps
        }
    }

    // create Node to represent each state of the puzzle tiles
    private class Node(
        val puzzle: Puzzle,
        val numOfMoves: Int,
        val prevNode: Node?
    ) : Comparable<Node> {

        // define the priority function as its manhattan distance plus the number of moves so far.
        private val priority: Int = puzzle.manhattanDistance() + numOfMoves

        var movement: String? = null

        override fun compareTo(other: Node): Int {
            return when {
                priority > other.priority -> 1
                priority < other.priority -> -1
                else -> numOfMoves.compareTo(other.numOfMoves)
            }
        }

        init {
            if (prevNode != null) {
                movement = prevNode.puzzle.movement(puzzle)
            }
        }
    }
}