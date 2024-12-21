package com.zmletters.battleship.controller

import com.zmletters.battleship.model.Board

import scala.util.Random

class GameController(board1: Board, board2: Board):

  private var currentPlayer: String = "Player"

  def playerAttack(x: Int, y: Int): String =
    val result = board2.attack(x, y)
    if (board2.checkAllSunk) {
      "Player 1 wins!"
    } else {
      currentPlayer = "AI"
      result
    }

  def aiAttack: (Int, Int, String) =
    val random = new Random
    var attackResult = ""
    var x, y = 0

    while
      attackResult == "Already Hit"
    do
      x = random.nextInt(board1.size)
      y = random.nextInt(board1.size)
      attackResult = board1.attack(x, y)

    if (board1.checkAllSunk) {
      (x, y, "Player 2 wins!")
    } else {
      currentPlayer = "Player 1"
      (x, y, attackResult)
    }

  def isGameOver: Boolean =
    board1.checkAllSunk || board2.checkAllSunk