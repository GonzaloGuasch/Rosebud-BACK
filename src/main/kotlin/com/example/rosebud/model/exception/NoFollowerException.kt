package com.example.rosebud.model.exception

import java.lang.RuntimeException

class NoFollowerException(errorMessage: String): RuntimeException(errorMessage)
