package com.zmletters.battleship.game

import com.zmletters.battleship.model.Board
import com.zmletters.battleship.game.AIDifficulty

object GameState:
  var difficulty: AIDifficulty = null
  var playerBoard: Board = Board(10)
  var opponentBoard: Board = Board(10)
  //var currentPlayer: String = "Player1"
