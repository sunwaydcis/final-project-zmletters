package com.zmletters.battleship.controller

import com.zmletters.battleship.BattleshipGame
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
    val startClicked = BattleshipGame.showDifficultySelection()

  def handleMenu(actionEvent: ActionEvent) =
    val menuClicked = BattleshipGame.showMenu()

  def handleQuit(action: ActionEvent) =
    Platform.exit()
