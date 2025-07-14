package org.jabref.model.entry.field;

import java.util.stream.Stream;
import java.util.Collections;

import org.jabref.model.entry.types.BiblatexApaEntryType;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;

class FieldFactoryTest {
    @Test
    void orFieldsTwoTerms() {
        assertEquals("Aaa/Bbb", FieldFactory.serializeOrFields(new UnknownField("aaa"), new UnknownField("bbb")));
    }

    @Test
    void orFieldsThreeTerms() {
        assertEquals("Aaa/Bbb/Ccc", FieldFactory.serializeOrFields(new UnknownField("aaa"), new UnknownField("bbb"), new UnknownField("ccc")));
    }

    private static Stream<Arguments> fieldsWithoutFieldProperties() {
        return Stream.of(
                // comment fields
                Arguments.of(new UserSpecificCommentField("user1"), "comment-user1"),
                Arguments.of(new UserSpecificCommentField("other-user-id"), "comment-other-user-id"),
                // unknown field
                Arguments.of(new UnknownField("cased", "cAsEd"), "cAsEd"),
                Arguments.of(new UnknownField("rights", "rights"), "UnknownField{name='rights'}")
        );
    }

    @ParameterizedTest
    @MethodSource
    void fieldsWithoutFieldProperties(Field expected, String name) {
        assertEquals(expected, FieldFactory.parseField(name));
    }

    @Test
    void doesNotParseApaFieldWithoutEntryType() {
        assertNotEquals(BiblatexApaField.ARTICLE, FieldFactory.parseField("article"));
    }

    @Test
    void doesParseApaFieldWithEntryType() {
        assertEquals(BiblatexApaField.ARTICLE, FieldFactory.parseField(BiblatexApaEntryType.Constitution, "article"));
    }
}

public class FieldFactoryTest {

    @Test
    void isNotEmptyReturnsFalseForNullOrBlank() {
        assertFalse(FieldFactory.isNotEmpty(null));
        assertFalse(FieldFactory.isNotEmpty(""));
        assertFalse(FieldFactory.isNotEmpty("   "));
    }

    @Test
    void isNotEmptyReturnsTrueForNonBlank() {
        assertTrue(FieldFactory.isNotEmpty("author"));
        assertTrue(FieldFactory.isNotEmpty("  editor "));
    }

    @Test
    void matchesFieldPatternAcceptsValidNames() {
        assertTrue(FieldFactory.matchesFieldPattern("title"));
        assertTrue(FieldFactory.matchesFieldPattern("field1"));
        assertTrue(FieldFactory.matchesFieldPattern("a-b:c"));
    }

    @Test
    void matchesFieldPatternRejectsInvalidNames() {
        assertFalse(FieldFactory.matchesFieldPattern("1title"));
        assertFalse(FieldFactory.matchesFieldPattern("-field"));
        assertFalse(FieldFactory.matchesFieldPattern("with space"));
    }

    @Test
    void parseOrFieldsReturnsEmptyForBlankInput() {
        OrFields empty = FieldFactory.parseOrFields("  ");
        assertEquals(Collections.emptySet(), empty.getFields());
    }

    @Test
    void serializeAndParseAreInverseOperations() {
        String input = "author/editor/unknownField";
        OrFields fields = FieldFactory.parseOrFields(input);
        String serialized = FieldFactory.serializeOrFields(fields);
        assertEquals(input, serialized);
    }
}

