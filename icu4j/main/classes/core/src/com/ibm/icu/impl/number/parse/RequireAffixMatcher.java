// © 2017 and later: Unicode, Inc. and others.
// License & terms of use: http://www.unicode.org/copyright.html#License
package com.ibm.icu.impl.number.parse;

/**
 * @author sffc
 *
 */
public class RequireAffixMatcher implements NumberParseMatcher {

    @Override
    public boolean match(StringSegment segment, ParsedNumber result) {
        return false;
    }

    @Override
    public void postProcess(ParsedNumber result) {
        if ((result.prefix == null) != (result.suffix == null)) {
            // We saw a prefix or a suffix but not both. Fail the parse.
            result.clear();
        }
    }

    @Override
    public String toString() {
        return "<RequireAffix>";
    }

}