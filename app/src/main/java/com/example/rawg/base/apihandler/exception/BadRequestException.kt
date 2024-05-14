package com.example.rawg.base.apihandler.exception

/**
 * BadRequestException is thrown when api response code is 400
 * This is handleable manually
 */
class BadRequestException(override val message: String): HandleableException()