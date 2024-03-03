package guru.springframework.sfgpetclinic.formatters;

import guru.springframework.sfgpetclinic.model.PetType;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author john
 * @since 29/02/2024
 */
@Component
public class PetTypeFormater implements Formatter<PetType> {
    @Override
    public PetType parse(String text, Locale locale) throws ParseException {
        // Assuming the String format is like: {id='1', name='Cat'}
        try {
            String[] keyValuePairs = text.split(",");
            Long id = Long.parseLong(keyValuePairs[0].split("=")[1].replace("'", ""));
            String name = keyValuePairs[1].split("=")[1].replace("'", "");
            return new PetType(id, name);
        } catch (Exception e) {
            throw new ParseException("Invalid format for PetType", 0);
        }
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return String.format("{id='%d', name='%s'}", petType.getId(), petType.getName());
    }
}
