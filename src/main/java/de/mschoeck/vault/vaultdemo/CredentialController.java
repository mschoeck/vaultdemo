package de.mschoeck.vault.vaultdemo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class CredentialController {

    @Value("${database.userid:N/A}")
    private String dbUser;

    @Value("${database.password:N/A}")
    private String dbPass;

    @GetMapping("/credentials")
    public ResponseEntity<Credentials> getCredentials(){
        Credentials cred = new Credentials(dbUser, dbPass);
        return ResponseEntity.ok(cred);
    }
}
