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
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.currency.CurrencyExchangeRater;
import walkingkooka.currency.CurrencyExchangeRaterContext;
import walkingkooka.currency.FakeCurrencyExchangeRater;
import walkingkooka.currency.FakeCurrencyExchangeRaterContext;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.reflect.JavaVisibility;

import java.util.List;

public final class AliasesCurrencyExchangeRaterProviderTest implements CurrencyExchangeRaterProviderTesting<AliasesCurrencyExchangeRaterProvider> {

    private final static String NAME1_STRING = "currency-exchange-rater-1";

    private final static CurrencyExchangeRaterName NAME1 = CurrencyExchangeRaterName.with(NAME1_STRING);

    private final static CurrencyExchangeRaterInfo INFO1 = CurrencyExchangeRaterInfo.parse("https://example.com/currency-exchange-rater-1 " + NAME1);

    private final static CurrencyExchangeRaterName ALIAS2 = CurrencyExchangeRaterName.with("alias2");

    private final static CurrencyExchangeRater<FakeCurrencyExchangeRaterContext> CURRENCYEXCHANGERATER1 = currencyExchangeRater(NAME1);

    private final static String NAME2_STRING = "currency-exchange-rater-2";

    private final static CurrencyExchangeRaterName NAME2 = CurrencyExchangeRaterName.with(NAME2_STRING);

    private final static CurrencyExchangeRater<FakeCurrencyExchangeRaterContext> CURRENCYEXCHANGERATER2 = currencyExchangeRater(NAME2);

    private final static CurrencyExchangeRaterInfo INFO2 = CurrencyExchangeRaterInfo.parse("https://example.com/currency-exchange-rater-2 " + NAME2);

    private final static String NAME3_STRING = "currency-exchange-rater-3";

    private final static CurrencyExchangeRaterName NAME3 = CurrencyExchangeRaterName.with(NAME3_STRING);

    private final static CurrencyExchangeRater<FakeCurrencyExchangeRaterContext> CURRENCYEXCHANGERATER3 = currencyExchangeRater(NAME3);

    private final static CurrencyExchangeRaterInfo INFO3 = CurrencyExchangeRaterInfo.parse("https://example.com/currency-exchange-rater-3 " + NAME3);

    private final static String VALUE3 = "Value3";

    private final static String NAME4_STRING = "custom4";

    private final static CurrencyExchangeRaterName NAME4 = CurrencyExchangeRaterName.with(NAME4_STRING);

    private final static CurrencyExchangeRaterInfo INFO4 = CurrencyExchangeRaterInfo.parse("https://example.com/custom4 " + NAME4);

    private static CurrencyExchangeRater<FakeCurrencyExchangeRaterContext> currencyExchangeRater(final CurrencyExchangeRaterName name) {
        return new FakeCurrencyExchangeRater() {

            @Override
            public int hashCode() {
                return name.hashCode();
            }

            @Override
            public boolean equals(final Object other) {
                return this == other || other instanceof CurrencyExchangeRater && this.equals0((CurrencyExchangeRater<?>) other);
            }

            private boolean equals0(final CurrencyExchangeRater<?> other) {
                return this.toString().equals(other.toString());
            }

            @Override
            public String toString() {
                return name.toString();
            }
        };
    }

    private final static ProviderContext CONTEXT = ProviderContexts.fake();

    @Test
    public void testWithUnknownCurrencyExchangeRaterName() {
        AliasesCurrencyExchangeRaterProvider.with(
            CurrencyExchangeRaterAliasSet.parse("unknown-currencyer404"),
            new FakeCurrencyExchangeRaterProvider() {
                @Override
                public CurrencyExchangeRaterInfoSet currencyExchangeRaterInfos() {
                    return CurrencyExchangeRaterInfoSet.parse("https://example.com/currency-exchange-rater-111 currency-exchange-rater-111");
                }
            }
        );
    }

    @Test
    public void testCurrencyExchangeRaterNameWithName() {
        this.currencyExchangeRaterAndCheck(
            NAME1,
            Lists.empty(),
            CONTEXT,
            CURRENCYEXCHANGERATER1
        );
    }

    @Test
    public void testCurrencyExchangeRaterSelectorWithName() {
        this.currencyExchangeRaterAndCheck(
            CurrencyExchangeRaterSelector.parse(NAME1 + ""),
            CONTEXT,
            CURRENCYEXCHANGERATER1
        );
    }

    @Test
    public void testCurrencyExchangeRaterNameWithAlias() {
        this.currencyExchangeRaterAndCheck(
            ALIAS2,
            Lists.empty(),
            CONTEXT,
            CURRENCYEXCHANGERATER2
        );
    }

    @Test
    public void testCurrencyExchangeRaterSelectorWithAlias() {
        this.currencyExchangeRaterAndCheck(
            CurrencyExchangeRaterSelector.parse(ALIAS2 + ""),
            CONTEXT,
            CURRENCYEXCHANGERATER2
        );
    }

    @Test
    public void testCurrencyExchangeRaterNameWithSelector() {
        this.currencyExchangeRaterAndCheck(
            NAME4,
            Lists.empty(),
            CONTEXT,
            CURRENCYEXCHANGERATER3
        );
    }

    @Test
    public void testCurrencyExchangeRaterSelectorWithSelector() {
        this.currencyExchangeRaterAndCheck(
            CurrencyExchangeRaterSelector.parse(NAME4 + ""),
            CONTEXT,
            CURRENCYEXCHANGERATER3
        );
    }

    @Test
    public void testInfos() {
        this.currencyExchangeRaterInfosAndCheck(
            INFO1,
            INFO2.setName(ALIAS2),
            INFO4.setName(NAME4) // from CurrencyExchangeRaterAliasSet
        );
    }

    @Override
    public AliasesCurrencyExchangeRaterProvider createCurrencyExchangeRaterProvider() {
        final String aliases = "currency-exchange-rater-1, alias2 currency-exchange-rater-2, custom4 currency-exchange-rater-3(\"Value3\") https://example.com/custom4";

        this.checkEquals(
            NAME1 + ", " + ALIAS2 + " " + NAME2 + ", " + NAME4 + " " + NAME3 + "(\"" + VALUE3 + "\") " + INFO4.url(),
            aliases
        );

        return AliasesCurrencyExchangeRaterProvider.with(
            CurrencyExchangeRaterAliasSet.parse(aliases),
            new FakeCurrencyExchangeRaterProvider() {
                @Override
                public <C extends CurrencyExchangeRaterContext> CurrencyExchangeRater<C> currencyExchangeRater(final CurrencyExchangeRaterSelector selector,
                                                                                                               final ProviderContext context) {
                    return selector.evaluateValueText(
                        this,
                        context
                    );
                }

                @Override
                public <C extends CurrencyExchangeRaterContext> CurrencyExchangeRater<C> currencyExchangeRater(final CurrencyExchangeRaterName name,
                                                                                                               final List<?> values,
                                                                                                               final ProviderContext context) {
                    CurrencyExchangeRater<?> currencyExchangeRater;

                    switch (name.toString()) {
                        case NAME1_STRING:
                            checkEquals(Lists.empty(), values, "values");
                            currencyExchangeRater = CURRENCYEXCHANGERATER1;
                            break;
                        case NAME2_STRING:
                            checkEquals(Lists.empty(), values, "values");
                            currencyExchangeRater = CURRENCYEXCHANGERATER2;
                            break;
                        case NAME3_STRING:
                            checkEquals(Lists.of(VALUE3), values, "values");
                            currencyExchangeRater = CURRENCYEXCHANGERATER3;
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown CurrencyExchangeRater " + name);
                    }

                    return Cast.to(currencyExchangeRater);
                }

                @Override
                public CurrencyExchangeRaterInfoSet currencyExchangeRaterInfos() {
                    return CurrencyExchangeRaterInfoSet.with(
                        Sets.of(
                            INFO1,
                            INFO2,
                            INFO3
                        )
                    );
                }
            }
        );
    }

    // class............................................................................................................

    @Override
    public Class<AliasesCurrencyExchangeRaterProvider> type() {
        return AliasesCurrencyExchangeRaterProvider.class;
    }
}
