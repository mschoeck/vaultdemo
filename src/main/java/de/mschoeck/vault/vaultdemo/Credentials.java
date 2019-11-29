package de.mschoeck.vault.vaultdemo;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credentials {

    private final String userid;
    private final String password;
}
