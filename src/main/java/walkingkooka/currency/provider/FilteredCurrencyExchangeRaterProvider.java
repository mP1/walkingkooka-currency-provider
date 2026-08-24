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
import walkingkooka.plugin.FilteredProviderGuard;
import walkingkooka.plugin.ProviderContext;

import java.util.List;
import java.util.Objects;

/**
 * A {@link CurrencyExchangeRaterProvider} that provides {@link CurrencyExchangeRater} from one provider but lists more {@link CurrencyExchangeRaterInfo}.
 */
final class FilteredCurrencyExchangeRaterProvider implements CurrencyExchangeRaterProvider {

    static FilteredCurrencyExchangeRaterProvider with(final CurrencyExchangeRaterProvider provider,
                                                      final CurrencyExchangeRaterInfoSet infos) {
        return new FilteredCurrencyExchangeRaterProvider(
            Objects.requireNonNull(provider, "provider"),
            Objects.requireNonNull(infos, "infos")
        );
    }

    private FilteredCurrencyExchangeRaterProvider(final CurrencyExchangeRaterProvider provider,
                                                  final CurrencyExchangeRaterInfoSet infos) {
        this.guard = FilteredProviderGuard.with(
            infos.names(),
            CurrencyExchangeRaterPluginHelper.INSTANCE
        );
        this.provider = provider;
        this.infos = infos;
    }

    @Override
    public <C extends CurrencyExchangeRaterContext> CurrencyExchangeRater<C> currencyExchangeRater(final CurrencyExchangeRaterSelector selector,
                                                                                                   final ProviderContext context) {
        return this.provider.currencyExchangeRater(
            selector,
            context
        );
    }

    @Override
    public <C extends CurrencyExchangeRaterContext> CurrencyExchangeRater<C> currencyExchangeRater(final CurrencyExchangeRaterName name,
                                                                                                   final List<?> values,
                                                                                                   final ProviderContext context) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(values, "values");
        Objects.requireNonNull(context, "context");

        return this.provider.currencyExchangeRater(
            this.guard.name(name),
            values,
            context
        );
    }

    private final FilteredProviderGuard<CurrencyExchangeRaterName, CurrencyExchangeRaterSelector> guard;

    private final CurrencyExchangeRaterProvider provider;

    @Override
    public CurrencyExchangeRaterInfoSet currencyExchangeRaterInfos() {
        return this.infos;
    }

    private final CurrencyExchangeRaterInfoSet infos;

    @Override
    public String toString() {
        return this.provider.toString();
    }
}
