package pl.edu.pg.apkademikbackend;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.edu.pg.apkademikbackend.WebSecurity.config.JwtTokenUtil;
import pl.edu.pg.apkademikbackend.WebSecurity.service.JwtUserDetailsService;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest()
@AutoConfigureMockMvc
public class SecuredControllerWebMvcIntegrationTest {

    @Autowired
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
    }
}
