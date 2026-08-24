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
import walkingkooka.collect.set.Sets;
import walkingkooka.net.Url;
import walkingkooka.plugin.PluginInfoSetLikeTesting;
import walkingkooka.reflect.ClassTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import static org.junit.jupiter.api.Assertions.assertSame;

public final class CurrencyExchangeRaterInfoSetTest implements PluginInfoSetLikeTesting<CurrencyExchangeRaterName, CurrencyExchangeRaterInfo, CurrencyExchangeRaterInfoSet, CurrencyExchangeRaterSelector, CurrencyExchangeRaterAlias, CurrencyExchangeRaterAliasSet>,
    ClassTesting<CurrencyExchangeRaterInfoSet> {

    @Test
    public void testImmutableSet() {
        final CurrencyExchangeRaterInfoSet set = this.createSet();

        assertSame(
            set,
            Sets.immutable(set)
        );
    }

    // parse............................................................................................................

    @Override
    public void testParseStringEmptyFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CurrencyExchangeRaterInfoSet parseString(final String text) {
        return CurrencyExchangeRaterInfoSet.parse(text);
    }

    // Set..............................................................................................................

    @Override
    public CurrencyExchangeRaterInfoSet createSet() {
        return CurrencyExchangeRaterInfoSet.with(
            Sets.of(
                this.info()
            )
        );
    }

    @Override
    public CurrencyExchangeRaterInfo info() {
        return CurrencyExchangeRaterInfo.with(
            Url.parseAbsolute("https://example.com/currencyExchangeRater-123"),
            CurrencyExchangeRaterName.with("currency-exchange-rater-123")
        );
    }

    // ImmutableSetTesting..............................................................................................

    @Override
    public void testSetElementsNullFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetElementsSame() {
        throw new UnsupportedOperationException();
    }

    // json.............................................................................................................

    @Test
    public void testMarshallEmpty() {
        this.marshallAndCheck(
            CurrencyExchangeRaterInfoSet.EMPTY,
            JsonNode.array()
        );
    }

    @Test
    public void testMarshallNotEmpty2() {
        this.marshallAndCheck(
            CurrencyExchangeRaterInfoSet.with(
                Sets.of(
                    CurrencyExchangeRaterInfo.with(
                        Url.parseAbsolute("https://example.com/test-123"),
                        CurrencyExchangeRaterName.with("test-123")
                    )
                )
            ),
            "[\n" +
                "  \"https://example.com/test-123 test-123\"\n" +
                "]"
        );
    }

    // json............................................................................................................

    @Override
    public CurrencyExchangeRaterInfoSet unmarshall(final JsonNode node,
                                                   final JsonNodeUnmarshallContext context) {
        return CurrencyExchangeRaterInfoSet.unmarshall(
            node,
            context
        );
    }

    @Override
    public CurrencyExchangeRaterInfoSet createJsonNodeMarshallingValue() {
        return CurrencyExchangeRaterInfoSet.with(
            Sets.of(
                CurrencyExchangeRaterInfo.with(
                    Url.parseAbsolute("https://example.com/test-111"),
                    CurrencyExchangeRaterName.with("test-111")
                ),
                CurrencyExchangeRaterInfo.with(
                    Url.parseAbsolute("https://example.com/test-222"),
                    CurrencyExchangeRaterName.with("test-222")
                )
            )
        );
    }

    // Class............................................................................................................

    @Override
    public Class<CurrencyExchangeRaterInfoSet> type() {
        return CurrencyExchangeRaterInfoSet.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
