package com.zmletters.battleship.model

import scala.util.Random

// Understanding Some https://www.scala-lang.org/api/3.2.2/scala/Some.html

class Board(val size: Int):

  // Board's grid storing in array
  // https://www.baeldung.com/scala/option-type Option[] to handle null just in case
  private val grid: Array[Array[Option[Ship]]] = Array.fill(size, size)(None)
  private var _shipList: List[Ship] = List()

  // setter getter
  def shipList: List[Ship] = _shipList
  def addShip(ship: Ship): Unit =
    _shipList = ship :: _shipList

  // function to display board in cli
  def displayBoard(): Unit =
    for (row <- grid) {
      println(row.map{
        case Some(ship) =>
          if (ship.isSunk) {
            "X"
          } else {
            "S"
          }
        case None => "-"
        case _ => "-"
      }.mkString(" "))
    }

  // function to place ship
  def placeShip(ship: Ship, start: (Int, Int)): Boolean =

    // add ship to list
    this.addShip(ship)

    val positions = ship.calculatePositions(start)
    // println(positions)
    if (isPlacementValid(positions)) {
      positions.foreach { case(x, y) =>
        grid(x)(y) = Some(ship)
      }
      ship.position_=(positions)
      // return true for valid position
      true
    } else {
      // return false if it is an invalid position
      false
    }

  // function to check if the place is valid or has been placed before
  def isPlacementValid(positions: List[(Int, Int)]): Boolean =
    positions.forall {
      case (x, y) =>
        x >= 0 && x < size && y >= 0 && y < size && grid(x)(y).isEmpty
    }

  // function to attack
  def attack(x: Int, y: Int): String =
    grid(x)(y) match
      case Some(ship) =>
        if (ship.takeHit((x,y))) {
          if (ship.isSunk) {
            "Hit and Sunk"
          } else {
            "Hit"
          }
        } else {
          "Already Hit"
        }
      case None => "Miss"

  // function for AI to randomly place ship
  def randomPlaceShip(ship: Ship): Boolean =
    val random = new Random
    var placed = false

    // if not place then random direction for ship placement
    while (!placed) {
      val direction = if (random.nextBoolean()) "Right" else "Down"
      ship.direction = direction

      // random x and y
      val startX = random.nextInt(size - 1)
      val startY = random.nextInt(size - 1)

      // start position for the ship
      val start = (startX, startY)
      if (placeShip(ship, start)) {
        placed = true
      }
    }
    true

  def checkAllSunk: Boolean =
    shipList.forall(_.isSunk)

