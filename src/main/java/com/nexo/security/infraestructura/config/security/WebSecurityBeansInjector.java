package com.nexo.security.infraestructura.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nexo.user.domain.repository.UserRepository;

@Configuration
public class WebSecurityBeansInjector {

    private final UserRepository userRepository;

    public WebSecurityBeansInjector(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Crea y configura el {@link AuthenticationManager} utilizando la configuración
     * proporcionada por Spring Security. El {@code AuthenticationManager} es la
     * interfaz
     * principal para la autenticación de usuarios.
     *
     * @param authenticationConfiguration Configuración de autenticación de Spring
     *                                    Security.
     * @return Una instancia del {@link AuthenticationManager}.
     * @throws Exception Si ocurre algún error al obtener el
     *                   {@code AuthenticationManager}.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Crea y configura el {@link PasswordEncoder} que se utilizará para codificar
     * las
     * contraseñas de los usuarios antes de almacenarlas y para verificar las
     * contraseñas
     * ingresadas durante la autenticación. Se utiliza
     * {@link BCryptPasswordEncoder},
     * un algoritmo de hash de contraseñas robusto y ampliamente utilizado.
     *
     * @return Una instancia del {@link BCryptPasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Crea y configura el {@link UserDetailsService} que es responsable de cargar
     * los
     * detalles de un usuario durante el proceso de autenticación. Esta
     * implementación
     * utiliza el {@link UserRepository} para buscar un usuario por su nombre de
     * usuario.
     * Si no se encuentra ningún usuario con el nombre de usuario proporcionado,
     * lanza
     * una {@link UsernameNotFoundException}.
     *
     * @return Una instancia del {@link UserDetailsService}.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            return userRepository.findByUsername(username).orElseThrow();
        };
    }

    /**
     * Crea y configura el {@link AuthenticationProvider} que se utilizará para
     * autenticar
     * a los usuarios. Utiliza un {@link DaoAuthenticationProvider}
     * que se conecta a un {@link UserDetailsService} para cargar los detalles del
     * usuario
     * y un {@link PasswordEncoder} para verificar las contraseñas.
     *
     * @return Una instancia del {@link DaoAuthenticationProvider}.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

}
