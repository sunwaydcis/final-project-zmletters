package com.zmletters.battleship.game

import com.zmletters.battleship.model.Board
import com.zmletters.battleship.game.AIDifficulty

import scala.collection.mutable

object GameState:
  var difficulty: AIDifficulty = null
  var playerBoard: Board = Board(10)
  var opponentBoard: Board = Board(10)

  var gameOverText: String = "You Lost!"

  private var _globalVolume: Double = 0.5

  def globalVolume: Double = _globalVolume
  def globalVolume_=(newVolume: Double): Unit =
    _globalVolume = newVolume