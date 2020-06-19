package com.scottxuan.web.i18n;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

/**
 * @author : scottxuan
 */
@Configuration
public class I18nConfig {

    /**
     * 默认解析器 其中locale表示默认语言
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new I18nLocaleResolver();
    }
}
