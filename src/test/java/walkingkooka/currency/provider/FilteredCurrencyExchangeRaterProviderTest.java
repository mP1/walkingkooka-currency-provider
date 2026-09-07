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
import walkingkooka.ToStringTesting;

import java.util.function.Function;

public final class FilteredCurrencyExchangeRaterProviderTest implements CurrencyExchangeRaterProviderTesting<FilteredCurrencyExchangeRaterProvider>,
    ToStringTesting<FilteredCurrencyExchangeRaterProvider> {

    private final static Function<String, Number> NUMBER_PARSER = Double::parseDouble;

    @Test
    public void testCurrencyExchangeRaterInfos() {
        this.currencyExchangeRaterInfosAndCheck(
            CurrencyExchangeRaterInfoSet.EMPTY.concat(
                CurrencyExchangeRaterInfo.parse("https://github.com/mP1/walkingkooka-currency-provider/CurrencyExchangeRater/properties properties")
            )
        );
    }

    @Override
    public FilteredCurrencyExchangeRaterProvider createCurrencyExchangeRaterProvider() {
        return FilteredCurrencyExchangeRaterProvider.with(
            CurrencyExchangeRaterProviders.currencyExchangeRaters(NUMBER_PARSER),
            CurrencyExchangeRaterInfoSet.EMPTY.concat(
                CurrencyExchangeRaterInfo.parse("https://github.com/mP1/walkingkooka-currency-provider/CurrencyExchangeRater/properties properties")
            )
        );
    }

    // ToString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createCurrencyExchangeRaterProvider(),
            CurrencyExchangeRaterProviders.currencyExchangeRaters(NUMBER_PARSER)
                .toString()
        );
    }

    // class............................................................................................................

    @Override
    public Class<FilteredCurrencyExchangeRaterProvider> type() {
        return FilteredCurrencyExchangeRaterProvider.class;
    }
}
