package com.zmletters.battleship.controller

import com.zmletters.battleship.BattleshipGame
import com.zmletters.battleship.game.{AIDifficulty, GameState, HardAI, NormalAI}
import javafx.fxml.FXML
import javafx.event.ActionEvent

@FXML
class DifficultySelectionController:

  def handleNormalAI(actionEvent: ActionEvent): Unit =

    GameState.difficulty = new NormalAI
    BattleshipGame.showPlayerShipPlacement()

  def handleHardAI(actionEvent: ActionEvent): Unit =

    GameState.difficulty = new HardAI
    BattleshipGame.showPlayerShipPlacement()

  def handleBackButton(actionEvent: ActionEvent): Unit =

    val backClicked = BattleshipGame.showMenu()