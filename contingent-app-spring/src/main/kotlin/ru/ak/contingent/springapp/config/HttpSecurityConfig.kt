package ru.ak.contingent.springapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity(debug = true)
@Configuration
class HttpSecurityConfig {

    @Bean
    fun users(): UserDetailsService {
        fun user(user: String, password: String, vararg roles: String) =
            User.withDefaultPasswordEncoder().username(user).password(password).roles(*roles).build()

        return InMemoryUserDetailsManager(
            user("user", "password", "USER"),
            user("admin", "password", "USER", "ADMIN"),
        )
    }

    @Bean
    fun apiFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
//            authorizeRequests {
//                authorize("/student/read", hasAnyAuthority("ROLE_USER", "ROLE_ADMIN"))
//                authorize("/student/search", hasAnyAuthority("ROLE_USER", "ROLE_ADMIN"))
//                authorize("/student/**", hasAuthority("ROLE_ADMIN"))
//                authorize("/**", permitAll)
//            }
            httpBasic {}
        }
        return http.build()
    }
}