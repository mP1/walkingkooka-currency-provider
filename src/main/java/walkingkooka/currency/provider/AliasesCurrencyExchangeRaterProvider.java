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
import java.util.Optional;

/**
 * A {@link CurrencyExchangeRaterProvider} that uses the given aliases definition and {@link CurrencyExchangeRaterProvider} to present another view.
 */
final class AliasesCurrencyExchangeRaterProvider implements CurrencyExchangeRaterProvider {

    static AliasesCurrencyExchangeRaterProvider with(final CurrencyExchangeRaterAliasSet aliases,
                                                     final CurrencyExchangeRaterProvider provider) {
        return new AliasesCurrencyExchangeRaterProvider(
            Objects.requireNonNull(aliases, "aliases"),
            Objects.requireNonNull(provider, "provider")
        );
    }

    private AliasesCurrencyExchangeRaterProvider(final CurrencyExchangeRaterAliasSet aliases,
                                                 final CurrencyExchangeRaterProvider provider) {
        this.aliases = aliases;
        this.provider = provider;

        this.infos = aliases.merge(provider.currencyExchangeRaterInfos());
    }

    @Override
    public <C extends CurrencyExchangeRaterContext> CurrencyExchangeRater<C> currencyExchangeRater(final CurrencyExchangeRaterSelector selector,
                                                                                                   final ProviderContext context) {
        return this.provider.currencyExchangeRater(
            this.aliases.selector(selector),
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

        CurrencyExchangeRater<C> currencyExchangeRater;

        final CurrencyExchangeRaterAliasSet aliases = this.aliases;
        final CurrencyExchangeRaterProvider provider = this.provider;

        final Optional<CurrencyExchangeRaterSelector> selector = aliases.aliasSelector(name);
        if (selector.isPresent()) {
            if (false == values.isEmpty()) {
                throw new IllegalArgumentException("Alias " + name + " should have no values");
            }
            // assumes that $provider caches selectors to currencyExchangeRater
            currencyExchangeRater = provider.currencyExchangeRater(
                selector.get(),
                context
            );
        } else {
            currencyExchangeRater = provider.currencyExchangeRater(
                aliases.aliasOrName(name)
                    .orElseThrow(() -> new IllegalArgumentException("Unknown CurrencyExchangeRater " + name)),
                values,
                context
            );
        }

        return currencyExchangeRater;
    }

    private final CurrencyExchangeRaterAliasSet aliases;

    private final CurrencyExchangeRaterProvider provider;

    @Override
    public CurrencyExchangeRaterInfoSet currencyExchangeRaterInfos() {
        return this.infos;
    }

    private final CurrencyExchangeRaterInfoSet infos;

    @Override
    public String toString() {
        return this.currencyExchangeRaterInfos().toString();
    }
}
