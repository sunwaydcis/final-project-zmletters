package com.zmletters.battleship.model

class Board(val size: Int):

  private val grid: Array[Array[Option[Ship]]] = Array.fill(size, size)(None)

  def displayBoard(): Unit =
    for (row <- grid) {
      println(row.map{
        case Some(ship) => "S"
        case None => "-"
        case _ => "-"
      }.mkString(" "))
    }

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

object TestBoard extends App:
  val b1 = Board(10)

  b1.displayBoard()