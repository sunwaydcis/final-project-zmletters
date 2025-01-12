package com.zmletters.battleship.controller

import com.zmletters.battleship.Battleship
import javafx.fxml.FXML
import javafx.event.ActionEvent
import javafx.application.Platform

@FXML
class MenuController:

  def handleStart(action: ActionEvent): Unit =
    val startClicked = Battleship.showDifficultySelection()

  def handleSettings(action: ActionEvent): Unit =
    val clicked = Battleship.showSettings()

  def handleQuit(action: ActionEvent): Unit =
    Platform.exit()