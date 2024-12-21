package com.zmletters.battleship.model

// Understanding Some https://www.scala-lang.org/api/3.2.2/scala/Some.html

class Board(val size: Int):

  // Board's grid storing in array
  // https://www.baeldung.com/scala/option-type Option[] to handle null just in case
  private val grid: Array[Array[Option[Ship]]] = Array.fill(size, size)(None)

  // function to display board in cli
  def displayBoard(): Unit =
    for (row <- grid) {
      println(row.map{
        case Some(ship) => "S"
        case None => "-"
        case _ => "-"
      }.mkString(" "))
    }

  // function to place ship
  def placeShip(ship: Ship, positions: List[(Int, Int)]): Boolean =

    if (isPlacementValid(positions)) {
      positions.foreach { case(x, y) =>
        grid(x)(y) = Some(ship)
      }
      ship.place(positions)
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

object TestBoard extends App:
  val b1 = new Board(10)
  val carrier = new Carrier
  val submarine = new Submarine

  b1.displayBoard()

  b1.placeShip(carrier, List((0,0),(0,1),(0,2),(0,3),(0,4)))
  b1.placeShip(submarine, List((1,0),(1,1),(1,2)))

  b1.displayBoard()

  println(b1.attack(0,1))
  b1.displayBoard()