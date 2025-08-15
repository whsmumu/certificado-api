package github.whsmumu.certificadoapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().fullyAuthenticated())

            .formLogin(formLogin -> formLogin 
                .loginPage("/login")
                .permitAll());
        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .ldapAuthentication()

                .userSearchBase("OU=TI,OU=NovoMix,DC=novomix,DC=local")
                
                .userSearchFilter("(&(sAMAccountName={0})(&(objectClass=user)(objectCategory=person)(!(userAccountControl:1.2.840.113556.1.4.803:=2))))")
                
                .groupSearchBase("OU=TI,OU=NovoMix,DC=novomix,DC=local")
                .contextSource()
                    .url("ldap://192.168.130.10:389/DC=novomix,DC=local")
                    .managerDn("appusernm@novomix.local")
                    .managerPassword("@novomix@");
    }
}
