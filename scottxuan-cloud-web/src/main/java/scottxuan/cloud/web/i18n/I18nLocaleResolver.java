package scottxuan.cloud.web.i18n;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import scottxuan.cloud.base.utils.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author : scottxuan
 */
public class I18nLocaleResolver implements LocaleResolver {
    private static final String I18N_LANGUAGE = "language";
    private static final String I18N_LANGUAGE_SESSION = "language:session";

    @Override
    public Locale resolveLocale(HttpServletRequest req) {
        String i18n_language = req.getParameter(I18N_LANGUAGE);
        return Locale.getDefault();
    }

    @Override
    public void setLocale(HttpServletRequest req, HttpServletResponse res, Locale locale) {

    }
}
