package com.zmletters.battleship.controller

import com.zmletters.battleship.Battleship
import com.zmletters.battleship.game.{AIDifficulty, EasyAI, GameState, HardAI, NormalAI}
import javafx.fxml.FXML
import javafx.event.ActionEvent
import javafx.application.Platform
import javafx.scene.control.Slider
import scalafx.scene.media.MediaPlayer

@FXML
class SettingsController:

  private var mediaPlayer: MediaPlayer = null

  @FXML private var volumeSlider: Slider = null

  def setMediaPlayer(mediaPlayer: MediaPlayer): Unit =
    this.mediaPlayer = mediaPlayer
    if (volumeSlider != null) {
      volumeSlider.setValue(GameState.globalVolume * 100)

      // Listener for the slider to adjust volume
      volumeSlider.valueProperty().addListener((_, _, newValue) => {
        val volume = newValue.doubleValue() / 100.0
        GameState.globalVolume = volume
        this.mediaPlayer.setVolume(volume)
        println(s"Volume set to: $volume")
      })
    }

  def handleDoneButton(actionEvent: ActionEvent): Unit =
    GameState.globalVolume = volumeSlider.getValue/100
    val clicked = Battleship.showMenu()

  def handleBackButton(actionEvent: ActionEvent): Unit =
    val backClicked = Battleship.showMenu()