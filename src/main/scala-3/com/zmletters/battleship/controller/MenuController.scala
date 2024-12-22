package com.zmletters.battleship.controller

import com.zmletters.battleship.Battleship
import javafx.fxml.FXML
import javafx.event.ActionEvent
import javafx.application.Platform

@FXML
class MenuController:
  def handleStart(action: ActionEvent) =
    val startClicked = Battleship.showBoard()
  def handleQuit(action: ActionEvent) =
    Platform.exit()
//    System.exit(0)