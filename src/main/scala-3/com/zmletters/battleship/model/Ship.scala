package com.zmletters.battleship.model

abstract class Ship(val name: String, val size: Int):

  private var _position: List[(Int, Int)] = List()
  private var _health: Int = size
  private var _image: String = ""
  private var _direction: String = "Right" // Default direction is right
  private var _isPlaced: Boolean = false

  // setter & getter
  def position: List[(Int, Int)] = _position
  def position_=(newPosition: List[(Int, Int)]): Unit =
    _position = newPosition
    
  def health: Int = _health
  def health_=(health: Int): Unit =
    _health = health

  def direction: String = _direction
  def direction_=(newDirection: String): Unit = _direction = newDirection

  def isPlaced: Boolean = _isPlaced
  def isPlaced_=(placed: Boolean): Unit = _isPlaced = placed

  def calculatePositions(start: (Int, Int)): List[(Int, Int)] =
    _direction match
      case "Right" => (0 until size).map(i=> (start._1, start._2 + i)).toList
      case "Down" => (0 until size).map(i=> (start._1 + i, start._2)).toList

  def takeHit(target: (Int, Int)): Boolean =
    if (_position.contains(target)) {
      _health -= 1
      true // Hit
    } else {
      false // False
    }

  def isSunk: Boolean = _health <=0

  override def toString: String =
    s"Ship (name=$name, size=$size, health=$_health, position=$_position, direction=$_direction)"

class Carrier extends Ship("Carrier", 5)

class Battleship extends Ship("Battleship", 4)

class Destroyer extends Ship("Destroyer", 3)

class Submarine extends Ship("Submarine", 3)

class Boat extends Ship("Boat", 2)



object ShipText extends App:
  val destroyer = new Carrier

  println("place ship")
//  destroyer.place(List((0, 0), (0, 1), (0, 2)))
//
//  println(destroyer)
//
//  println(s"Hit (0, 1): ${destroyer.takeHit((0,1))}")