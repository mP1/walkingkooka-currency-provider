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
import walkingkooka.currency.CurrencyExchangeRaterContext;
import walkingkooka.plugin.ProviderContext;

import java.util.List;
import java.util.Objects;

/**
 * A {@link CurrencyExchangeRaterProvider} that is empty without any {@link CurrencyExchangeRater}.
 */
final class EmptyCurrencyExchangeRaterProvider implements CurrencyExchangeRaterProvider {

    final static EmptyCurrencyExchangeRaterProvider INSTANCE = new EmptyCurrencyExchangeRaterProvider();

    private EmptyCurrencyExchangeRaterProvider() {
    }

    @Override
    public <C extends CurrencyExchangeRaterContext> CurrencyExchangeRater<C> currencyExchangeRater(final CurrencyExchangeRaterSelector selector,
                                                                                                   final ProviderContext context) {
        Objects.requireNonNull(selector, "selector");
        Objects.requireNonNull(context, "context");

        throw new IllegalArgumentException("Unknown CurrencyExchangeRater " + selector.name());
    }

    @Override
    public <C extends CurrencyExchangeRaterContext> CurrencyExchangeRater<C> currencyExchangeRater(final CurrencyExchangeRaterName name,
                                                                                                   final List<?> values,
                                                                                                   final ProviderContext context) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(values, "values");
        Objects.requireNonNull(context, "context");

        throw new IllegalArgumentException("Unknown CurrencyExchangeRater " + name);
    }

    @Override
    public CurrencyExchangeRaterInfoSet currencyExchangeRaterInfos() {
        return CurrencyExchangeRaterInfoSet.EMPTY;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
