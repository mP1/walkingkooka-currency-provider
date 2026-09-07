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
import walkingkooka.collect.set.Sets;
import walkingkooka.currency.CurrencyExchangeRater;
import walkingkooka.currency.CurrencyExchangeRaterContext;
import walkingkooka.currency.CurrencyExchangeRaters;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.Url;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.plugin.ProviderContexts;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class MergedMappedCurrencyExchangeRaterProviderTest implements CurrencyExchangeRaterProviderTesting<MergedMappedCurrencyExchangeRaterProvider>,
    ToStringTesting<MergedMappedCurrencyExchangeRaterProvider> {

    private final static AbsoluteUrl RENAMED_URL = Url.parseAbsolute("https://example.com/renamed-currency-exchange-rater-111");

    private final static CurrencyExchangeRaterName RENAMED_RENAME_NAME = CurrencyExchangeRaterName.with("renamed-rename-currency-exchange-rater-111");

    private final static CurrencyExchangeRaterName RENAMED_PROVIDER_NAME = CurrencyExchangeRaterName.with("renamed-provider-only-currency-exchange-rater-111");

    private final static CurrencyExchangeRater<CurrencyExchangeRaterContext> RENAME_CURRENCYEXCHANGERATER = CurrencyExchangeRaters.fake();

    private final static AbsoluteUrl PROVIDER_ONLY_URL = Url.parseAbsolute("https://example.com/provider-only-currency-exchange-rater-222");

    private final static CurrencyExchangeRaterName PROVIDER_ONLY_NAME = CurrencyExchangeRaterName.with("provider-only-currency-exchange-rater-222");

    private final static CurrencyExchangeRater<CurrencyExchangeRaterContext> PROVIDER_ONLY_CURRENCYEXCHANGERATER = CurrencyExchangeRaters.fake();

    private final static ProviderContext CONTEXT = ProviderContexts.fake();

    @Test
    public void testWithNullInfosFails() {
        assertThrows(
            NullPointerException.class,
            () -> MergedMappedCurrencyExchangeRaterProvider.with(
                null,
                CurrencyExchangeRaterProviders.fake()
            )
        );
    }

    @Test
    public void testWithNullProviderFails() {
        assertThrows(
            NullPointerException.class,
            () -> MergedMappedCurrencyExchangeRaterProvider.with(
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
    public void testCurrencyExchangeRaterSelectorWithRename() {
        this.currencyExchangeRaterAndCheck(
            CurrencyExchangeRaterSelector.parse("" + RENAMED_RENAME_NAME),
            CONTEXT,
            RENAME_CURRENCYEXCHANGERATER
        );
    }

    @Test
    public void testCurrencyExchangeRaterSelectorWithProviderOnly() {
        this.currencyExchangeRaterAndCheck(
            CurrencyExchangeRaterSelector.parse("" + PROVIDER_ONLY_NAME),
            CONTEXT,
            PROVIDER_ONLY_CURRENCYEXCHANGERATER
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
    public void testCurrencyExchangeRaterNameWithRename() {
        this.currencyExchangeRaterAndCheck(
            RENAMED_RENAME_NAME,
            Lists.empty(),
            CONTEXT,
            RENAME_CURRENCYEXCHANGERATER
        );
    }

    @Test
    public void testCurrencyExchangeRaterNameWithProviderOnly() {
        this.currencyExchangeRaterAndCheck(
            PROVIDER_ONLY_NAME,
            Lists.empty(),
            CONTEXT,
            PROVIDER_ONLY_CURRENCYEXCHANGERATER
        );
    }

    @Test
    public void testInfos() {
        this.currencyExchangeRaterInfosAndCheck(
            CurrencyExchangeRaterInfo.with(
                RENAMED_URL,
                RENAMED_RENAME_NAME
            ),
            CurrencyExchangeRaterInfo.with(
                PROVIDER_ONLY_URL,
                PROVIDER_ONLY_NAME
            )
        );
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createCurrencyExchangeRaterProvider(),
            "https://example.com/provider-only-currency-exchange-rater-222 provider-only-currency-exchange-rater-222, https://example.com/renamed-currency-exchange-rater-111 renamed-rename-currency-exchange-rater-111"
        );
    }

    @Override
    public MergedMappedCurrencyExchangeRaterProvider createCurrencyExchangeRaterProvider() {
        return MergedMappedCurrencyExchangeRaterProvider.with(
            CurrencyExchangeRaterInfoSet.with(
                Sets.of(
                    CurrencyExchangeRaterInfo.with(
                        RENAMED_URL,
                        RENAMED_RENAME_NAME
                    )
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

                    if (name.equals(RENAMED_PROVIDER_NAME)) {
                        return Cast.to(RENAME_CURRENCYEXCHANGERATER);
                    }
                    if (name.equals(PROVIDER_ONLY_NAME)) {
                        return Cast.to(PROVIDER_ONLY_CURRENCYEXCHANGERATER);
                    }
                    throw new IllegalArgumentException("Unknown CurrencyExchangeRater " + name);
                }

                @Override
                public CurrencyExchangeRaterInfoSet currencyExchangeRaterInfos() {
                    return CurrencyExchangeRaterInfoSet.with(
                        Sets.of(
                            CurrencyExchangeRaterInfo.with(
                                RENAMED_URL,
                                RENAMED_PROVIDER_NAME
                            ),
                            CurrencyExchangeRaterInfo.with(
                                PROVIDER_ONLY_URL,
                                PROVIDER_ONLY_NAME
                            )
                        )
                    );
                }
            }
        );
    }

    // Class............................................................................................................

    @Override
    public Class<MergedMappedCurrencyExchangeRaterProvider> type() {
        return MergedMappedCurrencyExchangeRaterProvider.class;
    }
}
