package com.zmletters.battleship.controller

import com.zmletters.battleship.Battleship
import com.zmletters.battleship.game.{AIDifficulty, EasyAI, GameState, HardAI, NormalAI}
import javafx.fxml.FXML
import javafx.event.ActionEvent
import javafx.application.Platform

@FXML
class DifficultySelectionController:
  def handleEasyAI(actionEvent: ActionEvent): Unit =

    GameState.difficulty = new EasyAI
    Battleship.showPlayerShipPlacement()

  def handleNormalAI(actionEvent: ActionEvent): Unit =

    GameState.difficulty = new NormalAI
    Battleship.showPlayerShipPlacement()

  def handleHardAI(actionEvent: ActionEvent): Unit =

    GameState.difficulty = new HardAI
    Battleship.showPlayerShipPlacement()

  def handleBackButton(actionEvent: ActionEvent): Unit =

    val backClicked = Battleship.showMenu()