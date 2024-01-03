package com.ecom.commandservice.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@ConfigurationProperties(prefix = "mes-config-ms")
@RefreshScope
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ApplicationConfiguration {
    private int commandesLast;
}
