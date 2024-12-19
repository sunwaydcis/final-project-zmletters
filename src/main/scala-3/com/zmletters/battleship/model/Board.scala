package com.zmletters.battleship.model

class Board(val size: Int):

  private val grid: Array[Array[Option[String]]] = Array.fill(size, size)(None)

  def displayBoard(): Unit =
    for (row <- grid) {
      println(row.map{
        case Some("S") => "S"
        case Some("H") => "H"
        case Some("M") => "M"
        case None => "-"
        case _ => "-"
      }.mkString(" "))
    }


object TestBoard extends App:
  val b1 = Board(10)

  b1.displayBoard()