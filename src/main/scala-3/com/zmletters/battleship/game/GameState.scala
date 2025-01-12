package com.zmletters.battleship.game

import com.zmletters.battleship.model.Board
import com.zmletters.battleship.game.AIDifficulty

import scala.collection.mutable

object GameState:
  var gameOverText: String = "You Lost!"

  private var _globalVolume: Double = 0.5
  private var _difficulty: AIDifficulty = null
  private var _playerBoard: Board = Board(10)
  private var _opponentBoard: Board = Board(10)

  def globalVolume: Double = _globalVolume
  def globalVolume_=(newVolume: Double): Unit =
    _globalVolume = newVolume

  def difficulty: AIDifficulty = _difficulty
  def difficulty_=(newDifficulty: AIDifficulty): Unit =
    _difficulty = newDifficulty

  def playerBoard: Board = _playerBoard
  def playerBoard_=(newBoard: Board): Unit =
    _playerBoard = newBoard

  def opponentBoard: Board = _opponentBoard
  def opponentBoard_=(newBoard: Board): Unit =
    _opponentBoard = newBoard