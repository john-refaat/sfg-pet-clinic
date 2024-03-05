package guru.springframework.sfgpetclinic.formatters;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author john
 * @since 04/03/2024
 */
@Component
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        System.out.println(text);
        return LocalDateTime.parse(text);
    }

    @Override
    public String print(LocalDateTime object, Locale locale) {
        return object.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm"));
    }
}
