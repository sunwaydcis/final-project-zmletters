package com.zmletters.battleship

import scalafx.application.JFXApp3
import scalafx.scene as sfxs


object Battleship extends JFXApp3:

  var roots: Option[sfxs.layout.BorderPane] = None

  override def start(): Unit =
    val rootResource = ""