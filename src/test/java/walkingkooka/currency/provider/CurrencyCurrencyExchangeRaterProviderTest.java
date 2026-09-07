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
import walkingkooka.Either;
import walkingkooka.collect.list.Lists;
import walkingkooka.convert.BinaryNumberConverterFunctions;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.currency.CurrencyExchangeRaters;
import walkingkooka.currency.CurrencyLocaleContextTesting;
import walkingkooka.datetime.DateTimeContextTesting;
import walkingkooka.math.DecimalNumberContextTesting;
import walkingkooka.plugin.FakeProviderContext;
import walkingkooka.props.Properties;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.text.BinaryTextContextTesting;
import walkingkooka.text.CharSequences;

import java.util.function.Function;

public final class CurrencyCurrencyExchangeRaterProviderTest implements CurrencyExchangeRaterProviderTesting<CurrencyCurrencyExchangeRaterProvider>,
    BinaryTextContextTesting,
    CurrencyLocaleContextTesting,
    DateTimeContextTesting,
    DecimalNumberContextTesting {

    private final static Function<String, Number> NUMBER_PARSER = Double::parseDouble;

    @Test
    public void testCurrencyExchangeRaterWithEMpty() {
        this.currencyExchangeRaterAndCheck(
            "empty",
            new FakeProviderContext() {
            },
            CurrencyExchangeRaters.empty()
        );
    }

    @Test
    public void testCurrencyExchangeRaterWithProperties() {
        final Properties properties = Properties.parse(
            "AUD-NZD=1.1\n"
        );

        System.out.println(
            properties
        );

        System.out.println(
            "properties " + properties
        );

        System.out.println(
            "properties " + CharSequences.quoteAndEscape(properties.toString())
        );

        this.currencyExchangeRaterAndCheck(
            "properties (" + CharSequences.quoteAndEscape(properties.toString()) + ")",
            new FakeProviderContext() {

                @Override
                public boolean canConvert(final Object value,
                                          final Class<?> type) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public <T> Either<T, String> convert(final Object value,
                                                     final Class<T> type) {
                    return this.converterContext.convert(
                        value,
                        type
                    );
                }

                private final ConverterContext converterContext = ConverterContexts.basic(
                    false, // canNumbersHaveGroupSeparator
                    0, // dateTimeOffset
                    ',', // valueSeparator
                    Converters.collection(
                        Lists.of(
                            Converters.characterOrCharSequenceOrHasTextOrStringToCharacterOrCharSequenceOrString(),
                            Converters.textToProperties()
                        )
                    ),
                    BinaryNumberConverterFunctions.multiply(),
                    BINARY_TEXT_CONTEXT,
                    CURRENCY_LOCALE_CONTEXT,
                    DATE_TIME_CONTEXT,
                    DECIMAL_NUMBER_CONTEXT
                );
            },
            CurrencyExchangeRaters.properties(
                properties,
                NUMBER_PARSER
            )
        );
    }

    @Override
    public CurrencyCurrencyExchangeRaterProvider createCurrencyExchangeRaterProvider() {
        return CurrencyCurrencyExchangeRaterProvider.with(
            NUMBER_PARSER
        );
    }

    // TreePrintable....................................................................................................

    @Test
    public void testPrintTree() {
        this.treePrintAndCheck(
            this.createCurrencyExchangeRaterProvider(),
            "CurrencyCurrencyExchangeRaterProvider\n" +
                "  CurrencyExchangeRaterInfoSet\n" +
                "    https://github.com/mP1/walkingkooka-currency-provider/CurrencyExchangeRater/empty empty\n" +
                "    https://github.com/mP1/walkingkooka-currency-provider/CurrencyExchangeRater/properties properties\n"
        );
    }

    // class............................................................................................................

    @Override
    public Class<CurrencyCurrencyExchangeRaterProvider> type() {
        return CurrencyCurrencyExchangeRaterProvider.class;
    }
}
