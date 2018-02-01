package hu.devo.util;

import com.univocity.parsers.annotations.BooleanString;
import com.univocity.parsers.annotations.LowerCase;
import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.annotations.Trim;

public class Url {
    @Trim
    @LowerCase
    @Parsed(field = "url")
    public String url;

    @Trim
    @LowerCase
    @BooleanString(trueStrings = { "good" }, falseStrings = { "bad" })
    @Parsed(field = "label")
    public Boolean good;

    @Override
    public String toString() {
        return (good ? "✔\t" : "✖\t") + url;
    }
}
