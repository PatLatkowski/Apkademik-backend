package pl.edu.pg.apkademikbackend;


import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertNotNull;


@SpringBootTest()
@AutoConfigureMockMvc
public class SecuredControllerWebMvcIntegrationTest {

    /*@Autowired
    private MockMvc mvc;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    private final String email ="czajnik98@wp.pl";
    private final String password = "ala";

    @Test
    public void shouldPassAuthentication() throws Exception{
        mvc.perform(MockMvcRequestBuilders.post("/authenticate").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"email\" : \""+email+"\" , \"password\" : \""+password+"\"}")).andExpect(status().isOk());
    }

    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/greeting")).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldGenerateAuthToken() throws Exception {
        String email = "czajnik98@wp.pl";
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        final String token = jwtTokenUtil.generateToken(userDetails);

        assertNotNull(token);
        mvc.perform(MockMvcRequestBuilders.get("/greeting").header("Authorization","Bearer " + token)).andExpect(status().isOk());
    }*/
}
