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
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;
import walkingkooka.collect.set.SortedSets;
import walkingkooka.plugin.PluginAliasSetLikeTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallerTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CurrencyExchangeRaterAliasSetTest implements PluginAliasSetLikeTesting<CurrencyExchangeRaterName,
    CurrencyExchangeRaterInfo,
    CurrencyExchangeRaterInfoSet,
    CurrencyExchangeRaterSelector,
    CurrencyExchangeRaterAlias,
    CurrencyExchangeRaterAliasSet>,
    HashCodeEqualsDefinedTesting2<CurrencyExchangeRaterAliasSet>,
    ToStringTesting<CurrencyExchangeRaterAliasSet>,
    JsonNodeMarshallerTesting<CurrencyExchangeRaterAliasSet> {

    // with.............................................................................................................

    @Test
    public void testWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyExchangeRaterAliasSet.with(null)
        );
    }

    @Test
    public void testWithEmpty() {
        assertSame(
            CurrencyExchangeRaterAliasSet.EMPTY,
            CurrencyExchangeRaterAliasSet.with(SortedSets.empty())
        );
    }

    @Test
    public void testWithCurrencyExchangeRaterAliasSetDoesntWrap() {
        final CurrencyExchangeRaterAliasSet currencyExchangeRaterAliasSet = this.createSet();
        assertSame(
            currencyExchangeRaterAliasSet,
            CurrencyExchangeRaterAliasSet.with(currencyExchangeRaterAliasSet)
        );
    }

    // name.............................................................................................................

    @Test
    public void testAliasOrNameWithName() {
        final CurrencyExchangeRaterName abc = CurrencyExchangeRaterName.with("abc");

        this.aliasOrNameAndCheck(
            this.createSet(),
            abc,
            abc
        );
    }

    @Test
    public void testAliasOrNameWithAlias() {
        this.aliasOrNameAndCheck(
            this.createSet(),
            CurrencyExchangeRaterName.with("sunshine-alias"),
            CurrencyExchangeRaterName.with("sunshine")
        );
    }

    @Test
    public void testAliasSelectorWithName() {
        this.aliasSelectorAndCheck(
            this.createSet(),
            CurrencyExchangeRaterName.with("abc")
        );
    }

    @Test
    public void testAliasSelectorWithAlias() {
        this.aliasSelectorAndCheck(
            this.createSet(),
            CurrencyExchangeRaterName.with("custom-alias"),
            CurrencyExchangeRaterSelector.parse("custom(1)")
        );
    }

    @Override
    public CurrencyExchangeRaterAliasSet createSet() {
        return CurrencyExchangeRaterAliasSet.parse("abc, moo, mars, custom-alias custom(1) https://example.com/custom , sunshine-alias sunshine");
    }

    // parse............................................................................................................

    @Override
    public CurrencyExchangeRaterAliasSet parseString(final String text) {
        return CurrencyExchangeRaterAliasSet.parse(text);
    }

    // equals...........................................................................................................

    @Test
    public void testEqualsDifferent() {
        this.checkNotEquals(
            CurrencyExchangeRaterAliasSet.parse("different")
        );
    }

    @Override
    public CurrencyExchangeRaterAliasSet createObject() {
        return CurrencyExchangeRaterAliasSet.parse("abc, custom-alias custom(1) https://example.com/custom");
    }

    // json.............................................................................................................

    @Override
    public CurrencyExchangeRaterAliasSet unmarshall(final JsonNode json,
                                                    final JsonNodeUnmarshallContext context) {
        return CurrencyExchangeRaterAliasSet.unmarshall(
            json,
            context
        );
    }

    @Override
    public CurrencyExchangeRaterAliasSet createJsonNodeMarshallingValue() {
        return CurrencyExchangeRaterAliasSet.parse("alias1 name1, name2, alias3 name3(\"999\") https://example.com/name3");
    }

    // class............................................................................................................

    @Override
    public Class<CurrencyExchangeRaterAliasSet> type() {
        return CurrencyExchangeRaterAliasSet.class;
    }
}
