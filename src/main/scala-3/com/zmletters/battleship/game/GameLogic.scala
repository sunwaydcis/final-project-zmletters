package com.zmletters.battleship.game

import com.zmletters.battleship.model.Board

import scala.util.Random

class GameLogic(board1: Board, board2: Board, ai: AIDifficulty):

  private var currentPlayer: String = "Player"

  def playerAttack(x: Int, y: Int): String =
    val result = board2.attack(x, y)
    if (board2.checkAllSunk) {
      s"Player wins!"
    } else {
      currentPlayer = "AI"
      result
    }

  def aiAttack: (Int, Int, String) =

    val (x, y, attackResult) = ai.aiAttack(board1)

    if (board1.checkAllSunk) {
      (x, y, "Enemy wins!")
    } else {
      currentPlayer = "Player"
      (x, y, attackResult)
    }

  def isGameOver: Boolean =
    board1.checkAllSunk || board2.checkAllSunk

  def board1Sunk: Boolean =
    board1.checkAllSunk

  def board2Sunk: Boolean =
    board2.checkAllSunk