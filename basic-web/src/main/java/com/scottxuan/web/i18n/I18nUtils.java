package com.scottxuan.web.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author : scottxuan
 */
@Component
public class I18nUtils {
    @Autowired
    private MessageSource messageSource;
    private static I18nUtils i18nUtils;
    public static final String MESSAGE_NOT_FOUND = "message not found!";

    @PostConstruct
    public void init() {
        i18nUtils = this;
    }

    public static String getMessage(String msgKey, Object... args) {
        try {
            return i18nUtils.messageSource.getMessage(msgKey, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException ex) {
            return MESSAGE_NOT_FOUND;
        }
    }

    public static String getMessage(String msgKey) {
        try {
            return i18nUtils.messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException ex) {
            return MESSAGE_NOT_FOUND;
        }
    }
}
