package com.zhuweiwei.springbootlearning0405.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zww
 * @date 2020-04-05 18:32
 * @description
 **/
@ConfigurationProperties(prefix = "my")
@Component
@Getter
@Setter
@Validated
public class MyProperties {
    @NotNull
    List<String> emails;
    @NotEmpty
    String projectName;
}
