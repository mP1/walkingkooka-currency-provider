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
import walkingkooka.ToStringTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.currency.CurrencyExchangeRater;
import walkingkooka.currency.CurrencyExchangeRaterContext;
import walkingkooka.currency.CurrencyExchangeRaters;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.Url;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.reflect.JavaVisibility;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class FilteredMappedCurrencyExchangeRaterProviderTest implements CurrencyExchangeRaterProviderTesting<FilteredMappedCurrencyExchangeRaterProvider>,
    ToStringTesting<FilteredMappedCurrencyExchangeRaterProvider> {

    private final static AbsoluteUrl URL = Url.parseAbsolute("https://example.com/currencyExchangeRater123");

    private final static CurrencyExchangeRaterName NAME = CurrencyExchangeRaterName.with("different-currencyer-name-123");

    private final static CurrencyExchangeRaterName ORIGINAL_NAME = CurrencyExchangeRaterName.with("original-currencyer-123");

    private final static CurrencyExchangeRater<CurrencyExchangeRaterContext> CURRENCYEXCHANGERATER = CurrencyExchangeRaters.fake();

    private final static ProviderContext CONTEXT = ProviderContexts.fake();

    @Test
    public void testWithNullViewFails() {
        assertThrows(
            NullPointerException.class,
            () -> FilteredMappedCurrencyExchangeRaterProvider.with(
                null,
                CurrencyExchangeRaterProviders.fake()
            )
        );
    }

    @Test
    public void testWithNullProviderFails() {
        assertThrows(
            NullPointerException.class,
            () -> FilteredMappedCurrencyExchangeRaterProvider.with(
                CurrencyExchangeRaterInfoSet.EMPTY,
                null
            )
        );
    }

    @Test
    public void testCurrencyExchangeRaterSelectorWithUnknownFails() {
        this.currencyExchangeRaterFails(
            CurrencyExchangeRaterSelector.parse("unknown"),
            CONTEXT
        );
    }

    @Test
    public void testCurrencyExchangeRaterSelector() {
        this.currencyExchangeRaterAndCheck(
            CurrencyExchangeRaterSelector.parse("" + NAME),
            CONTEXT,
            CURRENCYEXCHANGERATER
        );
    }

    @Test
    public void testCurrencyExchangeRaterNameWithUnknownFails() {
        this.currencyExchangeRaterFails(
            CurrencyExchangeRaterName.with("unknown"),
            Lists.empty(),
            CONTEXT
        );
    }

    @Test
    public void testCurrencyExchangeRaterName() {
        this.currencyExchangeRaterAndCheck(
            NAME,
            Lists.empty(),
            CONTEXT,
            CURRENCYEXCHANGERATER
        );
    }

    @Test
    public void testInfos() {
        this.currencyExchangeRaterInfosAndCheck(
            CurrencyExchangeRaterInfo.with(
                URL,
                NAME
            )
        );
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createCurrencyExchangeRaterProvider(),
            "https://example.com/currencyExchangeRater123 different-currencyer-name-123"
        );
    }

    @Override
    public FilteredMappedCurrencyExchangeRaterProvider createCurrencyExchangeRaterProvider() {
        return FilteredMappedCurrencyExchangeRaterProvider.with(
            CurrencyExchangeRaterInfoSet.EMPTY.concat(
                CurrencyExchangeRaterInfo.with(
                    URL,
                    NAME
                )
            ),
            new FakeCurrencyExchangeRaterProvider() {

                @Override
                public <C extends CurrencyExchangeRaterContext> CurrencyExchangeRater<C> currencyExchangeRater(final CurrencyExchangeRaterName name,
                                                                                                               final List<?> values,
                                                                                                               final ProviderContext context) {
                    Objects.requireNonNull(name, "name");
                    Objects.requireNonNull(values, "values");
                    Objects.requireNonNull(context, "context");

                    if (false == name.equals(ORIGINAL_NAME)) {
                        throw new IllegalArgumentException("Unknown CurrencyExchangeRater " + name);
                    }
                    return Cast.to(CURRENCYEXCHANGERATER);
                }

                @Override
                public CurrencyExchangeRaterInfoSet currencyExchangeRaterInfos() {
                    return CurrencyExchangeRaterInfoSet.EMPTY.concat(
                        CurrencyExchangeRaterInfo.with(
                            URL,
                            ORIGINAL_NAME
                        )
                    );
                }
            }
        );
    }

    // Class............................................................................................................

    @Override
    public Class<FilteredMappedCurrencyExchangeRaterProvider> type() {
        return FilteredMappedCurrencyExchangeRaterProvider.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
