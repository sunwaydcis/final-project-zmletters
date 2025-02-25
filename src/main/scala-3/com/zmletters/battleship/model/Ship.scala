package com.zmletters.battleship.model

// abstract class for Ship
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

  // function to calculate position based on the direction given a starting point
  def calculatePositions(start: (Int, Int)): List[(Int, Int)] =
    _direction match
      case "Right" => (0 until size).map(i=> (start._1, start._2 + i)).toList
      case "Down" => (0 until size).map(i=> (start._1 + i, start._2)).toList

  // function to reduce health
  def takeHit(target: (Int, Int)): Boolean =
    if (_position.contains(target)) {
      _health -= 1
      true // Hit
    } else {
      false // False
    }

  // function to check is sunk
  def isSunk: Boolean = _health <=0

  override def toString: String =
    s"Ship (name=$name, size=$size, health=$_health, position=$_position, direction=$_direction)"


// Different type of ships
class Carrier extends Ship("Carrier", 5)

class Battleship extends Ship("Battleship", 4)

class Destroyer extends Ship("Destroyer", 3)

class Submarine extends Ship("Submarine", 3)

class Boat extends Ship("Boat", 2)


