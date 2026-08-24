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
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.currency.CurrencyExchangeRater;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.plugin.ProviderTesting;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface CurrencyExchangeRaterProviderTesting<T extends CurrencyExchangeRaterProvider> extends ProviderTesting<T> {


    // currencyExchangeRater(CurrencyExchangeRaterSelector).....................................................................................

    @Test
    default void testCurrencyExchangeRaterSelectorWithNullSelectorFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createCurrencyExchangeRaterProvider()
                .currencyExchangeRater(
                    null,
                    ProviderContexts.fake()
                )
        );
    }

    @Test
    default void testCurrencyExchangeRaterSelectorWithNullContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createCurrencyExchangeRaterProvider()
                .currencyExchangeRater(
                    CurrencyExchangeRaterSelector.parse("currency-exchange-rater"),
                    null
                )
        );
    }

    default void currencyExchangeRaterFails(final String selector,
                                            final ProviderContext context) {
        this.currencyExchangeRaterFails(
            CurrencyExchangeRaterSelector.parse(selector),
            context
        );
    }

    default void currencyExchangeRaterFails(final CurrencyExchangeRaterSelector selector,
                                            final ProviderContext context) {
        this.currencyExchangeRaterFails(
            this.createCurrencyExchangeRaterProvider(),
            selector,
            context
        );
    }

    default void currencyExchangeRaterFails(final CurrencyExchangeRaterProvider provider,
                                            final CurrencyExchangeRaterSelector selector,
                                            final ProviderContext context) {
        assertThrows(
            IllegalArgumentException.class,
            () -> selector.evaluateValueText(
                provider,
                context
            )
        );
    }

    default void currencyExchangeRaterAndCheck(final String selector,
                                               final ProviderContext context,
                                               final CurrencyExchangeRater<?> expected) {
        this.currencyExchangeRaterAndCheck(
            CurrencyExchangeRaterSelector.parse(selector),
            context,
            expected
        );
    }

    default void currencyExchangeRaterAndCheck(final CurrencyExchangeRaterSelector selector,
                                               final ProviderContext context,
                                               final CurrencyExchangeRater<?> expected) {
        this.currencyExchangeRaterAndCheck(
            this.createCurrencyExchangeRaterProvider(),
            selector,
            context,
            expected
        );
    }

    default void currencyExchangeRaterAndCheck(final CurrencyExchangeRaterProvider provider,
                                               final String selector,
                                               final ProviderContext context,
                                               final CurrencyExchangeRater<?> expected) {
        this.currencyExchangeRaterAndCheck(
            provider,
            CurrencyExchangeRaterSelector.parse(selector),
            context,
            expected
        );
    }

    default void currencyExchangeRaterAndCheck(final CurrencyExchangeRaterProvider provider,
                                               final CurrencyExchangeRaterSelector selector,
                                               final ProviderContext context,
                                               final CurrencyExchangeRater<?> expected) {
        this.checkEquals(
            expected,
            provider.currencyExchangeRater(
                selector,
                context
            )
        );
    }

    // currencyExchangeRater(CurrencyExchangeRaterName, List<?>)................................................................................

    @Test
    default void testCurrencyExchangeRaterNameWithNullNameFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createCurrencyExchangeRaterProvider()
                .currencyExchangeRater(
                    null,
                    Lists.empty(),
                    ProviderContexts.fake()
                )
        );
    }

    @Test
    default void testCurrencyExchangeRaterNameWithNullValueFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createCurrencyExchangeRaterProvider()
                .currencyExchangeRater(
                    CurrencyExchangeRaterName.PROPERTIES,
                    null,
                    ProviderContexts.fake()
                )
        );
    }

    @Test
    default void testCurrencyExchangeRaterNameWithNullContextFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createCurrencyExchangeRaterProvider()
                .currencyExchangeRater(
                    CurrencyExchangeRaterName.PROPERTIES,
                    Lists.empty(),
                    null
                )
        );
    }

    default void currencyExchangeRaterFails(final CurrencyExchangeRaterName name,
                                            final List<?> values,
                                            final ProviderContext context) {
        this.currencyExchangeRaterFails(
            this.createCurrencyExchangeRaterProvider(),
            name,
            values,
            context
        );
    }

    default void currencyExchangeRaterFails(final CurrencyExchangeRaterProvider provider,
                                            final CurrencyExchangeRaterName name,
                                            final List<?> values,
                                            final ProviderContext context) {
        assertThrows(
            IllegalArgumentException.class,
            () -> provider.currencyExchangeRater(
                name,
                values,
                context
            )
        );
    }

    default void currencyExchangeRaterAndCheck(final CurrencyExchangeRaterName name,
                                               final List<?> values,
                                               final ProviderContext context,
                                               final CurrencyExchangeRater<?> expected) {
        this.currencyExchangeRaterAndCheck(
            this.createCurrencyExchangeRaterProvider(),
            name,
            values,
            context,
            expected
        );
    }

    default void currencyExchangeRaterAndCheck(final CurrencyExchangeRaterProvider provider,
                                               final CurrencyExchangeRaterName name,
                                               final List<?> values,
                                               final ProviderContext context,
                                               final CurrencyExchangeRater<?> expected) {
        this.checkEquals(
            expected,
            provider.currencyExchangeRater(
                name,
                values,
                context
            ),
            () -> provider + " " + name + " " + values
        );
    }

    // currencyExchangeRaterInfos...................................................................................................

    default void currencyExchangeRaterInfosAndCheck(final CurrencyExchangeRaterInfo... expected) {
        this.currencyExchangeRaterInfosAndCheck(
            this.createCurrencyExchangeRaterProvider(),
            expected
        );
    }

    default void currencyExchangeRaterInfosAndCheck(final CurrencyExchangeRaterProvider provider,
                                                    final CurrencyExchangeRaterInfo... expected) {
        this.currencyExchangeRaterInfosAndCheck(
            provider,
            CurrencyExchangeRaterInfoSet.with(
                Sets.of(
                    expected
                )
            )
        );
    }

    default void currencyExchangeRaterInfosAndCheck(final CurrencyExchangeRaterInfoSet expected) {
        this.currencyExchangeRaterInfosAndCheck(
            this.createCurrencyExchangeRaterProvider(),
            expected
        );
    }

    default void currencyExchangeRaterInfosAndCheck(final CurrencyExchangeRaterProvider provider,
                                                    final CurrencyExchangeRaterInfoSet expected) {
        this.checkEquals(
            expected,
            provider.currencyExchangeRaterInfos(),
            () -> provider.toString()
        );
    }

    T createCurrencyExchangeRaterProvider();
}
