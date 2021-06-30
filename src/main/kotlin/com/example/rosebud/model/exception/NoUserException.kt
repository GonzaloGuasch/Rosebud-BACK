package com.example.rosebud.model.exception

import java.lang.RuntimeException

class NoUserException(errorMessage: String) : RuntimeException(errorMessage)