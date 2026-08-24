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

import walkingkooka.currency.CurrencyExchangeRater;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.Url;
import walkingkooka.reflect.PublicStaticHelper;

import java.util.function.Function;

/**
 * A collection of CurrencyExchangeRaterProvider(s).
 */
public final class CurrencyExchangeRaterProviders implements PublicStaticHelper {

    /**
     * This is the base {@link AbsoluteUrl} for all {@link CurrencyExchangeRater} in this package. The name of each
     * currencyExchangeRater will be appended to this base.
     */
    public final static AbsoluteUrl BASE_URL = Url.parseAbsolute(
        "https://github.com/mP1/walkingkooka-currency-provider/" + CurrencyExchangeRater.class.getSimpleName()
    );

    /**
     * {@see AliasesCurrencyExchangeRaterProvider}
     */
    public static CurrencyExchangeRaterProvider aliases(final CurrencyExchangeRaterAliasSet aliases,
                                                        final CurrencyExchangeRaterProvider provider) {
        return AliasesCurrencyExchangeRaterProvider.with(
            aliases,
            provider
        );
    }

    /**
     * {@see CurrencyCurrencyExchangeRaterProvider}
     */
    public static CurrencyExchangeRaterProvider currencyExchangeRaters(final Function<String, Number> numberParser) {
        return CurrencyCurrencyExchangeRaterProvider.with(numberParser);
    }

    /**
     * {@see EmptyCurrencyExchangeRaterProvider}
     */
    public static CurrencyExchangeRaterProvider empty() {
        return EmptyCurrencyExchangeRaterProvider.INSTANCE;
    }

    /**
     * {@see FakeCurrencyExchangeRaterProvider}
     */
    public static CurrencyExchangeRaterProvider fake() {
        return new FakeCurrencyExchangeRaterProvider();
    }

    /**
     * {@see FilteredCurrencyExchangeRaterProvider}
     */
    public static CurrencyExchangeRaterProvider filtered(final CurrencyExchangeRaterProvider provider,
                                                         final CurrencyExchangeRaterInfoSet infos) {
        return FilteredCurrencyExchangeRaterProvider.with(
            provider,
            infos
        );
    }

    /**
     * {@see FilteredMappedCurrencyExchangeRaterProvider}
     */
    public static CurrencyExchangeRaterProvider filteredMapped(final CurrencyExchangeRaterInfoSet infos,
                                                               final CurrencyExchangeRaterProvider provider) {
        return FilteredMappedCurrencyExchangeRaterProvider.with(
            infos,
            provider
        );
    }

    /**
     * {@see MergedMappedCurrencyExchangeRaterProvider}
     */
    public static CurrencyExchangeRaterProvider mergedMapped(final CurrencyExchangeRaterInfoSet infos,
                                                             final CurrencyExchangeRaterProvider provider) {
        return MergedMappedCurrencyExchangeRaterProvider.with(
            infos,
            provider
        );
    }

    /**
     * Stop creation
     */
    private CurrencyExchangeRaterProviders() {
        throw new UnsupportedOperationException();
    }
}
