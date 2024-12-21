package com.zmletters.battleship.model



abstract class Ship(val name: String, val size: Int):

  private var _position: List[(Int, Int)] = List()
  private var _health: Int = size
  private var _image: String = ""

  // setter & getter
  def position: List[(Int, Int)] = _position
  def position_=(newPosition: List[(Int, Int)]): Unit =
    _position = newPosition
    
  def health: Int = _health
  def health_=(health: Int): Unit =
    _health = health

  def place(position: List[(Int, Int)]): Boolean =
    if (position.length == size) {
      this._position = position
      true
    } else {
      false
    }

  def takeHit(target: (Int, Int)): Boolean =
    if (_position.contains(target)) {
      _health -= 1
      true // Hit
    } else {
      false // False
    }

  def isSunk: Boolean = _health <=0

  override def toString: String =
    s"Ship (name=$name, size=$size, health=$_health, position=$_position)"

class Carrier extends Ship("Carrier", 5)

class Submarine extends Ship("Submarine", 3)


object ShipText extends App:
  val destroyer = new Carrier

  println("place ship")
  destroyer.place(List((0, 0), (0, 1), (0, 2)))

  println(destroyer)

  println(s"Hit (0, 1): ${destroyer.takeHit((0,1))}")