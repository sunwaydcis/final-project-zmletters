package com.zmletters.battleship.game

import com.zmletters.battleship.model.Board

import scala.util.Random

trait AIDifficulty:
  def aiAttack(playerBoard: Board): (Int, Int, String)


class NormalAI extends AIDifficulty:
  private val rand = new Random
  private val visited = scala.collection.mutable.Set[(Int, Int)]()

  def aiAttack(playerBoard: Board): (Int, Int, String) =
    var attackResult = ""
    var x, y = 0

    // Avoid repeating cells
    while attackResult == "" || attackResult == "Already Hit" do
      x = rand.nextInt(playerBoard.size)
      y = rand.nextInt(playerBoard.size)

      if !visited.contains((x, y)) then
        visited.add((x, y))
        attackResult = playerBoard.attack(x, y)

    (x, y, attackResult)

// reference : https://www.geeksforgeeks.org/play-battleships-game-with-ai/#2-hunt-and-target-method
class HardAI extends AIDifficulty:
  private val rand = new Random

  // Phase: "hunt" or "target"
  private var phase: String = "hunt"

  // A queue or stack of cells to target after finding a hit
  private val targetQueue = scala.collection.mutable.Queue[(Int, Int)]()

  // Track visited cells
  private val visited = scala.collection.mutable.Set[(Int, Int)]()

  def aiAttack(playerBoard: Board): (Int, Int, String) =
    var x, y = -1
    var attackResult = ""

    if phase == "hunt" then
      val huntCell = getHuntCell(playerBoard.size) // Get hunt cell
      x = huntCell._1
      y = huntCell._2
      attackResult = playerBoard.attack(x, y)

      // If "Hit", switch to "target" phase and add adjacent cells
      if attackResult == "Hit" || attackResult == "Hit and Sunk" then
        phase = "target"
        addAdjacentCells(x, y, playerBoard.size)
    else
      // "target" mode
      if targetQueue.nonEmpty then
        val targetCell = targetQueue.dequeue()
        x = targetCell._1
        y = targetCell._2
        attackResult = playerBoard.attack(x, y)

        if attackResult.startsWith("Hit") then
          // Add more adjacent cells
          addAdjacentCells(x, y, playerBoard.size)

        // If no more hits, go back to hunt if queue is empty or you sunk a ship
        if attackResult == "Miss" || attackResult == "Hit and Sunk" then
          if targetQueue.isEmpty then
            phase = "hunt"
      else
        // If queue is empty, revert to hunt
        phase = "hunt"
        return aiAttack(playerBoard)

    (x, y, attackResult)

  // Generate coordinates in a checkerboard pattern or any pattern that systematically avoids repeats, then randomly pick one.
  private def getHuntCell(boardSize: Int): (Int, Int) =
    var nx, ny = 0
    var found = false

    while !found do
      nx = rand.nextInt(boardSize)
      ny = rand.nextInt(boardSize)
      if !visited.contains((nx, ny)) then
        visited.add((nx, ny))
        found = true
    (nx, ny)

  // Add adjacent cells of a hit to the target queue.
  private def addAdjacentCells(x: Int, y: Int, boardSize: Int): Unit =
    val neighbors = List((x-1, y), (x+1, y), (x, y-1), (x, y+1))
    neighbors.foreach { case (nx, ny) =>
      if nx >= 0 && nx < boardSize && ny >= 0 && ny < boardSize &&
        !visited.contains((nx, ny))
      then
        visited.add((nx, ny))
        targetQueue.enqueue((nx, ny))
    }