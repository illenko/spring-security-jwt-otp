package com.illenko.gateway.config.security.token

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class OtpAuthentication : UsernamePasswordAuthenticationToken {
    constructor(principal: Any, credentials: Any) : super(principal, credentials)
    constructor(principal: Any, credentials: Any, authorities: Collection<GrantedAuthority>) : super(principal, credentials, authorities)
}