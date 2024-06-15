package dev.mcarr.a2fauthcompose.data.exceptions

import kotlinx.serialization.Serializable

@Serializable
data class Auth2FException(
    override val message: String,
) : Exception()

//bad request
@Serializable
data class Auth2FException400(
    override val message: String
) : Exception()

//Unauthenticated
@Serializable
data class Auth2FException401(
    override val message: String
) : Exception()

//Forbidden
@Serializable
data class Auth2FException403(
    override val message: String
) : Exception()

//Not Found
@Serializable
data class Auth2FException404(
    override val message: String
) : Exception()

//Invalid data
@Serializable
data class Auth2FException422(
    override val message: String
) : Exception()

//Internal Server Error
@Serializable
data class Auth2FException500(
    override val message: String
) : Exception()