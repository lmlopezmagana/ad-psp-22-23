# 22 Seguridad con JWT: Ejemplo de implementación de la seguridad Json Web Token con Spring Boot

## 1. Json Web Token

**¿Qué es un JSON Web Token?**

JWT es un estándar RFC 7519 para transmitir información con la identidad y _claims_ de un usuario de forma segura entre un cliente/servidor. Dicha información puede ser verificada y confiable porque está firmada digitalmente.

Como siempre, estas definiciones, cuando hablamos de conceptos técnicos son muy ambiguas y confunden más que ayuda a entenderlo.

A mi me gusta ir directo al grano, un JWT es "simplemente" una cadena de texto que tiene 3 partes codificadas en Base64, separadas por un punto (header.payload.firma) que generamos y entregamos a los clientes de nuestra API:

![Estructura de un token jwt](jwt_estructura.jpg)

> La codificación **Base64** se llama así porque genera una cadena ascii para representar cualquier dato binario, usando para ello un alfabeto de 64 caracteres. La versión URL elimina del alfabeto los caracteres “=”, “/” y “+”.

Es importante aclarar que la cadena/token esta codificado y lo crea nuestra aplicación, esto nos permite de manera muy fácil inspeccionar su contenido, por ejemplo con: [JWT Debugger](https://jwt.io/).

Por ejemplo, si usamos el siguiente token:

```jwt
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9. eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9. TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ
```

en JWT Debugger, obtenemos el siguiente resultado:

![Resultado de depurar un token con JWT Debugger](jwt-debugger.jpg)

**Pero, puedo ver el contenido. ¿Esto es seguro?**

Esta es la pregunta que todos nos hacemos la primera vez, hemos dicho que un JWT tiene 3 partes y esta codificado en Base64. Vamos a verlo:

- `HEADER`: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9`: Indica el algoritmo y tipo de Token, en nuestro caso: `HS256` y `JWT`.
- `PAYLOAD`: `eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9`: datos de usuario/_claims_, fecha creación/caducidad Token y lo que necesite nuestra API para validar la petición. El estándar define unos cuantos posibles:
    - `sub`: identificador del usuario
    - `iat`: timestamp del momento de creación.
    - `exp`: timestamp en segundos a partir del cual el token dejará de ser válido. Es importante, desde el punto de vista de la seguridad, generar tokens con caducidad.
> En el payload podemos incluir campos personalizados, aquellos que usemos frecuentemente y nos ahorren peticiones contra la base de datos por ejemplo. Uno típico podría ser el nivel de privilegios que tiene el usuario. La “necesidad” es usar https es obvia, ya que el payload (datos sensibles), está simplemente codificado en base64 y cualquiera que intercepte el token podrá ver su contenido.
- `SIGNATURE`: `TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ` (la firma, para verificar que el token es válido, aqui lo importante es el **"secret"** con el que firmamos y que ahora explicaremos).

Asi que la respuesta es: "Sí, es totalmente seguro", porque como vemos en la imagen anterior, se indica **"Signature Verified"**: esto quiere decir que la firma se usa para verificar que el remitente del JWT es quien dice ser y para asegurarse de que el mensaje no haya cambiado en el camino.

Expliquemos con algo más de detalle la firma. Como decíamos al principio, *"la información puede ser verificada y confiable porque está firmada digitalmente"*, con un `secret-key`. Lo importante aquí es el `secret-key` para generar la firma del token, por supuesto, no hace falta decir que nuestro `secret-key` nunca se lo daremos a nadie.

Veamos el ejemplo en el JWT Debugger cambiando "`secret`" por "`otro-secret`" en el editor y pasará lo que se muestra en la imagen.

![Resultado del cambio del "secret" en la firma](./jwt-debugger2.jpg)

En este caso, al cambiar el "secret-key", vemos que la firma no es válida "Invalid Signature". Esto quiere decir que no podemos confiar en el Token JWT, porque que alguien lo puede haber firmado de forma maliciosa o cambiado algo del payload.

Por supuesto, será responsibilidad de nuestra aplicación cuando recibamos un Token en nuestra API, verificarlo con nuestro "secret-key", garantizar que la firma es válida para aceptarlo o denegarlo y eso lo tenemos que programar o usar alguna libreria ya disponible.

A nivel de algoritmos, podemos utilizar los siguientes con JWT:


- `HS256`: El el algoritmo usado por defecto, que consiste en un cifrado de clave simétrica HMAC (necesita una clave o SECRET para realizar el cifrado) con el algoritmo de hash SHA-256 (que produce una salida de 256 bits).
- `HS512`: cifrado de clave simétrica HMAC (necesita una clave o SECRET para realizar el cifrado) con el algoritmo de hash SHA-512 (que produce una salida de 512 bits).
- `RS256`: cifrado de clave simétrica RSASSA-PKCS1-v1_5 con el algoritmo de hash SHA-256. NOTA: Los cifrados asimétricos son interesantes en aplicaciones desacopladas, donde podemos tener la clave privada en el servidor y la clave pública en el cliente, por ejemplo.
- `RS512`: cifrado de clave asimétrica RSASSA-PKCS1-v1_5 con el algoritmo de hash SHA-512.
- `PS256`: cifrado de clave asimétrica RSASSA-PSS con el algoritmo de hash SHA-256. Este es el reemplazo de RSA-PKCS
- `PS512`: cifrado de clave asimétrica RSASSA-PSS con el algoritmo de hash SHA-512.
- `ES256`: cifrado de clave asimétrica ECDSA con el algoritmo de hash SHA-256. ECDSA utiliza claves más pequeñas y es más eficiente que RSA, Actualmente es el algoritmo que usan las criptomonedas Bitcoin y Ethereum, por ejemplo.
- `ES512`: cifrado de clave asimétrica ECDSA con el algoritmo de hash SHA-512.

> SHA significa **Secure Hash Algorithm** y a día de hoy SHA256 y SHA512 (Ambos de la familia SHA-2), siguen sin ser quebrantados, a diferencia de SHA-1. Además ya existe SHA-3, que sigue un enfoque diferente a la anterior versión y que aún no se sabe si es mejor ni si será su reemplazo en el futuro.

Si queremos información más técnica y detallada, leer esto: https://jwt.io/introduction


### 1.1 Ciclo de vida de un Token

Ahora que ya sabemos qué es JWT, vamos a dar un paso más allá y a conocer como funciona el proceso completo con un diagrama de uso:

![Ciclo de vida de un token JWT](jwt-workflow.png)

El proceso completo del JWT consta de estos pasos:

1. El usuario de una aplicacion web/móvil/desktop hace login con sus credenciales en el servidor donde esta publicada el API.
2. El usuario es validado en el servidor y se crea un nuevo Token JWT (usando nuestro "secret-key") para entregárselo al usuario.
3. El servidor retorna el JWT firmado que contiene los datos/claims referentes al usuario y caducidad del Token.
4. El cliente/browser almacena el JWT para su uso y lo envia en cada petición mediante "Authorization: Bearer ".
5. El servidor verifica la firma del Token, su caducidad y comprueba si usuario tiene permisos al recurso leyendo los datos del payload.
6. El servidor responde al cliente la petición una vez ha confirmado el Token y los permisos del usuario son correctos.

Comentarios sobre el ciclo de vida del JWT:

- `"Authorization: Bearer "`, es la forma más común, indicar que existen otras técnicas para hacerlo.
- JWT es muy ligero: podemos codificar gran cantidad de datos sensibles en el payload y pasarlo como una cadena.
- Creamos servicios de autenticación optimizados desacoplados del servidor y tenemos protección contra ataques CSRF.
- Nos ahorramos mantener el estado del usuario en el servidor y lo delegamos al cliente.
- Recordar que siempre, siempre, siempre debemos usar HTTPS entre el cliente/servidor para las peticiones.
- Y lo más importante: ¡Nos olvidamos de cookies!

> Los ataques CSRF realizan peticiones falseadas desde un servidor a otro de forma oculta dentro del html, que ha sido manipulado para ello. De modo que no es necesario robar la cookie para poder llevar a cabo acciones sobre el backend usando los privilegios del propietario de la misma.

Observando el ciclo de vida de JWT, vemos que la ventaja fundamental de este modelo de seguridad, es que, en lugar de almacenar información relacionada con la autorización vinculada a cada usuario en sesión del servidor, se almacena una sola clave de firma ("secret-key") en el servidor que sirve para crear los Tokens.

### 1.2 Token de refresco

Adicionalmente al uso de tokens de autenticación, podemos usar un segundo token, cuya fecha de expiración sea mayor. Este token sirve para que, una vez caducado el token de autenticación, podamos obtener uno nuevo, sin tener que recurrir a realizar la petición de login. El token de refresco no debe de poder utilizarse para otro tipo de peticiones.


## 2. Implementación con Spring Security

Como sabemos, Spring Security es un framework de seguridad para aplicaciones Java que nos permite implementar las dos grandes operaciones relacionadas con la seguridad web: la autenticación y la autorización.

- La autenticación permite que un usuario pueda demostrar quién es.
- La autorización permite que, una vez identificado quién es el usuario, podamos determinar qué puede hacer y qué no.

### 2.1 Filtros

Spring Security no implementa por defecto la seguridad JWT, y por tanto la tenemos que implementar a través de un **filtro**. Los filtros vienen desde el _mundo_ Java. Son clases que nos permiten interceptar una petición y modificarla antes de que sea procesada por el Servlet/Controlador, o interceptar una respuesta antes que que la misma sea enviada al cliente. Para crear estas clases es necesario implementar la interface `javax.servlet.Filter` (o alguno de sus derivados), la cual define los siguientes tres métodos que debemos implementar:

- `init(cfg: FilterConfig)`. Es invocado una vez que el filtro entra en funcionamiento.
- `destroy()`. Este método se llama cuando el filtro deja de funcionar.
- `doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain)`. Se llama a este método para cada petición correlacionada con este filtro antes de invocar el Servlet/Controlador. El Servlet/Controlador original solicitado se ejecuta cuando el filtro llama al método `chain.doFilter(...)`.

![Filtro Java](./java-filter.gif)

### 2.2 Filtros de Spring Security

La Infraestructura Web de Spring Security esta basada enteramente en Filtros. Spring Security mantiene una cadena de filtros internamente, donde cada uno de estos filtros cumple un responsabilidad particular y los filtros son agregados o borrados de la configuración de acuerdo  a los servicios requeridos, el orden de los filtros es importante pues hay dependencias entre ellos.

Uno de los filtros configurados por defecto en esta cadena es `UsernamePasswordAuthenticationFilter`. Se encarga de procesar las peticiones de autenticación desde un formulario con usuario y contraseña. A la hora de implementar la seguridad con los filtros de seguridad para JWT, colocaremos nuestro filtro JWT antes de este `UsernamePasswordAuthenticationFilter`, de forma que no requiera de un usuario y contraseña para poder autenticarse. Veremos como hacerlo en la clase que configura la seguridad.

## 3. Arquitectura de la seguridad JWT con Spring Boot y Spring Security.

Para implementar la seguridad JWT con Spring Security vamos a comenzar creando la clase que gestione la seguridad, que como siempre, extenderá a `WebSecurityConfigurerAdapter` e irá anotada con `@EnableWebSecurity`.

```java
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

}

```

Ahora, conforme vayamos sobrescribiendo los métodos de nuestra clase base, vamos a ir viendo otras clases o beans que tenemos que implementar.

### 3.1 Clase para modelar a un usuario

La primera que se nos viene a la mente es nuestra clase usuario. Debe ser una clase que implemente la intefaz `UserDetails` definida en Spring Security, para hacerla compatible con el resto del framework de autenticación:

```java
@Entity
@Table(name="users")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements UserDetails {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NaturalId
    @Column(unique = true, updatable = false)
    private String email;

    private String password;

    private String avatar;

    private String fullName;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder.Default
    private LocalDateTime lastPasswordChangeAt = LocalDateTime.now();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }


    /**
     * No vamos a gestionar la expiración de cuentas. De hacerse, se tendría que dar
     * cuerpo a este método
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * No vamos a gestionar el bloqueo de cuentas. De hacerse, se tendría que dar
     * cuerpo a este método
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * No vamos a gestionar la expiración de cuentas. De hacerse, se tendría que dar
     * cuerpo a este método
     */

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /**
     * No vamos a gestionar el bloqueo de cuentas. De hacerse, se tendría que dar
     * cuerpo a este método
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}

```

> En esta implementación se ha decidido usar el tipo `UUID` como identificador, aunque se podría sustituir por `Long`. Puedes visitar el siguiente enlace para tener más información sobre UUID: [https://www.baeldung.com/java-uuid](https://www.baeldung.com/java-uuid)



Acompañanado a esta entidad, tendremos el correspondiente repositorio y servicio:

```java
public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findFirstByEmail(String email);

}

@Service
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> {


}

```

### 3.2 `UserDetailsService`

Spring Security nos permite definir diferentes mecanismos de autenticación:

- Almacenando los datos en memoria
- En un servidor ldap, como el _Active Directory_ de Windows
- En una base de datos que se accederá a través de JDBC
- Definiendo un servicio que nos devuelva, a través de _algún mecanismo_, una instancia de `UserDetails`.

Nosotros escogemos el último mecanismo, más adecuado para integrar con Spring Data JPA.

Necesitamos definir entonces una clase que extienda a `UserDetailsService` e implemente el único método que define esta interfaz. 

> En nuestro caso, como `UserDetailsService` es simplemente un DAO de usuarios, añadiremos la implementación de la interfaz al servicio de usuario ya definido.

```java
@Service("userDetailsService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repositorio.findFirstByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email + " no encontrado"));
    }

}

```

### 3.3 Retomamos nuestra clase `SecurityConfig`

Una vez definidas algunas de las clases anterior, podemos comenzar a dar cuerpo a algunos métodos de la clase de configuración de seguridad.

Antes, tenemos que definir el mecanismo mediante el cuál vamos a cifrar nuestra contraseña, para que no se guarde en la base de datos como texto plano. Para ello, creamos un bean de tipo `PasswordEncoder`, usando el algoritmo _bcrypt_.

```java
@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
```

Una vez que lo hemos definido, podemos implementar algunos métodos de `SecurityConfig`: 


```java
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

```

> Nótese que estamos inyectando los beans de tipo `UserDetailsService` y `PasswordEncoder` a través del constructor.

El primer método define cuál va a ser el esquema de autenticación (a través de nuestro `UserDetailsService`), y el segundo expone el esquema de autenticación como un bean, para poder realizar dicha autenticación programáticamente.

### 3.4 Método que implementa la autorización

En la clase  `SecurityConfig` nos falta sobrescribir un método fundamental, `protected void configure(HttpSecurity http) throws Exception`, que sirve para definir la autorización y terminar de configurar la seguridad. Vamos a ir implementándolo poco a poco, ya que requerirá de que demos implementación a algunos beans más.

```java
@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                    .exceptionHandling()
                        .authenticationEntryPoint(...)
                    .accessDeniedHandler(...)
                .and()
                    .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    ...

        http.addFilterBefore(..., UsernamePasswordAuthenticationFilter.class);

        // Para dar acceso a h2
        http.headers().frameOptions().disable();

    }

```

Podemos observar lo siguiente:

- Se deshabilita la seguridad csrf. Es un problema normalmente asociado al uso de sesiones, en nuestro caso no las vamos a usar, ya que la sesión reside en el token que tendrá el cliente.
- Tenemos que definir un bean para setear como _authenticationEntryPoint_ y otro para al _accessDeniedHandler_.
- Indicamos que la política de sesiones es no crear sesiones.
- Autorizamos determinadas peticiones que más adelante trabajaremos
- Vamos a necesitar añadir un filtro, que se encargará de la autenticación basada en token.
- Configuramos lo necesario para poder acceder a la consola de h2

Por tanto, por ahora vamos a necesitar definir:

- Un _Authentication Entry Point_ y un _Access Denied Handler_
- Un filtro para la autenticación JWT.


### 3.5 `AuthenticationEntryPoint` y `AccessDeniedHandler`

Según la documentación de Spring, las clases que implementan la interfaz `AuthenticationEntryPoint` son las que definen el reino de seguridad y son invocadas cuando hay algún problema de autenticación. Y las que implementan la interfaz `AccessDeniedHandler` son las que se encargan de manejar los errores de acceso denegado (un usuario conocido, válido, que trata de acceder a un recurso para el que no tiene privilegios).

En nuestro caso, vamos a realizar una implementación simple, que devuelve JSON sencillo con un código de respuesta 401/403 cuando haya algún problema de autenticación. Nos basaremos en la interfaz `AuthenticationEntryPoint` y `AccessDeniedHandler`:

```java
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;


    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType("application/json");

        Map<String, String> mensajes =
                Map.of("mensaje", e.getMessage());

        String strjson = mapper.writeValueAsString(mensajes);

        httpServletResponse.getWriter().println(strjson);



    }
}

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper;


    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        httpServletResponse.setContentType("application/json");

        Map<String, String> mensajes =
                Map.of("mensaje", e.getMessage());

        String strjson = mapper.writeValueAsString(mensajes);

        httpServletResponse.getWriter().println(strjson);
    }
}
```

En caso de error en la autenticación, devolvemos un JSON sencillo con el mensaje de la excepción, y código de respuesta 401/403.



## 4. Código necesario para usar JWT

En el apartado anterior nos hemos quedado pendientes, para culminar con la seguridad, de implementar un filtro que realice la autenticación con JWT. Para ello, necesitaremos implementar algunas clases más, a la sazón:

- Un componente que nos permita trabajar con tokens, pudiendo _fabricarlos_ o _verificarlos_
- El filtro en sí
- Un controlador que permita realizar las peticiones de autenticación y refresco de token
- Algunas clases DTO adicionales para las operaciones anteriores.

Spring no provee de ninguna clase _nativa_ para trabajar con tokens JWT, por lo que necesitamos añadir alguna dependencia auxiliar, como:

```xml
<dependency>
	<groupId>io.jsonwebtoken</groupId>
	<artifactId>jjwt-api</artifactId>
	<version>0.11.1</version>
</dependency>
<dependency>
	<groupId>io.jsonwebtoken</groupId>
	<artifactId>jjwt-impl</artifactId>
	<version>0.11.1</version>
	<scope>runtime</scope>
</dependency>
<dependency>
	<groupId>io.jsonwebtoken</groupId>
	<artifactId>jjwt-jackson</artifactId>
	<version>0.11.1</version>
	<scope>runtime</scope>
</dependency>
```

> En mvnrepository puedes encontrar fácilmente estas dependencias para gradle, si es que estás trabajando con él.


### 4.1 Proveedor de tokens

La primera clase que vamos a implementar tendrá muchos métodos diferentes. La vamos a definir como un componente, aunque posiblemente podría haber estar anotada como servicio (o al menos, con algún conjunto de sus métodos)


```java
@Service
public class JwtProvider {

    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    @Value("${jwt.secret:elsecretoibericoeselquemasmegusta}")
    private String jwtSecret;

    @Value("${jwt.duration:86400}") // 1 día
    private int jwtLifeInSeconds;

}
```

En primer lugar, definimos algunos valores que van ser necesarios:

- Una serie de constantes que usaremos a lo largo de la clase
- Valores típicos al trabajar con JWT, como la duración de los tokens o el secreto.

> Para estos es bueno utilizar la anotación `@Value` y definirlos como propiedades o, mejor aun, variables de entorno (con valores por defecto, para facilitar la configuración y ejecución del proyecto)

#### 4.1.1 Cómo generar el token

Para generar el token, vamos a implementar un método, que reciba los datos del usuario, y que nos devuelva un `String` con el token.

```java
public String generateToken(Authentication authentication) {

        UserEntity user = (UserEntity) authentication.getPrincipal();

        Date tokenExpirationDate = Date
                .from(LocalDateTime
                        .now()
                        .plusSeconds(jwtLifeInSeconds)
                        .atZone(ZoneId.systemDefault()).toInstant());


        return Jwts.builder()
                .setHeaderParam("typ", TOKEN_TYPE)
                .setSubject(user.getId().toString())
                .setIssuedAt(tokenExpirationDate)
                .claim("fullname", user.getFullName())
                .claim("role", user.getRole().name())
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();


    }
```

- El método recibe los datos del usuario (en forma de un Authentication) para el que va a generar el token.
- Calculamos la fecha de experiación del token en función del tipo del mismo y su duración
- Indicamos el secreto (y si queremos, el algoritmo a utilizar).
- Establecemos el _subject_, que es el _claim_ principal.
- También establecemos la fecha de expiración
- Establecemos la fecha en la que se genera el token.
- Añadimos un nuevo _claim_ con el nombre completo.
- Añadimos otro _claim_ con el rol (o los roles si el usuario tuviera más de uno)


#### 4.1.2 Métodos para procesar y validar el token

Nuestra clase proveedora de tokens también debe ofrecer mecanismos para, una vez obtenido el token, identificar al usuario que lo posee, y validar dicho token. Para ello añadimos algunos métodos más, así como un objeto `parser` para procesar el token

```java
@Service
public class JwtProvider {

    // Resto de propiedades

    private JwtParser parser;

    @PostConstruct
    public void init() {
        parser = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build();
    }
```

El método encargado de la validación del token sería el siguiente:

```java
    public boolean validateToken(String token) {

        try {
            parser.parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            log.info("Error con el token: " + ex.getMessage());
        }
        return false;

    }

```

Nos falta un último método en la clase, que nos servirá en el siguiente apartado, para obtener el identificador del usuario a partir del token JWT.

```java
    public UUID getUserIdFromJwt(String token) {
        return UUID.fromString(parser.parseClaimsJws(token).getBody().getSubject());
    }
```


### 4.2 Filtro para autenticar a través del token


Ahora sí que podemos implementar nuestro filtro, que extenderá a `OncePerRequestFilter`, un conveniente filtro base para extender que se invoca una vez por cada petición.

```java
Log
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UserEntityService userService;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getJwtFromRequest(request);

        try {
        if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {

            //Long userId = jwtProvider.getUserIdFromJwt(token);
            UUID userId = jwtProvider.getUserIdFromJwt(token);

            Optional<UserEntity> userEntity = userService.findById(userId);

            if (userEntity.isPresent()) {
                UserEntity user = userEntity.get();
                UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        user.getRole(),
                        user.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);


            }

            // Si no encontramos al usuario por ID, no se guarda un UsernamePasswordAuthenticationToken
            // en el contexto de seguridad, y por tanto, el usuario no estará autenticado.
            // Por tanto, Spring tratará de autenticarlo en el siguiente filtro, y si no lo consigue,
            // devolverá un error.

        }

        } catch (Exception ex) {
            // Informar en el log
            log.info("No se ha podido establecer el contexto de seguridad (" + ex.getMessage() + ")");
        }

        filterChain.doFilter(request, response);




    }

    private String getJwtFromRequest(HttpServletRequest request) {
        // Authorization: Bearer eltoken.qiemas.megusta
        String bearerToken = request.getHeader(JwtProvider.TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProvider.TOKEN_PREFIX)) {
            return bearerToken.substring(JwtProvider.TOKEN_PREFIX.length());
        }
        return null;
    }

}
```

Primero podemos revisar el método de más abajo, `getJwtFromRequest`. En él vamos a definir y utilizar una estrategia para obtener el token. Usualmente, el token se suele proporcionar en la petición a través de un encabezado `Autentication: Bearer eltoken`.


Si se encuentra el encabezado adecuado, se extrae el token y se devuelve como un `String`. En otro caso, se devuelve un `null`.

> Podría ser conveniente refactorizar el código para usar la clase `Optional`.


Si ahora revisamos el método propiamente hablando del filtro, podemos ver:


- Este filtro recibe dos beans inyectados: el proveedor de tokens y el servicio de usuario.
- En primer lugar, tratamos de extraer el token de la petición
- Si está presente, lo validamos
- En caso de ser válido, obtnemos el ID del usuario
- A partir de dicho ID, obtenemos los datos del usuario
- Generamos una instancia de `UsernamePasswordAuthenticationToken`, que es la clase conveniente para almacenar la información del usuario autenticado en el contexto de seguridad.
- Añadimos esa instancia al contexto de seguridad de la petición.
- Permitimos que la petición continue su curso.

Se contemplan y manejan las siguientes situaciones de error o excepción

- Que no exista el token
- Que el token no sea válido
- Que el token sea válido pero el usuario no exista en la base de datos

### 4.3 Controladores para el registro y la autenticación

Necesitamos ahora una serie de métodos de controlador para el registro y la autenticación

```java
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;

    @PostMapping("/auth/register")
    public ResponseEntity<GetUserDto> nuevoUsuario(@RequestBody CreateUserDto newUser) {
        UserEntity saved = userEntityService.save(newUser);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserDto(saved));

    }


}

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        Authentication authentication =
                authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                           loginDto.getEmail(),
                           loginDto.getPassword()
                   )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Devolver una respuesta adecuada
        // que incluya el token del usuario.
        String jwt = jwtProvider.generateToken(authentication);


        UserEntity user = (UserEntity) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertUserToJwtUserResponse(user, jwt));

    }

    @GetMapping("/me")
    public ResponseEntity<?> quienSoyYo(@AuthenticationPrincipal UserEntity user){
        return ResponseEntity.ok(convertUserToJwtUserResponse(user, null));
    }


    private JwtUserResponse convertUserToJwtUserResponse(UserEntity user, String jwt) {
        return JwtUserResponse.builder()
                .fullName(user.getFullName())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .role(user.getRole().name())
                .token(jwt)
                .build();
    }


}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    private String email;
    private String password;

}

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class JwtUserResponse {

    private String email;
    private String fullName;
    private String avatar;
    private String role;
    private String token;

}
```

> Algunos autores gustan de implementar el mecanismo de obtención del token a partir del `username/password` también como un filtro de tipo `UsernamePasswordAuthenticationFilter`.


## 5. `WebSecurityConfiguration` completo

Una vez que hemos definido todas las clases necesarias para trabajar con los tokens JWT, podemos terminar de implementar la configuración de seguridad:

```java
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthorizationFilter filter;
    private final JwtAccessDeniedHandler accessDeniedHandler;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                    .exceptionHandling()
                        .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler)
                .and()
                    .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/producto/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/auth/**").anonymous()
                    .antMatchers("/h2-console/**").permitAll()
                    .anyRequest().authenticated();

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        // Para dar acceso a h2
        http.headers().frameOptions().disable();


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

```

Añadimos también una clase que configure las peticiones permitidas por el mecanismo CORS.

```java
@Component
@Order(1)
public class CorsFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With");
        chain.doFilter(req, res);
    }
}
```



## Bibliografía

- https://jwt.io
- Curso de Seguridad en tu API Rest con Spring Boot (Openwebinars.net)
- https://thorben-janssen.com/generate-uuids-primary-keys-hibernate/
