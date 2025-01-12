package com.zmletters.battleship.controller

import com.zmletters.battleship.Battleship
import com.zmletters.battleship.game.{GameLogic, GameState}
import javafx.fxml.FXML
import javafx.event.ActionEvent
import javafx.application.Platform
import javafx.scene.control.Label

@FXML
class GameOverController:

  @FXML private var resultLabel: Label = null

  def initialize(): Unit =
    resultLabel.setText(GameState.gameOverText)
    GameState.gameOverText = ""

  def handlePlayAgain(action: ActionEvent) =
    val startClicked = Battleship.showDifficultySelection()

  def handleMenu(actionEvent: ActionEvent) =
    val menuClicked = Battleship.showMenu()

  def handleQuit(action: ActionEvent) =
    Platform.exit()
