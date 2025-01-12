package com.zmletters.battleship.controller

import com.zmletters.battleship.Battleship
import com.zmletters.battleship.game.{AIDifficulty, GameState, HardAI, NormalAI}
import javafx.fxml.FXML
import javafx.event.ActionEvent

@FXML
class DifficultySelectionController:

  def handleNormalAI(actionEvent: ActionEvent): Unit =

    GameState.difficulty = new NormalAI
    Battleship.showPlayerShipPlacement()

  def handleHardAI(actionEvent: ActionEvent): Unit =

    GameState.difficulty = new HardAI
    Battleship.showPlayerShipPlacement()

  def handleBackButton(actionEvent: ActionEvent): Unit =

    val backClicked = Battleship.showMenu()