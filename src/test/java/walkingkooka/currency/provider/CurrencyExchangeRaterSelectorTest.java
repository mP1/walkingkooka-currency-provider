/*
 * Copyright 2026 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.currency.provider;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.InvalidCharacterException;
import walkingkooka.collect.list.Lists;
import walkingkooka.currency.CurrencyExchangeRater;
import walkingkooka.currency.CurrencyExchangeRaterContext;
import walkingkooka.currency.CurrencyExchangeRaters;
import walkingkooka.plugin.PluginSelectorEvaluateValueTextProvider;
import walkingkooka.plugin.PluginSelectorLikeTesting;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.text.CharSequences;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CurrencyExchangeRaterSelectorTest implements PluginSelectorLikeTesting<CurrencyExchangeRaterSelector, CurrencyExchangeRaterName> {

    private final static CurrencyExchangeRaterName NAME = CurrencyExchangeRaterName.with("currency-exchange-rater-1");

    private final static CurrencyExchangeRaterName NAME2 = CurrencyExchangeRaterName.with("currency-exchange-rater-2");

    private final static CurrencyExchangeRaterName NAME3 = CurrencyExchangeRaterName.with("currency-exchange-rater-3");

    private final static String TEXT = "$0.00";

    private final static ProviderContext CONTEXT = ProviderContexts.fake();

    @Override
    public CurrencyExchangeRaterSelector createPluginSelectorLike(final CurrencyExchangeRaterName name,
                                                                  final String text) {
        return CurrencyExchangeRaterSelector.with(
            name,
            text
        );
    }

    @Override
    public CurrencyExchangeRaterName createName(final String value) {
        return CurrencyExchangeRaterName.with(value);
    }

    // parse............................................................................................................

    @Override
    public CurrencyExchangeRaterSelector parseString(final String text) {
        return CurrencyExchangeRaterSelector.parse(text);
    }

    // EvaluateValueText................................................................................................

    @Test
    public void testEvaluateValueTextFails() {
        final String text = NAME + " text/plain";

        final InvalidCharacterException thrown = assertThrows(
            InvalidCharacterException.class,
            () -> CurrencyExchangeRaterSelector.parse(text)
                .evaluateValueText(
                    CurrencyExchangeRaterProviders.fake(),
                    CONTEXT
                )
        );

        this.checkEquals(
            new InvalidCharacterException(
                text,
                text.indexOf(' ')
            ).getMessage(),
            thrown.getMessage()
        );
    }

    @Test
    public void testEvaluateValueTextNoText() {
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected = CurrencyExchangeRaters.fake();

        this.evaluateValueTextAndCheck(
            NAME + "",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p);

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextSpacesText() {
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected = CurrencyExchangeRaters.fake();

        this.evaluateValueTextAndCheck(
            NAME + " ",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p);

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextSpacesText2() {
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected = CurrencyExchangeRaters.fake();

        this.evaluateValueTextAndCheck(
            NAME + "   ",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p);

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextOpenParensFail() {
        this.evaluateValueTextFails(
            NAME + "(",
            "Invalid character '(' at 25"
        );
    }

    @Test
    public void testEvaluateValueTextDoubleLiteral() {
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected = CurrencyExchangeRaters.fake();

        this.evaluateValueTextAndCheck(
            NAME + " (1)",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p, 1.0);

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextNegativeDoubleLiteral() {
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected = CurrencyExchangeRaters.fake();

        this.evaluateValueTextAndCheck(
            NAME + " (-1)",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p, -1.0);

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextDoubleLiteralWithDecimals() {
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected = CurrencyExchangeRaters.fake();

        this.evaluateValueTextAndCheck(
            NAME + " (1.25)",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p, 1.25);

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextDoubleMissingClosingParensFail() {
        this.checkEquals(
            26,
            NAME.textLength() + 1
        );

        this.evaluateValueTextFails(
            NAME + "(1",
            "Invalid character '1' at 26"
        );
    }

    @Test
    public void testEvaluateValueTextStringUnclosedFail() {
        this.evaluateValueTextFails(
            NAME + " (\"unclosed",
            "Missing closing '\"'"
        );
    }

    @Test
    public void testEvaluateValueTextEmptyParameterList() {
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected = CurrencyExchangeRaters.fake();

        this.evaluateValueTextAndCheck(
            NAME + " ()",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p);

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextEmptyParameterListWithExtraSpaces() {
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected = CurrencyExchangeRaters.fake();

        this.evaluateValueTextAndCheck(
            NAME + "  ( )",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p);

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextEmptyParameterListWithExtraSpaces2() {
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected = CurrencyExchangeRaters.fake();

        this.evaluateValueTextAndCheck(
            NAME + "   (  )",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p);

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextStringLiteral() {
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected = CurrencyExchangeRaters.fake();

        this.evaluateValueTextAndCheck(
            NAME + " (\"string-literal-parameter\")",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p, "string-literal-parameter");

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextStringLiteralStringLiteral() {
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected = CurrencyExchangeRaters.fake();

        this.evaluateValueTextAndCheck(
            NAME + " (\"string-literal-parameter-1\",\"string-literal-parameter-2\")",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p, "string-literal-parameter-1", "string-literal-parameter-2");

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextStringLiteralStringLiteralWithExtraSpaceIgnored() {
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected = CurrencyExchangeRaters.fake();

        this.evaluateValueTextAndCheck(
            NAME + "  ( \"string-literal-parameter-1\" , \"string-literal-parameter-2\" )",
            (n, p, x) -> {
                checkName(n, NAME);
                checkParameters(p, "string-literal-parameter-1", "string-literal-parameter-2");

                return expected;
            },
            expected
        );
    }

    @Test
    public void testEvaluateValueTextCurrencyExchangeRater() {
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected1 = CurrencyExchangeRaters.fake();
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected2 = CurrencyExchangeRaters.fake();

        this.evaluateValueTextAndCheck(
            NAME + " (" + NAME2 + ")",
            (n, p, x) -> {
                if (n.equals(NAME)) {
                    checkParameters(p, expected2);
                    return expected1;
                }
                if (n.equals(NAME2)) {
                    checkParameters(p);
                    return expected2;
                }

                throw new IllegalArgumentException("Unknown currencyExchangeRater " + n);
            },
            expected1
        );
    }

    @Test
    public void testEvaluateValueTextCurrencyExchangeRaterCurrencyExchangeRater() {
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected1 = CurrencyExchangeRaters.fake();
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected2 = CurrencyExchangeRaters.fake();
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected3 = CurrencyExchangeRaters.fake();

        this.evaluateValueTextAndCheck(
            NAME + " (" + NAME2 + "," + NAME3 + ")",
            (n, p, x) -> {
                if (n.equals(NAME)) {
                    checkParameters(p, expected2, expected3);
                    return expected1;
                }
                if (n.equals(NAME2)) {
                    checkParameters(p);
                    return expected2;
                }
                if (n.equals(NAME3)) {
                    checkParameters(p);
                    return expected3;
                }

                throw new IllegalArgumentException("Unknown currencyExchangeRater " + n);
            },
            expected1
        );
    }

    @Test
    public void testEvaluateValueTextNestedCurrencyExchangeRater() {
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected1 = CurrencyExchangeRaters.fake();
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected2 = CurrencyExchangeRaters.fake();
        final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected3 = CurrencyExchangeRaters.fake();

        this.evaluateValueTextAndCheck(
            NAME + " (" + NAME2 + "(" + NAME3 + "))",
            (n, p, x) -> {
                if (n.equals(NAME)) {
                    checkParameters(p, expected2);
                    return expected1;
                }
                if (n.equals(NAME2)) {
                    checkParameters(p, expected3);
                    return expected2;
                }
                if (n.equals(NAME3)) {
                    checkParameters(p);
                    return expected3;
                }

                throw new IllegalArgumentException("Unknown CurrencyExchangeRater " + n);
            },
            expected1
        );
    }

    private void evaluateValueTextFails(final String selector,
                                        final String expected) {
        this.evaluateValueTextFails(
            selector,
            CONTEXT,
            expected
        );
    }

    private void evaluateValueTextFails(final String selector,
                                        final ProviderContext context,
                                        final String expected) {
        this.evaluateValueTextFails(
            selector,
            CurrencyExchangeRaterProviders.fake(),
            context,
            expected
        );
    }

    private void evaluateValueTextFails(final String selector,
                                        final CurrencyExchangeRaterProvider provider,
                                        final ProviderContext context,
                                        final String expected) {
        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> CurrencyExchangeRaterSelector.parse(selector)
                .evaluateValueText(
                    provider,
                    context
                )
        );
        this.checkEquals(
            expected,
            thrown.getMessage(),
            () -> "currencyExchangeRater " + CharSequences.quoteAndEscape(selector)
        );
    }

    private void evaluateValueTextAndCheck(final String selector,
                                           final PluginSelectorEvaluateValueTextProvider<CurrencyExchangeRaterName, CurrencyExchangeRater<CurrencyExchangeRaterContext>> factory,
                                           final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected) {
        this.evaluateValueTextAndCheck(
            selector,
            new FakeCurrencyExchangeRaterProvider() {
                @Override
                public <C extends CurrencyExchangeRaterContext> CurrencyExchangeRater<C> currencyExchangeRater(final CurrencyExchangeRaterName name,
                                                                                                               final List<?> values,
                                                                                                               final ProviderContext context) {
                    return Cast.to(
                        factory.get(
                            name,
                            values,
                            context
                        )
                    );
                }
            },
            CONTEXT,
            expected
        );
    }

    private void evaluateValueTextAndCheck(final String selector,
                                           final CurrencyExchangeRaterProvider provider,
                                           final ProviderContext context,
                                           final CurrencyExchangeRater<CurrencyExchangeRaterContext> expected) {
        this.checkEquals(
            expected,
            CurrencyExchangeRaterSelector.parse(selector)
                .evaluateValueText(
                    provider,
                    context
                )
        );
    }

    private void checkName(final CurrencyExchangeRaterName name,
                           final CurrencyExchangeRaterName expected) {
        this.checkEquals(
            expected,
            name,
            "name"
        );
    }

    private void checkParameters(final List<?> parameters,
                                 final Object... expected) {
        this.checkParameters(
            parameters,
            Lists.of(expected)
        );
    }

    private void checkParameters(final List<?> parameters,
                                 final List<?> expected) {
        this.checkEquals(
            expected,
            parameters,
            "parameters"
        );
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<CurrencyExchangeRaterSelector> type() {
        return CurrencyExchangeRaterSelector.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }

    // Json.............................................................................................................

    @Test
    public void testMarshall() {
        this.marshallAndCheck(
            this.createJsonNodeMarshallingValue(),
            "\"currency-exchange-rater-1 $0.00\""
        );
    }

    @Test
    public void testUnmarshall() {
        this.unmarshallAndCheck(
            "\"currency-exchange-rater-1 $0.00\"",
            this.createJsonNodeMarshallingValue()
        );
    }

    @Override
    public CurrencyExchangeRaterSelector unmarshall(final JsonNode json,
                                                    final JsonNodeUnmarshallContext context) {
        return CurrencyExchangeRaterSelector.unmarshall(
            json,
            context
        );
    }

    @Override
    public CurrencyExchangeRaterSelector createJsonNodeMarshallingValue() {
        return CurrencyExchangeRaterSelector.with(
            NAME,
            TEXT
        );
    }

    // type name testing................................................................................................

    @Override
    public String typeNamePrefix() {
        return CurrencyExchangeRater.class.getSimpleName();
    }
}
