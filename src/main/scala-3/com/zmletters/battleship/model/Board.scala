package com.zmletters.battleship.model

import scala.util.Random

// Understanding Some https://www.scala-lang.org/api/3.2.2/scala/Some.html

class Board(val size: Int):

  // Board's grid storing in array
  // https://www.baeldung.com/scala/option-type Option[] to handle null just in case
  private val grid: Array[Array[Option[Ship]]] = Array.fill(size, size)(None)

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
    val positions = ship.calculatePositions(start)
    println(positions)
    if (isPlacementValid(positions)) {
      positions.foreach { case(x, y) =>
        grid(x)(y) = Some(ship)
      }
      ship.position_=(positions)
      true
    } else {
      false
    }

  private def isPlacementValid(positions: List[(Int, Int)]): Boolean =
    positions.forall {
      case (x, y) =>
        x >= 0 && x < size && y >= 0 && y <= size && grid(x)(y).isEmpty
    }

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

  def randomPlaceShip(ship: Ship): Boolean =
    val random = new Random
    var placed = false

    while (!placed) {
      val direction = if (random.nextBoolean()) "Right" else "Down"
      ship.direction = direction

      val startX = random.nextInt(size - 1)
      val startY = random.nextInt(size - 1)

      val start = (startX, startY)
      if (placeShip(ship, start)) {
        placed = true
      }
    }
    true

object TestBoard extends App:
  val b1 = new Board(10)
  val carrier = new Carrier
  val submarine = new Submarine
  val boat = new Boat

  boat.direction = "Down"
  submarine.direction = "Down"

  b1.placeShip(boat, (5,5))
  b1.placeShip(carrier, (0,0))
  //b1.placeShip(submarine, (7,5))
  b1.randomPlaceShip(submarine)
  b1.displayBoard()