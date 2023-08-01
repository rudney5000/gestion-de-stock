package com.dedytech.gestiondestock.user

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "_user")
data class User(
    @Id
    @GeneratedValue
    var id: Int,
    var firstname: String,
    var lastname: String,
    var email: String,
    var password: String,

    @Enumerated(EnumType.STRING)
    val roles: List<Role> = listOf(Role.USER, Role.AMIN)
)
{
    fun toUserDetails(): UserDetails {
        val user = this@User
        return object:UserDetails {

            override fun getAuthorities() = user.roles.map { role ->
                SimpleGrantedAuthority(role.name)
            }.toList()

            override fun getPassword(): String {
                TODO("Not yet implemented")
            }

            override fun getUsername(): String {
                TODO("Not yet implemented")
            }

            override fun isAccountNonExpired(): Boolean {
                TODO("Not yet implemented")
            }

            override fun isAccountNonLocked(): Boolean {
                TODO("Not yet implemented")
            }

            override fun isCredentialsNonExpired(): Boolean {
                TODO("Not yet implemented")
            }

            override fun isEnabled(): Boolean {
                TODO("Not yet implemented")
            }

        }
    }
}

//):UserDetails {
//    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
//        TODO("Not yet implemented")
//        return listOf(new SimpleGrantedAuthority(role.name()))
//    }
//
//    override fun getPassword(): String {
//        TODO("Not yet implemented")
//    }
//
//    override fun getUsername(): String {
//        TODO("Not yet implemented")
//    }
//
//    override fun isAccountNonExpired(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun isAccountNonLocked(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun isCredentialsNonExpired(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun isEnabled(): Boolean {
//        TODO("Not yet implemented")
//    }
//}
