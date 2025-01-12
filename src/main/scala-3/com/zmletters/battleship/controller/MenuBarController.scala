package com.zmletters.battleship.controller

import com.zmletters.battleship.BattleshipGame.mediaPlayer
import com.zmletters.battleship.game.GameState

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.stage.Stage
import javafx.scene.control.MenuItem
import scalafx.scene.media.MediaPlayer

class MenuBarController:

  @FXML private var quitMenuItem: javafx.scene.control.MenuItem = _
  @FXML private var muteItem: MenuItem = _

  def handleQuitMenuItem(event: ActionEvent): Unit =
    // Close the application
    val stage = quitMenuItem.getParentPopup.getOwnerWindow.asInstanceOf[Stage]
    stage.close()

  def handleMute(event: ActionEvent): Unit =
    GameState.globalVolume = 0
    mediaPlayer.setVolume(0)
