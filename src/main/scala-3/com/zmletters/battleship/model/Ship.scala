package com.zmletters.battleship.model



class Ship(val name: String, val size: Int):

  var position: List[(Int, Int)] = List()
  private var health: Int = size

  def place(position: List[(Int, Int)]): Boolean =
    if (position.length == size) {
      this.position = position
      true
    } else {
      false
    }

  def takeHit(target: (Int, Int)): Boolean =
    if (position.contains(target)) {
      health -= 1
      true // Hit
    } else {
      false // False
    }

  def isSunk: Boolean = health <=0

  override def toString: String =
    s"Ship (name=$name, size=$size, health=$health, position=$position)"

class Carrier extends Ship("Carrier", 5)

class Submarine extends Ship("Submarine", 5)


object ShipText extends App:
  val destroyer = new Ship("Destroyer", 3)

  println("place ship")
  destroyer.place(List((0, 0), (0, 1), (0, 2)))

  println(destroyer)

  println(s"Hit (0, 1): ${destroyer.takeHit((0,1))}")